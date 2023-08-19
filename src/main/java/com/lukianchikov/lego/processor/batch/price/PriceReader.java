package com.lukianchikov.lego.processor.batch.price;

import com.lukianchikov.lego.processor.batch.model.PriceIn;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class PriceReader {

    @Bean
    @StepScope
    public JsonItemReader<PriceIn> priceItemReader(@Value("#{jobParameters[pricesFilePath]}") String pathToFile) {
        return new JsonItemReaderBuilder<PriceIn>()
                .name("priceItemReader")
                .jsonObjectReader(new NestedArrayJsonReader<PriceIn>(PriceIn.class, "products"))
                .resource(new FileSystemResource(pathToFile))
                .build();
    }
}
