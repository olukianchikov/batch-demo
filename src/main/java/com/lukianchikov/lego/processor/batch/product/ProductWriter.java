package com.lukianchikov.lego.processor.batch.product;

import com.lukianchikov.lego.processor.batch.model.Product;
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
public class ProductWriter {

    private final WritableResourceProvider<Product> writableResourceProvider;

    @Value("classpath:product-avro-schema.json")
    private Resource avroSchema;


    @Autowired
    public ProductWriter(@Qualifier("WritableResourceProviderProduct") WritableResourceProvider<Product> writableResourceProvider) {
        this.writableResourceProvider = writableResourceProvider;
    }

    @Bean(name = "productAvroWriter", destroyMethod = "")
    public AvroItemWriter<Product> productAvroWriter() throws IOException {
        WritableResource resource = writableResourceProvider.getWritableResource();

        return new AvroItemWriterBuilder<Product>()
                .name("productAvroWriter")
                .resource(resource)
                .schema(avroSchema).type(Product.class)
                .build();
    }
}
