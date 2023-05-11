package com.emsys.homepage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class EmsysHompageApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmsysHompageApplication.class, args);
    }

}
