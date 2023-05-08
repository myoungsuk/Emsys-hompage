package com.emsys.homepage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing //JPA Auditing 활성화
@Configuration //설정파일임을 알림
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("root"); //TODO: 스프링 시큐리티 인증 기능을 붙이게 될 때, 수정하자//
    }
    // createdBy가 누군지 모르니깐 일단 시큐리티 진행전에 옵셔널로 명석을 호출하게 만들게 시키는 것이다.


}
