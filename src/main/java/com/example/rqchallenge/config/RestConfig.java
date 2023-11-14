package com.example.rqchallenge.config;

import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplateConfig() {
        return new RestTemplate();
    }

    @Bean
    public JSONParser jsonParser() {
        return new JSONParser();
    }
}
