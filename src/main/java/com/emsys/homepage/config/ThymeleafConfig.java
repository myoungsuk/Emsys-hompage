// 패키지를 선언하여 이 파일이 어느 디렉토리에 위치해 있는지 정의합니다.
package com.emsys.homepage.config;

// Lombok 라이브러리의 Getter와 RequiredArgsConstructor 어노테이션을 임포트합니다.
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 스프링 부트의 ConfigurationProperties와 ConstructorBinding 어노테이션을 임포트합니다.
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

// 스프링의 Bean과 Configuration 어노테이션을 임포트합니다.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Thymeleaf 템플릿 리졸버를 임포트합니다.
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

// 설정 클래스임을 나타내는 Configuration 어노테이션입니다.
@Configuration
public class ThymeleafConfig {

    // Thymeleaf 템플릿 리졸버 빈을 생성하는 메소드입니다.
    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        // 기본 템플릿 리졸버에 decoupled logic 사용 여부를 설정합니다.
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());

        // 설정이 완료된 템플릿 리졸버를 반환합니다.
        return defaultTemplateResolver;
    }

    // 필요한 필드가 있는 생성자를 자동으로 만드는 Lombok 어노테이션입니다.
    @RequiredArgsConstructor
    // 모든 필드에 대한 getter를 생성하는 Lombok 어노테이션입니다.
    @Getter
    // 생성자 바인딩을 사용하는 어노테이션입니다.
    @ConstructorBinding
    // 설정 프로퍼티에 접근하는 클래스에 붙이는 어노테이션입니다.
    @ConfigurationProperties("spring.thymeleaf3")
    public static class Thymeleaf3Properties {
        /**
         * Thymeleaf 3 Decoupled Logic를 사용하도록 설정합니다.
         */
        private final boolean decoupledLogic;
    }
}
