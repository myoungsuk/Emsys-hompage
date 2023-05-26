package com.emsys.homepage.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter // 이 어노테이션은 Getter 메소드를 자동으로 생성합니다.
@ToString // 이 어노테이션은 toString 메소드를 자동으로 생성합니다.
@Table(indexes = { // 이 테이블에 인덱스를 추가합니다.
        @Index(columnList = "userId", unique = true), // 유니크한 'userId'에 인덱스를 추가합니다.
        @Index(columnList = "email", unique = true), // 유니크한 'email'에 인덱스를 추가합니다.
        @Index(columnList = "createdAt"), // 'createdAt'에 인덱스를 추가합니다.
        @Index(columnList = "createdBy") // 'createdBy'에 인덱스를 추가합니다.
})
@Entity // 이 클래스를 JPA 엔티티로 선언합니다.
public class UserAccount extends AuditingFields { // 'AuditingFields'를 상속받아 공통 필드를 재사용합니다.
    @Id // 'userId'를 primary key로 지정합니다.
    @Column(length = 50) private String userId; // 'userId' 컬럼의 최대 길이를 50으로 지정합니다.
    @Setter @Column(nullable = false) private String userPassword; // null 값을 허용하지 않는 'userPassword' 컬럼을 정의하고 Setter를 생성합니다.

    @Setter @Column(length = 100) private String email; // 'email' 컬럼의 최대 길이를 100으로 지정하고 Setter를 생성합니다.
    @Setter @Column(length = 100) private String nickname; // 'nickname' 컬럼의 최대 길이를 100으로 지정하고 Setter를 생성합니다.
    @Setter private String memo; // 'memo' 컬럼을 정의하고 Setter를 생성합니다.

    protected UserAccount() {} // 기본 생성자. JPA에서 필요로 합니다.

    // 사용자가 필요한 필드를 지정하여 객체를 생성하는 생성자
    private UserAccount(String userId, String userPassword, String email, String nickname, String memo) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }

    // 팩토리 메소드. 이 메소드를 통해 UserAccount 객체를 생성할 수 있습니다.
    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
        return new UserAccount(userId, userPassword, email, nickname, memo);
    }

    // 동등성을 판단하기 위한 equals 메소드. 'userId'를 기반으로 판단합니다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount userAccount)) return false;
        return userId != null && userId.equals(userAccount.userId);
    }

    // hashCode 메소드. 'userId'를 기반으로 해시코드를 생성합니다.
    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
