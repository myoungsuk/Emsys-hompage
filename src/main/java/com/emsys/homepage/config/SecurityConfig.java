package com.emsys.homepage.config;


import com.emsys.homepage.dto.UserAccountDto;
import com.emsys.homepage.dto.security.BoardPrincipal;
import com.emsys.homepage.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration // 현재 클래스를 스프링의 구성(Configuration) 클래스로 지정합니다.
public class SecurityConfig {

    @Bean // SecurityFilterChain 빈을 생성하는 메서드입니다.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 정적 리소스에 대한 요청을 허용합니다.
                        .mvcMatchers(
                                HttpMethod.GET,
                                "/",
                                "/articles",
                                "/articles/search-hashtag"
                        ).permitAll() // 특정 URL에 대한 GET 요청을 허용합니다.
                        .anyRequest().authenticated() // 그 외의 모든 요청은 인증이 필요합니다.
                )
                .formLogin().and() // 폼 로그인 설정을 추가합니다.
                .logout() // 로그아웃 설정을 추가합니다.
                .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트할 URL을 설정합니다.
                .and()
                .build(); // HttpSecurity 설정을 빌드하여 SecurityFilterChain 객체를 반환합니다.
    }

    @Bean // UserDetailsService 빈을 생성하는 메서드입니다.
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
        return username -> userAccountRepository
                .findById(username)
                .map(UserAccountDto::from) // UserAccountDto로 변환합니다.
                .map(BoardPrincipal::from) // BoardPrincipal로 변환합니다.
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username));
    }

    @Bean // PasswordEncoder 빈을 생성하는 메서드입니다.
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // PasswordEncoder 인스턴스를 생성하여 반환합니다.
    }
}
