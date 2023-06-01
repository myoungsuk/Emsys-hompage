package com.emsys.homepage.repository;

import com.emsys.homepage.domain.Article;
import com.emsys.homepage.domain.QArticle;
import com.emsys.homepage.repository.querydsl.ArticleRepositoryCustom;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import static antlr.build.ANTLR.root;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        ArticleRepositoryCustom,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle> {

    /**
     * 제목을 포함하는 게시글을 페이지로 조회합니다.
     * @param title 제목 키워드
     * @param pageable 페이지 정보
     * @return 게시글 페이지
     */
    Page<Article> findByTitleContaining(String title, Pageable pageable);

    /**
     * 내용을 포함하는 게시글을 페이지로 조회합니다.
     * @param content 내용 키워드
     * @param pageable 페이지 정보
     * @return 게시글 페이지
     */
    Page<Article> findByContentContaining(String content, Pageable pageable);

    /**
     * 유저 ID를 포함하는 게시글을 페이지로 조회합니다.
     * @param userId 유저 ID 키워드
     * @param pageable 페이지 정보
     * @return 게시글 페이지
     */
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);

    /**
     * 닉네임을 포함하는 게시글을 페이지로 조회합니다.
     * @param nickname 닉네임 키워드
     * @param pageable 페이지 정보
     * @return 게시글 페이지
     */
    Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);

    /**
     * 특정 해시태그를 가지고 있는 게시글을 페이지로 조회합니다.
     * @param hashtag 해시태그
     * @param pageable 페이지 정보
     * @return 게시글 페이지
     */
    Page<Article> findByHashtag(String hashtag, Pageable pageable);

    /**
     * 특정 아이디와 유저 ID에 해당하는 게시글을 삭제합니다.
     * @param articleId 게시글 ID
     * @param userId 유저 ID
     */
    void deleteByIdAndUserAccount_UserId(Long articleId, String userId);

    // QuerydslBinderCustomizer 인터페이스의 메소드로, Querydsl 바인딩을 커스터마이징합니다. 여기서는 제목, 내용, 해시태그, 생성일시,
    // 생성자에 대한 검색 조건을 설정하고, 대소문자 구분 없이 문자열을 포함하는 검색을 수행합니다.
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase);      // like 's{v}'
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);  // like '%s{v}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    };
}