package com.emsys.homepage.config;

import com.emsys.homepage.domain.UserAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration // 현재 클래스를 스프링 구성(Configuration) 클래스로 지정합니다.
public class DataRestConfig {

    @Bean // repositoryRestConfigurer() 메서드가 반환하는 객체를 빈(Bean)으로 등록합니다.
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig((config, cors) ->
                config.exposeIdsFor(UserAccount.class) // UserAccount 클래스의 ID 속성을 노출합니다.
        );
    }

}