package com.bumsoo.springbatchsample.retry.api;

import com.bumsoo.springbatchsample.retry.RetryableException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RetryApiTestJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job retryTestJob() {
        return jobBuilderFactory
                .get("retryApiTestJob")
                .incrementer(new RunIdIncrementer())
                .start(retryApiTestStep())
                .build();
    }

    @Bean
    Step retryApiTestStep() {
        return stepBuilderFactory
                .get("retryApiTestStep")
                .<String, String>chunk(5)
                .reader(retryApiTestReader())
                .processor(retryApiTestProcessor())
                .writer(list -> list.forEach(System.out::println))
                .faultTolerant()
                .skip(RetryableException.class)
                .skipLimit(2)
//                .retry(RetryableException.class)
//                .retryLimit(2)
                .retryPolicy(retryApiTestRetryPolicy())
                .build();
    }

    @Bean
    ItemProcessor<? super String, String> retryApiTestProcessor() {
        return new RetryApiTestProcessor();
    }

    @Bean
    ItemReader<String> retryApiTestReader() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            items.add(String.valueOf(i));
        }
        return new ListItemReader<>(items);
    }

    @Bean
    RetryPolicy retryApiTestRetryPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptionClass = new HashMap<>();
        exceptionClass.put(RetryableException.class, true);

        return new SimpleRetryPolicy(2, exceptionClass);
    }
}
