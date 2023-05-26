package com.emsys.homepage.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

// Lombok 라이브러리의 @Getter 어노테이션. Getter 메소드를 자동으로 생성해 줍니다.
@Getter

// Lombok 라이브러리의 @ToString 어노테이션. toString 메소드를 자동으로 생성해 줍니다.
// callSuper = true 옵션으로 부모 클래스의 필드들도 출력 내용에 포함시킵니다.
@ToString(callSuper = true)

// 데이터베이스 테이블에 인덱스를 추가합니다. 특정 컬럼의 값을 기준으로 빠르게 데이터를 검색할 수 있습니다.
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})

// JPA 어노테이션. 이 클래스를 데이터베이스의 테이블과 매핑한다는 것을 나타냅니다.
@Entity

// Article 클래스는 AuditingFields 클래스를 상속받습니다.
public class Article extends AuditingFields {

    // JPA 어노테이션. 이 필드를 데이터베이스의 기본키(Primary Key)로 설정합니다.
    @Id

    // JPA 어노테이션. 데이터베이스에서 기본키 값을 자동으로 생성하도록 설정합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lombok의 @Setter 어노테이션. Setter 메소드를 자동으로 생성합니다.
    // JPA의 @ManyToOne 어노테이션. UserAccount와 Article은 다대일 관계를 가집니다.
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount;

    // Lombok의 @Setter 어노테이션. Setter 메소드를 자동으로 생성합니다.
    // JPA의 @Column 어노테이션. title 필드를 데이터베이스의 컬럼으로 매핑합니다.
    @Setter @Column(nullable = false) private String title;

    // content 필드를 데이터베이스의 컬럼으로 매핑합니다. 컬럼의 길이는 최대 10000입니다.
    @Setter @Column(nullable = false, length = 10000) private String content;

    // hashtag 필드를 데이터베이스의 컬럼으로 매핑합니다.
    @Setter private String hashtag;

    // JPA의 @OneToMany 어노테이션. Article과 ArticleComment는 일대다 관계를 가집니다.
    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    // protected로 설정된 기본 생성자
    protected Article() {}

    // 생성자 메소드. Article 객체를 생성할 때 필요한 필드를 매개변수로 받습니다.
    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 팩토리 메소드. new 키워드를 사용하지 않고도 Article 객체를 생성할 수 있게 해줍니다.
    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    // equals 메소드. Article 객체를 비교할 때 사용합니다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    // hashCode 메소드. Article 객체를 비교할 때 사용합니다.
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
