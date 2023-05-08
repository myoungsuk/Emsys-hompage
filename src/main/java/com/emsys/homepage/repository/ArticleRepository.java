package com.emsys.homepage.repository;

import com.emsys.homepage.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>
{

}
