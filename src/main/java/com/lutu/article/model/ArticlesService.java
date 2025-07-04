package com.lutu.article.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.ac_fav_record.model.AcFavRecordRepository;
import com.lutu.article_report.model.ArticleReportRepository;
import com.lutu.article_type.model.ArticleTypeRepository;
import com.lutu.article_type.model.ArticleTypeVO;
import com.lutu.nice_article.model.NiceArticleRepository;
import com.lutu.reply.model.ReplyRepository;
import com.lutu.reply_report.model.ReplyReportRepository;

@Service("articlesService")
public class ArticlesService {

    @Autowired
    ArticlesRepository repository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ReplyReportRepository replyReportRepository;

    @Autowired
    ArticleReportRepository articleReportRepository;

    @Autowired
    NiceArticleRepository niceArticleRepository;

    @Autowired
    AcFavRecordRepository acFavRecordRepository;

    @Autowired
    ArticleTypeRepository articleTypeRepository;

    // 注意：這裡可能會產生循環依賴，建議在需要時再啟用
    // @Autowired
    // ArticleImageService articleImageService;

    public void addArticles(ArticlesVO articlesVO) {
        repository.save(articlesVO);
    }

    public void updateArticles(ArticlesVO articlesVO) {
        repository.save(articlesVO);
    }

    /**
     * 安全的部分更新方法 - 避免 Hibernate 實體關聯問題
     * 只更新基本字段，不影響集合關聯
     */
    @Transactional
    public ArticlesVO updateArticleBasicInfo(Integer acId, String acTitle, String acContext, Integer acTypeId) {
        Optional<ArticlesVO> optional = repository.findById(acId);
        if (optional.isPresent()) {
            ArticlesVO existingArticle = optional.get();

            // 只更新基本字段，保持原有的關聯不變
            if (acTitle != null) {
                // 檢查標題長度
                if (acTitle.length() > 30) {
                    throw new IllegalArgumentException("文章標題長度不能超過30個字元");
                }
                existingArticle.setAcTitle(acTitle);
            }

            if (acContext != null) {
                // 檢查內容長度並處理
                String processedContent = validateAndProcessContent(acContext);
                existingArticle.setAcContext(processedContent);
            }

            if (acTypeId != null) {
                // 載入新的文章類型
                Optional<ArticleTypeVO> newTypeOptional = articleTypeRepository.findById(acTypeId);
                if (newTypeOptional.isPresent()) {
                    existingArticle.setArticleTypeVO(newTypeOptional.get());
                }
            }

            // 使用現有實體保存，Hibernate 會追蹤變更
            return repository.save(existingArticle);
        }
        return null;
    }

    /**
     * 驗證和處理文章內容，確保不超過長度限制
     */
    private String validateAndProcessContent(String content) {
        if (content == null) {
            return "";
        }

        // 先嘗試提取並分離 Base64 圖片
        String processedContent = extractAndSeparateImages(content);

        // 如果內容仍超過限制，進行智能截斷
        if (processedContent.length() > 100000) {
            // 嘗試在完整的HTML標籤處截斷
            return truncateHtmlContent(processedContent, 95000); // 留一些緩衝空間
        }

        return processedContent;
    }

    /**
     * 提取內容中的 Base64 圖片，保存到 article_image 表，並替換為圖片引用
     */
    private String extractAndSeparateImages(String htmlContent) {
        if (htmlContent == null || !htmlContent.contains("data:image")) {
            return htmlContent;
        }

        // 使用正則表達式查找 Base64 圖片
        String base64Pattern = "<img[^>]*src=[\"']data:image/([^;]+);base64,([^\"']+)[\"'][^>]*>";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(base64Pattern);
        java.util.regex.Matcher matcher = pattern.matcher(htmlContent);

        String processedContent = htmlContent;

        while (matcher.find()) {
            String fullMatch = matcher.group(0);
            String imageType = matcher.group(1); // jpeg, png, etc.
            String base64Data = matcher.group(2);

            try {
                // 解碼 Base64 圖片
                byte[] imageBytes = java.util.Base64.getDecoder().decode(base64Data);

                // 保存到 article_image 表（需要注入 ArticleImageService）
                // 暫時先替換為佔位符，避免循環依賴
                String placeholder = String.format(
                        "<img src=\"/api/article-images/placeholder\" alt=\"圖片已分離存儲\" data-size=\"%d\">",
                        imageBytes.length);

                processedContent = processedContent.replace(fullMatch, placeholder);

            } catch (Exception e) {
                // 如果處理失敗，保留原始圖片但添加警告
                String warningImg = fullMatch.replace("<img",
                        "<img data-warning=\"圖片過大，建議使用圖片上傳功能\"");
                processedContent = processedContent.replace(fullMatch, warningImg);
            }
        }

        return processedContent;
    }

