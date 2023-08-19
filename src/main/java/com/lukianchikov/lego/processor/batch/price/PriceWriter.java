package com.lukianchikov.lego.processor.batch.price;

import com.lukianchikov.lego.processor.batch.model.Price;
import com.lukianchikov.lego.processor.batch.writable.WritableResourceProvider;
import org.springframework.batch.item.avro.AvroItemWriter;
import org.springframework.batch.item.avro.builder.AvroItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.IOException;

@Configuration

public class PriceWriter {

    private final WritableResourceProvider<Price> writableResourceProvider;

    @Value("classpath:price-avro-schema.json")
    private Resource avroSchema;

    @Autowired
    public PriceWriter(@Qualifier("WritableResourceProviderPrice") WritableResourceProvider<Price> writableResourceProvider) {
        this.writableResourceProvider = writableResourceProvider;
    }

    @Bean
    public AvroItemWriter<Price> priceAvroWriter() throws IOException {
        WritableResource resource = writableResourceProvider.getWritableResource();

        return new AvroItemWriterBuilder<Price>()
                .name("priceAvroWriter")
                .resource(resource)
                .schema(avroSchema).type(Price.class)
                .build();
    }
}
