package com.emsys.homepage.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter  // Lombok 라이브러리를 사용하여 getter 메소드를 자동으로 생성합니다.
@ToString(callSuper = true)  // Lombok 라이브러리를 사용하여 toString 메소드를 자동으로 생성하며, 부모 클래스의 필드를 포함합니다.
@Table(indexes = {
        @Index(columnList = "title"),  // 'title' 칼럼에 인덱스를 생성합니다.
        @Index(columnList = "createdAt"),  // 'createdAt' 칼럼에 인덱스를 생성합니다.
        @Index(columnList = "createdBy")  // 'createdBy' 칼럼에 인덱스를 생성합니다.
})
@Entity  // JPA를 사용하여 이 클래스를 데이터베이스 테이블에 매핑합니다.
public class Article extends AuditingFields {  // AuditingFields를 상속하는 Article 클래스를 정의합니다.

    @Id  // 'id' 필드를 기본 키로 지정합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 'id' 필드의 값을 자동으로 생성하도록 설정합니다.
    private Long id;  // 'id' 필드를 선언합니다.

    @Setter
    @JoinColumn(name = "userId")  // 'userAccount' 필드를 'userId' 칼럼에 매핑합니다.
    @ManyToOne(optional = false)  // 'userAccount' 필드를 UserAccount 엔티티와 ManyToOne 관계로 설정하며, null을 허용하지 않습니다.
    private UserAccount userAccount; // 유저 정보 (ID)

    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    @ToString.Exclude  // toString 메소드 생성 시 'hashtags' 필드를 제외합니다.
    @JoinTable(
            name = "article_hashtag",  // 중간 테이블의 이름을 'article_hashtag'로 설정합니다.
            joinColumns = @JoinColumn(name = "articleId"),  // 현재 엔티티를 'articleId' 칼럼에 매핑합니다.
            inverseJoinColumns = @JoinColumn(name = "hashtagId")  // 대상 엔티티를 'hashtagId' 칼럼에 매핑합니다.
    )
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})  // 'hashtags' 필드를 Hashtag 엔티티와 ManyToMany 관계로 설정하며, 캐스케이드 옵션을 PERSIST와 MERGE로 설정합니다.
    private Set<Hashtag> hashtags = new LinkedHashSet<>();  // 'hashtags' 필드를 선언하고 초기화합니다.

    @ToString.Exclude  // toString 메소드 생성 시 'articleComments' 필드를 제외합니다.
    @OrderBy("createdAt DESC")  // 'articleComments' 필드를 'createdAt' 칼럼의 내림차순으로 정렬합니다.
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)  // 'articleComments' 필드를 ArticleComment 엔티티와 OneToMany 관계로 설정하며, 캐스케이드 옵션을 ALL로 설정합니다.
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();  // 'articleComments' 필드를 선언하고 초기화합니다.

    protected Article() {}  // 기본 생성자를 protected로 선언합니다. 이는 JPA 스펙에 따른 것입니다.

    private Article(UserAccount userAccount, String title, String content) {  // 모든 필드를 초기화하는 생성자를 선언합니다.
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
    }

    public static Article of(UserAccount userAccount, String title, String content) {  // static factory method를 선언하여 Article 객체를 생성합니다.
        return new Article(userAccount, title, content);
    }

    public void addHashtag(Hashtag hashtag) {  // 'hashtags' 필드에 Hashtag 객체를 추가하는 메소드를 선언합니다.
        this.getHashtags().add(hashtag);
    }

    public void addHashtags(Collection<Hashtag> hashtags) {  // 'hashtags' 필드에 Hashtag 객체의 컬렉션을 추가하는 메소드를 선언합니다.
        this.getHashtags().addAll(hashtags);
    }

    public void clearHashtags() {  // 'hashtags' 필드의 모든 요소를 제거하는 메소드를 선언합니다.
        this.getHashtags().clear();
    }

    @Override
    public boolean equals(Object o) {  // equals 메소드를 오버라이드 합니다.
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {  // hashCode 메소드를 오버라이드 합니다.
        return Objects.hash(this.getId());
    }
}
