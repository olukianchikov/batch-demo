package com.lukianchikov.lego.processor.batch.price;

import com.lukianchikov.lego.processor.batch.exception.ItemInvalidException;
import com.lukianchikov.lego.processor.batch.listener.JobCompletionNotificationListener;
import com.lukianchikov.lego.processor.batch.listener.PriceStepSkipListener;
import com.lukianchikov.lego.processor.batch.model.Price;
import com.lukianchikov.lego.processor.batch.model.PriceIn;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.avro.AvroItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PriceProcessor {

    private JsonItemReader<PriceIn> priceItemReader;
    private PriceItemProcessor priceItemProcessor;

    @Autowired
    public PriceProcessor(JsonItemReader<PriceIn> priceItemReader, PriceItemProcessor priceItemProcessor) {
        this.priceItemReader = priceItemReader;
        this.priceItemProcessor = priceItemProcessor;
    }

    @Bean("processPriceJob")
    public Job processPriceJob(JobRepository jobRepository,
                               JobCompletionNotificationListener listener,
                               Step processPriceStep) {
        return new JobBuilder("processPriceJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(processPriceStep)
                .end()
                .build();
    }


    public SkipListener<PriceIn, Price> skipListener() {
        return new PriceStepSkipListener();
    }


    @Bean
    public Step processPriceStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 AvroItemWriter<Price> writer) {
        return new StepBuilder("processPriceStep", jobRepository)
                .<PriceIn, Price>chunk(10, transactionManager)
                .reader(priceItemReader)
                .processor(this.priceItemProcessor)
                .writer(writer)
                .faultTolerant()
                .listener(skipListener())
                .skip(ItemInvalidException.class)
                .skipLimit(50)
                .build();
    }

}
