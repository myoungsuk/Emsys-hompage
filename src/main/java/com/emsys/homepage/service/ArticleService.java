package com.emsys.homepage.service;

import com.emsys.homepage.domain.Article;
import com.emsys.homepage.domain.constant.SearchType;
import com.emsys.homepage.dto.ArticleDto;
import com.emsys.homepage.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<ArticleDto> searchArticles(SearchType title, String search_keyword) {
        return List.of();

    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long l) {
        return null;
    }
}
