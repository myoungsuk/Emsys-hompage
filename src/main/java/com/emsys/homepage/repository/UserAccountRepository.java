package com.emsys.homepage.repository;

import com.emsys.homepage.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, String>{  // UserAccount 엔티티에 대한 레포지터리 인터페이스를 정의함. JpaRepository를 상속하여 스프링 데이터 JPA에서 제공하는 기본 CRUD 기능을 사용할 수 있게 함.
}
