package br.com.iriscareapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppPropertiesConfig {

    @Bean
    public AppProperties appProperties() {
        return new AppProperties();
    }
}
