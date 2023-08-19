package com.lukianchikov.lego.processor.batch.product;

import com.lukianchikov.lego.processor.batch.model.ProductIn;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ProductReader {

    @Bean
    @StepScope
    public FlatFileItemReader<ProductIn> productItemReader(@Value("#{jobParameters[productsFilePath]}") String pathToFile) {
        return new FlatFileItemReaderBuilder<ProductIn>()
                .name("productItemReader")
                .resource(new FileSystemResource(pathToFile))
                .delimited()
                .names("id", "name", "stock", "created", "updated")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<ProductIn>() {{
                    setTargetType(ProductIn.class);
                }})
                .linesToSkip(1)
                .build();
    }

}
