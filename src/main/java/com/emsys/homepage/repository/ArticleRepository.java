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

@RepositoryRestResource  // 이 클래스를 RESTful 웹 서비스로 만듦. 스프링 데이터 REST는 이 어노테이션을 가진 스프링 데이터 리포지토리를 찾아 해당 엔드포인트를 자동으로 생성합니다.
public interface ArticleRepository extends
        JpaRepository<Article, Long>,  // 스프링 데이터 JPA에서 제공하는 JpaRepository 인터페이스를 상속. 이를 통해 CRUD 기능과 페이지네이션, 정렬 등을 제공받을 수 있음.
        ArticleRepositoryCustom,  // 사용자 정의 쿼리 메소드를 담고 있는 인터페이스를 상속. 이를 통해 더 복잡한 쿼리를 수행할 수 있음.
        QuerydslPredicateExecutor<Article>,  // QueryDSL 지원을 위한 인터페이스를 상속. 이를 통해 동적 쿼리를 수행할 수 있음.
        QuerydslBinderCustomizer<QArticle> {  // QueryDSL의 바인더 커스터마이징을 위한 인터페이스를 상속. 이를 통해 QueryDSL의 바인딩 동작을 커스터마이징 할 수 있음.

    Page<Article> findByTitleContaining(String title, Pageable pageable);  // 제목이 주어진 문자열을 포함하는 모든 Article을 페이지 단위로 반환하는 메서드.
    Page<Article> findByContentContaining(String content, Pageable pageable);  // 본문이 주어진 문자열을 포함하는 모든 Article을 페이지 단위로 반환하는 메서드.
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);  // 유저ID가 주어진 문자열을 포함하는 모든 Article을 페이지 단위로 반환하는 메서드.
    Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);  // 닉네임이 주어진 문자열을 포함하는 모든 Article을 페이지 단위로 반환하는 메서드.
    Page<Article> findByHashtag(String hashtag, Pageable pageable);  // 해시태그가 주어진 문자열과 일치하는 모든 Article을 페이지 단위로 반환하는 메서드.

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {  // QueryDSL의 바인딩 동작을 커스터마이징 하는 메서드. 이 메서드를 통해 QueryDSL의 바인딩 동작을 원하는 대로 조정할 수 있음.
        bindings.excludeUnlistedProperties(true);  // root(Article)의 프로퍼티 중 아래에서 명시적으로 바인딩하지 않은 프로퍼티들을 바인딩에서 제외함.
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);  // 이 프로퍼티들을 바인딩에 포함시킴.
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);  // 'title' 프로퍼티에 대한 바인딩을 정의함. 대소문자를 구분하지 않는 방식으로 문자열이 포함되는지를 체크함.
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);  // 'content' 프로퍼티에 대한 바인딩을 정의함. 대소문자를 구분하지 않는 방식으로 문자열이 포함되는지를 체크함.
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);  // 'hashtag' 프로퍼티에 대한 바인딩을 정의함. 대소문자를 구분하지 않는 방식으로 문자열이 포함되는지를 체크함.
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);  // 'createdAt' 프로퍼티에 대한 바인딩을 정의함. 입력된 날짜와 같은지를 체크함.
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);  // 'createdBy' 프로퍼티에 대한 바인딩을 정의함. 대소문자를 구분하지 않는 방식으로 문자열이 포함되는지를 체크함.
    };
}



