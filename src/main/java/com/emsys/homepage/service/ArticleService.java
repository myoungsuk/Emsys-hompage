package com.emsys.homepage.service;

import com.emsys.homepage.domain.Article;
import com.emsys.homepage.domain.UserAccount;
import com.emsys.homepage.domain.type.SearchType;
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

@Slf4j  // SLF4J의 로거를 이용해 로깅을 할 수 있도록 합니다.
@RequiredArgsConstructor // final이나 @NonNull인 필드만을 인자로 받는 생성자를 생성합니다. 여기서는 레포지터리들을 주입받기 위해 사용합니다.
@Transactional // 클래스 레벨에 Transactional 어노테이션을 적용하여 이 클래스의 모든 public 메소드에 트랜잭션을 적용합니다.
@Service // 이 클래스가 Spring의 Service 레이어의 클래스임을 나타내는 어노테이션입니다.
public class ArticleService {

    private final ArticleRepository articleRepository; // ArticleRepository를 주입받습니다. 이를 통해 게시글 관련 데이터 처리를 할 수 있습니다.
    private final UserAccountRepository userAccountRepository; // UserAccountRepository를 주입받습니다. 이를 통해 사용자 관련 데이터 처리를 할 수 있습니다.

    @Transactional(readOnly = true) // 이 메소드는 읽기 전용 트랜잭션 범위에서 실행됩니다. 즉, 데이터 변경을 할 수 없습니다.
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

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
        articleRepository.save(dto.toEntity(userAccount));
    }

    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            if (dto.title() != null) { article.setTitle(dto.title()); }
            if (dto.content() != null) { article.setContent(dto.content()); }
            article.setHashtag(dto.hashtag());
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다 - dto: {}", dto);
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);
    }

    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }

}
