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


@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "content"), // "content" 필드에 대한 인덱스 설정
        @Index(columnList = "createdAt"), // "createdAt" 필드에 대한 인덱스 설정
        @Index(columnList = "createdBy") // "createdBy" 필드에 대한 인덱스 설정
})
@Entity
public class ArticleComment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 ID

    @Setter
    @ManyToOne(optional = false)
    private Article article; // 댓글이 속한 게시글

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private UserAccount userAccount; // 댓글 작성자

    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 댓글 내용

    protected ArticleComment() {
    }

    private ArticleComment(Article article, UserAccount userAccount, String content) {
        this.article = article;
        this.userAccount = userAccount;
        this.content = content;
    }

    /**
     * 댓글 객체 생성을 위한 factory method
     *
     * @param article     댓글이 속한 게시글
     * @param userAccount 댓글 작성자
     * @param content     댓글 내용
     * @return 생성된 ArticleComment 객체
     */
    public static ArticleComment of(Article article, UserAccount userAccount, String content) {
        return new ArticleComment(article, userAccount, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
