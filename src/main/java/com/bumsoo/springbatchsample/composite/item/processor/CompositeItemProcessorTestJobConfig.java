package com.bumsoo.springbatchsample.composite.item.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CompositeItemProcessorTestJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job compositeItemProcessorTestJob() {
        return jobBuilderFactory
                .get("compositeItemProcessorTestJob")
                .incrementer(new RunIdIncrementer())
                .start(compositeItemProcessorTestStep())
                .build();
    }

    @Bean
    Step compositeItemProcessorTestStep() {
        return stepBuilderFactory
                .get("compositeItemProcessorTestStep")
                .<String, String>chunk(10)
                .reader(new ItemReader<>() {
                    int i = 0;

                    @Override
                    public String read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        return i > 10 ? null : "item";
                    }
                })
                .processor(compositeItemProcessorTestProcessor())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    @Bean
    ItemProcessor<? super String, String> compositeItemProcessorTestProcessor() {

        List itemProcessor = new ArrayList<>();
        itemProcessor.add(new CompositeItemProcessor1());
        itemProcessor.add(new CompositeItemProcessor2());

        return new CompositeItemProcessorBuilder<>()
                .delegates(itemProcessor)
                .build();
    }
}
