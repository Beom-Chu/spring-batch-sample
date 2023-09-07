package com.bumsoo.springbatchsample.retry.template;

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
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RetryTemplateTestJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job retryTemplateTestJob() {
        return jobBuilderFactory
                .get("retryTemplateTestJob")
                .incrementer(new RunIdIncrementer())
                .start(retryTemplateTestStep())
                .build();
    }

    @Bean
    Step retryTemplateTestStep() {
        return stepBuilderFactory
                .get("retryTemplateTestStep")
                .<String, Customer>chunk(5)
                .reader(retryTemplateTestReader())
                .processor(retryTemplateTestProcessor())
                .writer(list -> list.forEach(System.out::println))
                .faultTolerant()
                .skip(RetryableException.class)
                .skipLimit(2)
                .build();
    }

    @Bean
    ItemReader<String> retryTemplateTestReader() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            items.add(String.valueOf(i));
        }
        return new ListItemReader<>(items);
    }

    @Bean
    ItemProcessor<String, Customer> retryTemplateTestProcessor() {
        return new RetryTemplateTestProcessor();
    }


    @Bean
    RetryTemplate retryTemplateTestRetryTemplate() {
        Map<Class<? extends Throwable>, Boolean> exceptionClass = new HashMap<>();
        exceptionClass.put(RetryableException.class, true);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(2000);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(2, exceptionClass);
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}
