package com.lutu.article.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlesRepository extends JpaRepository<ArticlesVO, Integer> {

    /**
     * 根據文章標題進行模糊查詢
     * 
     * @param acTitle 標題關鍵字
     * @return 含有該關鍵字的所有文章
     */
    List<ArticlesVO> findByAcTitleContaining(String acTitle);

    /**
     * 根據文章標題及類別進行複合查詢
     * 
     * @param acTitle  標題關鍵字
     * @param acTypeId 文章類別ID
     * @return 含有該關鍵字且類別符合的所有文章
     */
    List<ArticlesVO> findByAcTitleContainingAndArticleTypeVO_AcTypeId(String acTitle, Integer acTypeId);

    /**
     * 根據文章內容進行模糊查詢
     * 
     * @param acContext 內容關鍵字
     * @return 含有該關鍵字的所有文章
     */
    List<ArticlesVO> findByAcContextContaining(String acContext);

    /**
     * 根據文章標題或內容進行模糊查詢
     * 
     * @param acTitle   標題關鍵字
     * @param acContext 內容關鍵字
     * @return 標題或內容含有該關鍵字的所有文章
     */
    List<ArticlesVO> findByAcTitleContainingOrAcContextContaining(String acTitle, String acContext);

    /**
     * 根據文章標題、內容及類別進行複合查詢
     * 
     * @param acTitle   標題關鍵字
     * @param acContext 內容關鍵字
     * @param acTypeId  文章類別ID
     * @return 符合條件的所有文章
     */
    List<ArticlesVO> findByAcTitleContainingAndArticleTypeVO_AcTypeIdOrAcContextContainingAndArticleTypeVO_AcTypeId(
            String acTitle, Integer acTypeId1, String acContext, Integer acTypeId2);

    /**
     * 根據文章類別查詢文章數量
     * 
     * @param acTypeId 文章類別ID
     * @return 該類別的文章數量
     */
    long countByArticleTypeVOAcTypeId(Integer acTypeId);

    /**
     * 根據會員ID查詢文章數量
     * 
     * @param memId 會員ID
     * @return 該會員的文章數量
     */
    long countByMemberVOMemId(Integer memId);

    /**
     * 根據留言者姓名查詢所有有該留言者留言的文章（distinct）
     * 
     * @param memName 留言者姓名
     * @return 有該留言者留言的所有文章
     */
    @Query("SELECT DISTINCT r.articlesVO FROM ReplyVO r WHERE r.memberVO.memName = :memName")
    List<ArticlesVO> findDistinctArticlesByReplyMemberName(@Param("memName") String memName);
}
