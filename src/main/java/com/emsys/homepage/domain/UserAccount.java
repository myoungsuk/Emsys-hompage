package com.emsys.homepage.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter  // Lombok 라이브러리를 사용하여 getter 메소드를 자동으로 생성합니다.
@ToString(callSuper = true)  // Lombok 라이브러리를 사용하여 toString 메소드를 자동으로 생성합니다.
@Table(indexes = {
        @Index(columnList = "email", unique = true),  // 테이블에 'email'에 대한 유니크 인덱스를 생성합니다.
        @Index(columnList = "createdAt"),  // 'createdAt'에 대한 인덱스를 생성합니다.
        @Index(columnList = "createdBy")  // 'createdBy'에 대한 인덱스를 생성합니다.
})
@Entity  // JPA를 사용하여 이 클래스를 데이터베이스 테이블에 매핑합니다.
public class UserAccount extends AuditingFields {  // AuditingFields를 상속하는 UserAccount 클래스를 정의합니다.
    @Id  // 'userId' 필드를 기본 키로 지정합니다.
    @Column(length = 50)  // 'userId' 칼럼의 최대 길이를 50으로 지정합니다.

    private String userId;  // userId 필드를 선언합니다.

    @Setter @Column(nullable = false) private String userPassword;  // 비밀번호 필드를 선언하고, 이 필드는 null을 허용하지 않습니다.

    @Setter @Column(length = 100) private String email;  // email 필드를 선언하고, 이 칼럼의 최대 길이를 100으로 지정합니다.
    @Setter @Column(length = 100) private String nickname;  // nickname 필드를 선언하고, 이 칼럼의 최대 길이를 100으로 지정합니다.
    @Setter private String memo;  // memo 필드를 선언합니다.


    protected UserAccount() {}  // 기본 생성자를 protected로 선언합니다. 이는 JPA 스펙에 따른 것입니다.


    private UserAccount(String userId, String userPassword, String email, String nickname, String memo, String createdBy) {  // 모든 필드를 초기화하는 생성자를 선언합니다.
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }
//    이는 모든 필드를 초기화하는 생성자이다 private 으로 선언되었는데 이는 객체 생성을 정적 팩토리 메서드를 통해서만
//    가능하도록 제한하기 위함이다


    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {  // static factory method를 선언하여 UserAccount 객체를 생성합니다.
        return new UserAccount(userId, userPassword, email, nickname, memo, null);
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo, String createdBy) {
        return new UserAccount(userId, userPassword, email, nickname, memo, createdBy);
    }
    //정적 팩토리 메서드이다. 이름을 가질 수 있어 생정자에 비해 가독성이 좋고 호출할 때마다 새로운 객체를 생성하지 않아도 된다.
    //또한 반환 타입의 하위 타입 객체를 반환할 수 있는 유연성을 가지고 있다.

    @Override
    public boolean equals(Object o) {  // equals 메소드를 오버라이드 합니다.
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return this.getUserId() != null && this.getUserId().equals(that.getUserId());
    }
    //이 메서드는 객체 동일성을 판단하기 위해 오버리아드 된 'equals()' 메서드입니다. 여기서는 'userId'가 같은지를
    //비교하여 객체 동일성을 판단합니다. 이 메서들르 오버라이드 하지 않으면, 객체의 레퍼런스를 비교하는 기본
    //'equals()'메서드가 사용되어 의도치 않은 결과가 나올 수 있습니다.

    @Override
    public int hashCode() {  // hashCode 메소드를 오버라이드 합니다.
        return Objects.hash(this.getUserId());
    }
    // 이 메서드는 객체의 해시 코드를 생성하기 위해 오버라이드 된 메서드입니다.
    // 'equals()' 메서드가 'true' 를 반환하는 두 객체는 같은 해시 코드를 반환해야 합니다. 따라서
    // 'equals()' 를 오버라이드 하면 'hashCode()' 도 함께 오버라이드 해야합니다.
    // 그렇지 않으면 해시 기반의 컬렉션에서 의도치 않은 동작을 할 수 있습니다.
}
