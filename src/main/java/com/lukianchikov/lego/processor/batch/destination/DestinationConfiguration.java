package com.lukianchikov.lego.processor.batch.destination;

import com.lukianchikov.lego.processor.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DestinationConfiguration {

    @Autowired
    private AppProperties appProperties;

    @Bean
    public Destination getDestination() {
        return Destination.of(appProperties);
    }
}
