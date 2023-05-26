// 패키지를 선언하여 이 파일이 어느 디렉토리에 위치해 있는지 정의합니다.
package com.emsys.homepage.config;

// 필요한 라이브러리들을 임포트합니다.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

// 스프링 시큐리티의 Customizer 클래스를 임포트합니다.
import static org.springframework.security.config.Customizer.withDefaults;

// 이 클래스가 설정 클래스임을 나타내는 Configuration 어노테이션입니다.
@Configuration
public class SecurityConfig{

    // Bean 어노테이션을 사용하여 SecurityFilterChain 빈을 생성합니다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http를 이용하여 접근 권한을 설정합니다.
        http.authorizeRequests(auth ->
                        // 모든 요청을 허용합니다.
                        auth.anyRequest().permitAll()
                )
                // 폼 로그인을 사용하도록 설정합니다.
                .formLogin();

        // 구성한 http 보안 설정을 적용하고 SecurityFilterChain 객체를 반환합니다.
        return http.build();
    }
}
