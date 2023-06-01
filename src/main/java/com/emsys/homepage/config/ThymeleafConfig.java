package com.emsys.homepage.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration // 현재 클래스를 스프링의 구성(Configuration) 클래스로 지정합니다.
public class ThymeleafConfig {

    @Bean // SpringResourceTemplateResolver 빈을 생성하는 메서드입니다.
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic()); // Decoupled Logic 사용 여부를 설정합니다.

        return defaultTemplateResolver; // 설정된 SpringResourceTemplateResolver 객체를 반환합니다.
    }

    @RequiredArgsConstructor // 필수 인자를 가지는 생성자를 자동으로 생성해주는 어노테이션입니다.
    @Getter // 필드의 Getter 메서드를 자동으로 생성해주는 어노테이션입니다.
    @ConstructorBinding // 생성자 주입을 위한 어노테이션입니다.
    @ConfigurationProperties("spring.thymeleaf3") // application.yml 파일의 'spring.thymeleaf3' 프로퍼티에 바인딩합니다.
    public static class Thymeleaf3Properties {
        /**
         * Use Thymeleaf 3 Decoupled Logic
         */
        private final boolean decoupledLogic; // Decoupled Logic 사용 여부를 저장하는 필드입니다.
    }
}