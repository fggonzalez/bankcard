package com.bank.credit_card.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration

public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()

                .group("credit-card-public")
                .pathsToMatch("/cards/**", "/transactions/**")
                .build();
    }
}