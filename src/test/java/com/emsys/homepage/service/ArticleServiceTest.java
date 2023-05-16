package com.emsys.homepage.service;

import com.emsys.homepage.domain.constant.SearchType;
import com.emsys.homepage.dto.ArticleDto;
import com.emsys.homepage.repository.ArticleRepository;
import com.emsys.homepage.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("비즈니스 로직 -게시글")
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @InjectMocks private ArticleService sut;

    @Mock private ArticleRepository articleRepository;
    @Mock private UserAccountRepository userAccountRepository;

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSerachParameters_whenSerachingArticles_thenReturnsArticleList() {
        // Given


        // When
        List<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword");

        // Then
        assertThat(articles)
                .isNotNull();
    }

    @Disabled
    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchArticle_theReturnArticle() {
        // Given


        // When
        ArticleDto articles = sut.searchArticle(1L);

        // Then
        assertThat(articles)
                .isNotNull();
    }


}
