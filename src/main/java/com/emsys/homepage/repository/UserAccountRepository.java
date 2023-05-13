package com.emsys.homepage.repository;

import com.emsys.homepage.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, String>{

}
