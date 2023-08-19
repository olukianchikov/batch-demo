package com.lukianchikov.lego.processor.batch.product;


import com.lukianchikov.lego.processor.batch.exception.ItemInvalidException;
import com.lukianchikov.lego.processor.batch.listener.JobCompletionNotificationListener;
import com.lukianchikov.lego.processor.batch.listener.ProductStepSkipListener;
import com.lukianchikov.lego.processor.batch.model.Product;
import com.lukianchikov.lego.processor.batch.model.ProductIn;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.avro.AvroItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ProductProcessor {

    @Autowired
    private FlatFileItemReader<ProductIn> productItemReader;
    private ProductItemProcessor productItemProcessor;

    @Autowired
    public ProductProcessor(FlatFileItemReader<ProductIn> productItemReader, ProductItemProcessor productItemProcessor) {
        this.productItemReader = productItemReader;
        this.productItemProcessor = productItemProcessor;
    }

    @Bean("processProductJob")
    public Job processProductJob(JobRepository jobRepository,
                                 JobCompletionNotificationListener listener,
                                 Step processProductStep) {
        return new JobBuilder("processProductJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(processProductStep)
                .end()
                .build();
    }

    public SkipListener<ProductIn, Product> skipListener() {
        return new ProductStepSkipListener();
    }

    @Bean
    public Step processProductStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   AvroItemWriter<Product> writer) {
        return new StepBuilder("processProductStep", jobRepository)
                .<ProductIn, Product>chunk(10, transactionManager)
                .reader(this.productItemReader)
                .processor(this.productItemProcessor)
                .writer(writer)
                .faultTolerant()
                .listener(skipListener())
                .skip(ItemInvalidException.class)
                .skip(FlatFileParseException.class)
                .skipLimit(50)
                .build();
    }
}
