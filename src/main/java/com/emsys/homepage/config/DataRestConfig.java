// 패키지를 선언하여 이 파일이 어느 디렉토리에 위치해 있는지 정의합니다.
package com.emsys.homepage.config;

// 도메인 패키지에서 UserAccount 클래스를 가져옵니다.
import com.emsys.homepage.domain.UserAccount;

// Bean 어노테이션을 가져옵니다. 이 어노테이션은 빈을 정의하는데 사용됩니다.
import org.springframework.context.annotation.Bean;

// Configuration 어노테이션을 가져옵니다. 이 어노테이션은 클래스가 하나 이상의 @Bean 메소드를 선언한다는 것을 나타냅니다.
import org.springframework.context.annotation.Configuration;

// RepositoryRestConfigurer를 가져옵니다. 이것은 스프링 데이터 REST 구성을 사용자 정의하는데 사용됩니다.
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

// 이 클래스가 구성 클래스임을 나타내는 Configuration 어노테이션.
@Configuration
public class DataRestConfig {

    // RepositoryRestConfigurer 빈을 정의하는 Bean 어노테이션.
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        // UserAccount에 대한 Id들을 노출하는 구성으로 RepositoryRestConfigurer를 반환합니다.
        return RepositoryRestConfigurer.withConfig((config, cors) ->
                // JSON으로 직렬화할 때 UserAccount 클래스의 ID를 노출하도록 구성합니다.
                config.exposeIdsFor(UserAccount.class)
        );
    }

}
