package com.lutu.article_image.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface ArticleImageRepository extends JpaRepository<ArticleImageVO, Integer>{
	
	

}
