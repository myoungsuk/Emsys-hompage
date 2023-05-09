package com.emsys.homepage.repository;

import com.emsys.homepage.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {


}
