package com.bumsoo.springbatchsample.listener.retry;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RetryListenerTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job retryListenerTestJob() {
        return jobBuilderFactory
                .get("retryListenerTestJob")
                .incrementer(new RunIdIncrementer())
                .start(retryListenerTestStep())
                .build();
    }

    @Bean
    Step retryListenerTestStep() {
        return stepBuilderFactory
                .get("retryListenerTestStep")
                .<Integer, String>chunk(10)
                .reader(retryListenerTestReader())
                .processor(new CustomItemProcessor())
                .writer(new CustomItemWriter())
                .faultTolerant()
                .retry(CustomRetryException.class)
                .retryLimit(2)
                .listener(new CustomRetryListener())
                .build();
    }

    @Bean
    ItemReader<Integer> retryListenerTestReader() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4);
        return new LinkedListItemReader<>(l);
    }
}
