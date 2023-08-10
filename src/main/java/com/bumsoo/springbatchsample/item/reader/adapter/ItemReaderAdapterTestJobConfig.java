package com.bumsoo.springbatchsample.item.reader.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ItemReaderAdapterTestJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job itemReaderAdapterTestJob() {
        return jobBuilderFactory
                .get("itemReaderAdapterTestJob")
                .incrementer(new RunIdIncrementer())
                .start(itemReaderAdapterTestStep())
                .build();
    }

    @Bean
    Step itemReaderAdapterTestStep() {
        return stepBuilderFactory
                .get("itemReaderAdapterTest")
                .chunk(10)
                .reader(itemReaderAdapterTestReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    @Bean
    ItemReaderAdapter<String> itemReaderAdapterTestReader() {
        ItemReaderAdapter<String> reader = new ItemReaderAdapter<>();

        reader.setTargetObject(itemReaderAdapterTestService());
        reader.setTargetMethod("joinMember");

        return reader;
    }

    private CustomerService<String> itemReaderAdapterTestService() {
        return new CustomerService<>();
    }
}
