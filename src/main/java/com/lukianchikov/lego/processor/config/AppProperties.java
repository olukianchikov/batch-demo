package com.lukianchikov.lego.processor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String s3Bucket;
    private String fsOutput;
    private String destination;
    private String[] inputDateFormats;
    private String timezone;
}
