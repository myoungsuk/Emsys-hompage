package com.emsys.homepage.config;

import com.emsys.homepage.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing // JPA Auditing을 활성화하는 어노테이션입니다.
@Configuration // 설정 파일임을 알리는 어노테이션입니다.
public class JpaConfig {

    @Bean // 빈(Bean) 객체를 생성하여 스프링 컨테이너에 등록하는 역할을 합니다.
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext()) // 현재 SecurityContextHolder의 컨텍스트를 Optional로 감싸서 반환합니다.
                .map(SecurityContext::getAuthentication) // 컨텍스트에서 Authentication 객체를 추출합니다.
                .filter(Authentication::isAuthenticated) // 인증되었는지 확인합니다.
                .map(Authentication::getPrincipal) // Principal 객체를 추출합니다.
                .map(BoardPrincipal.class::cast) // Principal 객체를 BoardPrincipal로 캐스팅합니다.
                .map(BoardPrincipal::getUsername); // BoardPrincipal에서 username을 추출하여 반환합니다.
    }

}