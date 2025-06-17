package com.lutu.article_type.controller;

import java.util.List;

import com.lutu.article_type.model.ArticleTypeDAO;
import com.lutu.article_type.model.ArticleTypeService;
import com.lutu.article_type.model.ArticleTypeVO;


public class TestArticleType {

	public static void main(String[] args) {

		ArticleTypeService articleTypeSvc = new ArticleTypeService();
		// 新增類別----------------------------------------------
		ArticleTypeVO articleTypeVO1 = new ArticleTypeVO();

		articleTypeVO1.setAcTypeKind("露營知識");
		articleTypeVO1.setAcTypeText("露營小知識");

		ArticleTypeVO newArticleTypeVO1 = articleTypeSvc.addArticleType(articleTypeVO1);

		// 更新資料----------------------------------------------
		ArticleTypeVO articleTypeVO2 = new ArticleTypeVO();

		articleTypeVO2.setAcTypeId(30001);
		articleTypeVO2.setAcTypeKind("露營知識");
		articleTypeVO2.setAcTypeText("露營相關知識");
		
		ArticleTypeVO newArticleTypeVO2 = articleTypeSvc.updateArticleType(articleTypeVO2);

		// 刪除文章類別----------------------------------------------

		articleTypeSvc.deleteArticleType(30002);

		// 查詢單筆類別----------------------------------------------

		ArticleTypeVO articleTypeVO = articleTypeSvc.getOneArticleType(30002);

		System.out.print(articleTypeVO.getAcTypeId() + ",");
		System.out.print(articleTypeVO.getAcTypeKind() + ",");
		System.out.print(articleTypeVO.getAcTypeText() + ",");

		System.out.println("---------------------");

		// 查詢所有文章類別----------------------------------------------

		List<ArticleTypeVO> articleTypeVOList = articleTypeSvc.getAll();
		
		for (ArticleTypeVO articleTypeVO3 : articleTypeVOList) {
			System.out.print(articleTypeVO3.getAcTypeId() + ",");
			System.out.print(articleTypeVO3.getAcTypeKind() + ",");
			System.out.print(articleTypeVO3.getAcTypeText() + ",");

			System.out.println();
		}

	}

}
