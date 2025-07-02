package com.lutu.reply.controller;

import com.lutu.ApiResponse;
import com.lutu.reply.model.ReplyService;
import com.lutu.reply.model.ReplyVO;
import com.lutu.member.model.MemberRepository;
import com.lutu.article.model.ArticlesRepository;
import com.lutu.member.model.MemberVO;
import com.lutu.article.model.ArticlesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class ReplyApiController {
    @Autowired
    ReplyService replyService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArticlesRepository articlesRepository;

    // 取得所有留言
    @GetMapping("/api/replies")
    public ApiResponse<List<ReplyDTO>> getAllReplies() {
        List<ReplyVO> replies = replyService.getAll();
        List<ReplyDTO> replyDTOs = replies.stream()
                .map(ReplyDTO::new)
                .collect(Collectors.toList());
        return new ApiResponse<>("success", replyDTOs, "查詢成功");
    }

    // 取得所有留言數量
    @GetMapping("/api/replies/count")
    public ApiResponse<Long> getRepliesCount() {
        List<ReplyVO> replies = replyService.getAll();
        Long count = (long) replies.size();
        return new ApiResponse<>("success", count, "查詢成功");
    }

    // 取得單一留言
    @GetMapping("/api/replies/{replyId}")
    public ApiResponse<ReplyDTO> getOneReply(@PathVariable Integer replyId) {
        ReplyVO reply = replyService.getOneReply(replyId);
        if (reply != null) {
            ReplyDTO replyDTO = new ReplyDTO(reply);
            return new ApiResponse<>("success", replyDTO, "查詢成功");
        } else {
            return new ApiResponse<>("fail", null, "查無此留言");
        }
    }

    // 根據文章ID取得該文章的所有留言
    @GetMapping("/api/replies/article/{acId}")
    public ApiResponse<List<ReplyDTO>> getRepliesByArticle(@PathVariable Integer acId) {
        try {
            // 先確認文章是否存在
            ArticlesVO article = articlesRepository.findById(acId).orElse(null);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            // 使用優化的查詢方法，直接取得該文章的留言並載入會員資料
            List<ReplyVO> articleReplies = replyService.getRepliesByArticleId(acId);

            List<ReplyDTO> replyDTOs = articleReplies.stream()
                    .map(ReplyDTO::new)
                    .collect(Collectors.toList());

            return new ApiResponse<>("success", replyDTOs, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "查詢失敗: " + e.getMessage());
        }
    }

    // 根據文章ID取得該文章的留言數量
    @GetMapping("/api/replies/article/{acId}/count")
    public ApiResponse<Long> getReplyCountByArticle(@PathVariable Integer acId) {
        try {
            // 先確認文章是否存在
            ArticlesVO article = articlesRepository.findById(acId).orElse(null);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            // 取得所有留言並篩選出屬於該文章的留言
            List<ReplyVO> allReplies = replyService.getAll();
            Long count = allReplies.stream()
                    .filter(reply -> reply.getArticlesVO() != null &&
                            reply.getArticlesVO().getAcId().equals(acId))
                    .count();

            return new ApiResponse<>("success", count, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", 0L, "查詢失敗: " + e.getMessage());
        }
    }

    // 新增留言
    @PostMapping("/api/replies")
    public ApiResponse<ReplyDTO> createReply(@Valid @RequestBody ReplyDTO dto) {
        try {
            // 將 DTO 轉為 VO
            ReplyVO vo = new ReplyVO();
            vo.setReplyContext(dto.getReplyContext());

            // 查詢並設置 MemberVO
            MemberVO member = null;
            if (dto.getMemId() != null) {
                member = memberRepository.findById(dto.getMemId()).orElse(null);
                if (member == null) {
                    return new ApiResponse<>("fail", null, "查無此會員");
                }
            }
            vo.setMemberVO(member);

            // 查詢並設置 ArticlesVO
            ArticlesVO article = null;
            if (dto.getAcId() != null) {
                article = articlesRepository.findById(dto.getAcId()).orElse(null);
                if (article == null) {
                    return new ApiResponse<>("fail", null, "查無此文章");
                }
            }
            vo.setArticlesVO(article);

            // 設定回覆時間為當前時間
            if (vo.getReplyTime() == null) {
                vo.setReplyTime(java.time.LocalDateTime.now());
            }

            // 設定預設狀態為顯示 (0)
            if (vo.getReplyStatus() == null) {
                vo.setReplyStatus((byte) 0);
            }

            replyService.addReply(vo);

            // 回傳包含完整資料的 DTO
            ReplyDTO replyDTO = new ReplyDTO(vo);
            return new ApiResponse<>("success", replyDTO, "新增成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "新增失敗: " + e.getMessage());
        }
    }

    // 更新留言
    @PutMapping("/api/replies/{replyId}")
    public ApiResponse<ReplyDTO> updateReply(@PathVariable Integer replyId, @Valid @RequestBody ReplyDTO dto) {
        try {
            // 先檢查留言是否存在
            ReplyVO existingReply = replyService.getOneReply(replyId);
            if (existingReply == null) {
                return new ApiResponse<>("fail", null, "查無此留言");
            }

            // 更新留言內容
            existingReply.setReplyContext(dto.getReplyContext());

            // 如果提供了新的狀態，則更新狀態
            if (dto.getReplyStatus() != null) {
                existingReply.setReplyStatus(dto.getReplyStatus());
            }

            // 如果提供了新的回覆時間，則更新時間
            if (dto.getReplyTime() != null) {
                existingReply.setReplyTime(dto.getReplyTime());
            }

            replyService.updateReply(existingReply);

            ReplyDTO replyDTO = new ReplyDTO(existingReply);
            return new ApiResponse<>("success", replyDTO, "更新成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "更新失敗: " + e.getMessage());
        }
    }

    // 刪除留言
    @DeleteMapping("/api/replies/{replyId}")
    public ApiResponse<Void> deleteReply(@PathVariable Integer replyId) {
        try {
            // 先檢查留言是否存在
            ReplyVO existingReply = replyService.getOneReply(replyId);
            if (existingReply == null) {
                return new ApiResponse<>("fail", null, "查無此留言");
            }

            replyService.deleteReply(replyId);
            return new ApiResponse<>("success", null, "刪除成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "刪除失敗: " + e.getMessage());
        }
    }

    // 根據會員ID取得該會員的所有留言
    @GetMapping("/api/replies/member/{memId}")
    public ApiResponse<List<ReplyDTO>> getRepliesByMember(@PathVariable Integer memId) {
        try {
            // 先確認會員是否存在
            MemberVO member = memberRepository.findById(memId).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            // 取得所有留言並篩選出屬於該會員的留言
            List<ReplyVO> allReplies = replyService.getAll();
            List<ReplyVO> memberReplies = allReplies.stream()
                    .filter(reply -> reply.getMemberVO() != null &&
                            reply.getMemberVO().getMemId().equals(memId))
                    .collect(Collectors.toList());

            List<ReplyDTO> replyDTOs = memberReplies.stream()
                    .map(ReplyDTO::new)
                    .collect(Collectors.toList());

            return new ApiResponse<>("success", replyDTOs, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "查詢失敗: " + e.getMessage());
        }
    }

    // 根據會員ID取得該會員的留言數量
    @GetMapping("/api/replies/member/{memId}/count")
    public ApiResponse<Long> getReplyCountByMember(@PathVariable Integer memId) {
        try {
            // 先確認會員是否存在
            MemberVO member = memberRepository.findById(memId).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            // 取得所有留言並篩選出屬於該會員的留言
            List<ReplyVO> allReplies = replyService.getAll();
            Long count = allReplies.stream()
                    .filter(reply -> reply.getMemberVO() != null &&
                            reply.getMemberVO().getMemId().equals(memId))
                    .count();

            return new ApiResponse<>("success", count, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", 0L, "查詢失敗: " + e.getMessage());
        }
    }
}
