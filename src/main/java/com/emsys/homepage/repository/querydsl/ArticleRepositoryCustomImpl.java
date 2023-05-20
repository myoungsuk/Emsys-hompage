package com.emsys.homepage.repository.querydsl;

import com.emsys.homepage.domain.Article;
import com.emsys.homepage.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom{

    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article;

        return from(article)
                .distinct()
                .select(article.hashtag)    // 하나의 컬럼만 내보냄
                .where(article.hashtag.isNotNull())
                .fetch();
    }
}