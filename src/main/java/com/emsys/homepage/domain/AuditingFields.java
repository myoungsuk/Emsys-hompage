package com.emsys.homepage.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// Lombok @Getter 어노테이션은 클래스 필드에 대한 getter 메소드를 자동으로 생성하는 데 사용됩니다.
@Getter

// Lombok @ToString 어노테이션은 toString 메소드를 자동으로 생성하는 데 사용됩니다.
@ToString

// @EntityListeners 어노테이션은 엔티티 또는 매핑된 상위 클래스에 사용할 콜백 리스너 클래스를 지정합니다.
// 여기서는 AuditingEntityListener 클래스와 함께 사용되어 감사(auditing) 기능을 제공합니다.
@EntityListeners(AuditingEntityListener.class)

// @MappedSuperclass 어노테이션은 이 클래스가 다른 엔티티의 기반 클래스로 사용되도록 지시하는 JPA 특정 어노테이션입니다.
// 이 클래스 자체는 직접 데이터베이스에 매핑할 수 없으며 대신 매핑 정보는 이 클래스를 상속하는 엔티티에 적용됩니다.
@MappedSuperclass

// 이 클래스는 혼자서는 엔티티가 될 수 없으며, 다른 클래스가 상속하여 사용하는 부모 클래스입니다.
public abstract class AuditingFields {

    // @DateTimeFormat 어노테이션은 날짜 및 시간 형식을 지정합니다. 여기서는 ISO 형식의 날짜 시간을 지정하고 있습니다.
    // @CreatedDate 어노테이션은 엔티티가 처음 저장될 때 현재 날짜와 시간으로 필드를 자동으로 채웁니다.
    // @Column 어노테이션은 클래스의 필드가 데이터베이스 컬럼에 매핑됨을 나타냅니다. nullable=false는 이 컬럼이 null 값을 가질 수 없음을 의미하고,
    // updatable=false는 이 컬럼의 값을 한 번 설정한 후에는 변경할 수 없음을 의미합니다.
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;    // 생성일시

    // @CreatedBy 어노테이션은 엔티티가 처음 저장될 때 현재 사용자 이름으로 필드를 자동으로 채웁니다.
    @CreatedBy
    @Column(nullable = false, updatable = false, length = 100)
    private String createdBy;           // 생성자

    // @LastModifiedDate 어노테이션은 엔티티가 저장될 때마다 현재 날짜와 시간으로 필드를 자동으로 채웁니다.
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;   // 수정일시

    // @LastModifiedBy 어노테이션은 엔티티가 저장될 때마다 현재 사용자 이름으로 필드를 자동으로 채웁니다.
    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy;          // 수정자
}