    /**
     * 智能截斷HTML內容，避免破壞HTML結構
     */
    private String truncateHtmlContent(String htmlContent, int maxLength) {
        if (htmlContent.length() <= maxLength) {
            return htmlContent;
        }

        // 簡單截斷策略：在最接近限制的地方截斷，然後添加結束標籤
        String truncated = htmlContent.substring(0, maxLength);

        // 檢查是否在標籤中間截斷
        int lastOpenTag = truncated.lastIndexOf('<');
        int lastCloseTag = truncated.lastIndexOf('>');

        if (lastOpenTag > lastCloseTag) {
            // 在標籤中間截斷，回退到上一個完整標籤
            truncated = truncated.substring(0, lastOpenTag);
        }

        // 添加警告訊息
        truncated += "<p><em style=\"color: orange;\">內容因長度限制而被截斷...</em></p>";

        return truncated;
    }

    @Transactional
    public void deleteArticles(Integer acId) {
        if (repository.existsById(acId)) {
            // 先刪除相關的 reply_report 記錄
            replyReportRepository.deleteByAcId(acId);

            // 再刪除相關的 reply 記錄
            replyRepository.deleteByArticlesVO_AcId(acId);

            // 刪除相關的文章檢舉記錄
            articleReportRepository.deleteByArticlesVO_AcId(acId);

            // 刪除相關的文章按讚記錄
            niceArticleRepository.deleteByAcId(acId);

            // 刪除相關的文章收藏記錄
            acFavRecordRepository.deleteByAcId(acId);

            // 最後刪除文章 (article_images 會因為 cascade = CascadeType.ALL, orphanRemoval = true
            // 自動刪除)
            repository.deleteById(acId);
        }
    }

    public ArticlesVO getOneArticles(Integer acId) {
        Optional<ArticlesVO> optional = repository.findById(acId);
        // return optional.get();
        return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
    }

    public List<ArticlesVO> getAll() {
        return repository.findAll();
    }

    public List<ArticlesVO> findByAcTitleContaining(String acTitle) {
        return repository.findByAcTitleContaining(acTitle);
    }

    public List<ArticlesVO> findByAcTitleAndOptionalType(String acTitle, Integer acTypeId) {
        if (acTypeId == null) {
            return repository.findByAcTitleContaining(acTitle);
        } else {
            return repository.findByAcTitleContainingAndArticleTypeVO_AcTypeId(acTitle, acTypeId);
        }
    }

    public List<ArticlesVO> findByAcContextContaining(String acContext) {
        return repository.findByAcContextContaining(acContext);
    }

    public List<ArticlesVO> findByAcTitleOrContextContaining(String keyword) {
        return repository.findByAcTitleContainingOrAcContextContaining(keyword, keyword);
    }

    public List<ArticlesVO> findByAcTitleOrContextAndOptionalType(String keyword, Integer acTypeId) {
        if (acTypeId == null) {
            return repository.findByAcTitleContainingOrAcContextContaining(keyword, keyword);
        } else {
            return repository
                    .findByAcTitleContainingAndArticleTypeVO_AcTypeIdOrAcContextContainingAndArticleTypeVO_AcTypeId(
                            keyword, acTypeId, keyword, acTypeId);
        }
    }

    /**
     * 根據文章類別查詢文章數量
     * 
     * @param acTypeId 文章類別ID
     * @return 該類別的文章數量
     */
    public long getArticleCountByType(Integer acTypeId) {
        return repository.countByArticleTypeVOAcTypeId(acTypeId);
    }

    /**
     * 根據會員ID查詢文章數量
     * 
     * @param memId 會員ID
     * @return 該會員的文章數量
     */
    public long getArticleCountByMember(Integer memId) {
        return repository.countByMemberVOMemId(memId);
    }

    /**
     * 根據留言者姓名查詢所有有該留言者留言的文章
     * 
     * @param memName 留言者姓名
     * @return 有該留言者留言的所有文章
     */
    public List<ArticlesVO> findArticlesByReplyMemberName(String memName) {
        return repository.findDistinctArticlesByReplyMemberName(memName);
    }

    // 根據會員名稱查詢所有有該會員發表的文章
    public List<ArticlesVO> findByMemberName(String memName) {
        return repository.findByMemberVOMemName(memName);
    }

    /**
     * 查詢瀏覽次數最多的文章
     * 
     * @return ArticlesVO 或 null
     */
    public ArticlesVO getMostViewedArticle() {
        return repository.findTopByOrderByAcViewCountDesc().orElse(null);
    }

    /**
     * 根據文章ID將瀏覽次數 +1 並回傳最新值
     * 
     * @param acId 文章ID
     * @return 最新瀏覽次數，若查無此文章則回傳 null
     */
    @Transactional
    public Long increaseViewCount(Integer acId) {
        ArticlesVO article = repository.findById(acId).orElse(null);
        if (article != null) {
            Long current = article.getAcViewCount() == null ? 0L : article.getAcViewCount();
            article.setAcViewCount(current + 1);
            repository.save(article);
            return article.getAcViewCount();
        }
        return null;
    }

}