package com.lukianchikov.lego.processor.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({AppProperties.class})
public class AppConfig {
}

