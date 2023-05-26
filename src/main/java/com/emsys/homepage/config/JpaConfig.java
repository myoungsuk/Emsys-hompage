// 패키지를 선언하여 이 파일이 어느 디렉토리에 위치해 있는지 정의합니다.
package com.emsys.homepage.config;

// 필요한 라이브러리들을 임포트합니다.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Java의 Optional 클래스를 임포트합니다. 이 클래스는 null 값을 가질 수 있는 컨테이너 객체입니다.
import java.util.Optional;

// JPA Auditing 기능을 활성화시키는 어노테이션입니다.
@EnableJpaAuditing

// 이 클래스가 설정 클래스임을 나타내는 Configuration 어노테이션입니다.
@Configuration
public class JpaConfig {

    // Bean 어노테이션을 사용하여 auditorAware 빈을 생성합니다.
    @Bean
    public AuditorAware<String> auditorAware() {
        // "root"라는 문자열을 담고 있는 Optional 객체를 반환하는 람다식입니다.
        // 이후에 스프링 시큐리티 인증 기능이 추가되면 이 부분을 수정할 예정이라는 것을 나타내는 주석입니다.
        return () -> Optional.of("root");
    }
}
