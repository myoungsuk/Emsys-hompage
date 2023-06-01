package com.emsys.homepage.service;

import com.emsys.homepage.domain.Article;
import com.emsys.homepage.domain.UserAccount;
import com.emsys.homepage.domain.constant.SearchType;
import com.emsys.homepage.dto.ArticleDto;
import com.emsys.homepage.dto.ArticleWithCommentsDto;
import com.emsys.homepage.repository.ArticleRepository;
import com.emsys.homepage.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    /**
     * 게시글을 검색합니다.
     * @param searchType 검색 유형
     * @param searchKeyword 검색 키워드
     * @param pageable 페이지 정보
     * @return 게시글 DTO 페이지
     */
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    /**
     * 게시글과 해당 게시글의 댓글들을 가져옵니다.
     * @param articleId 게시글 ID
     * @return 게시글 및 댓글 DTO
     */
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    /**
     * 게시글을 가져옵니다.
     * @param articleId 게시글 ID
     * @return 게시글 DTO
     */
    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    /**
     * 게시글을 저장합니다.
     * @param dto 게시글 DTO
     */
    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
        articleRepository.save(dto.toEntity(userAccount));
    }

    /**
     * 게시글을 업데이트합니다.
     * @param articleId 게시글 ID
     * @param dto 게시글 DTO
     */
    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());

            if (article.getUserAccount().equals(userAccount)) {
                if (dto.title() != null) {
                    article.setTitle(dto.title());
                }
                if (dto.content() != null) {
                    article.setContent(dto.content());
                }
                article.setHashtag(dto.hashtag());
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다 - dto: {}", dto);
        }
    }

    /**
     * 게시글을 삭제합니다.
     * @param articleId 게시글 ID
     * @param userId 유저 ID
     */
    public void deleteArticle(long articleId, String userId) {
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
    }

    /**
     * 게시글의 총 개수를 반환합니다.
     * @return 게시글 개수
     */
    public long getArticleCount() {
        return articleRepository.count();
    }

    /**
     * 해시태그로 게시글을 검색합니다.
     * @param hashtag 해시태그
     * @param pageable 페이지 정보
     * @return 게시글 DTO 페이지
     */
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);
    }

    /**
     * 모든 게시글에 사용된 해시태그를 가져옵니다.
     * @return 해시태그 리스트
     */
    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }
}