package com.lutu.article_type.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface ArticleTypeRepository extends JpaRepository<ArticleTypeVO, Integer> {


}
