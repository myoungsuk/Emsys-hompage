package com.emsys.homepage.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


// Lombok 라이브러리의 @Getter 어노테이션. Getter 메소드를 자동으로 생성해 줍니다.
@Getter

// Lombok 라이브러리의 @ToString 어노테이션. toString 메소드를 자동으로 생성해 줍니다.
// callSuper = true 옵션으로 부모 클래스의 필드들도 출력 내용에 포함시킵니다.
@ToString(callSuper = true)

// 데이터베이스 테이블에 인덱스를 추가합니다. 특정 컬럼의 값을 기준으로 빠르게 데이터를 검색할 수 있습니다.
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})

// JPA 어노테이션. 이 클래스를 데이터베이스의 테이블과 매핑한다는 것을 나타냅니다.
@Entity

// ArticleComment 클래스는 AuditingFields 클래스를 상속받습니다.
public class ArticleComment extends AuditingFields {

    // JPA 어노테이션. 이 필드를 데이터베이스의 기본키(Primary Key)로 설정합니다.
    @Id

    // JPA 어노테이션. 데이터베이스에서 기본키 값을 자동으로 생성하도록 설정합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lombok의 @Setter 어노테이션. Setter 메소드를 자동으로 생성합니다.
    // JPA의 @ManyToOne 어노테이션. Article과 ArticleComment는 다대일 관계를 가집니다.
    @Setter @ManyToOne(optional = false) private Article article;

    // Lombok의 @Setter 어노테이션. Setter 메소드를 자동으로 생성합니다.
    // JPA의 @ManyToOne 어노테이션. UserAccount와 ArticleComment는 다대일 관계를 가집니다.
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount;

    // Lombok의 @Setter 어노테이션. Setter 메소드를 자동으로 생성합니다.
    // JPA의 @Column 어노테이션. content 필드를 데이터베이스의 컬럼으로 매핑합니다. 컬럼의 길이는 최대 500입니다.
    @Setter @Column(nullable = false, length = 500) private String content;

    // protected로 설정된 기본 생성자
    protected ArticleComment() {
    }

    // 생성자 메소드. ArticleComment 객체를 생성할 때 필요한 필드를 매개변수로 받습니다.
    private ArticleComment(Article article, UserAccount userAccount, String content) {
        this.article = article;
        this.userAccount = userAccount;
        this.content = content;
    }

    // 팩토리 메소드. new 키워드를 사용하지 않고도 ArticleComment 객체를 생성할 수 있게 해줍니다.
    public static ArticleComment of(Article article, UserAccount userAccount, String content) {
        return new ArticleComment(article, userAccount, content);
    }

    // equals 메소드. ArticleComment 객체를 비교할 때 사용합니다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    // hashCode 메소드. ArticleComment 객체를 비교할 때 사용합니다.
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
