package com.bumsoo.springbatchsample.item.stream.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ItemStreamTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job itemStreamTestJob() {
        return jobBuilderFactory
                .get("itemStreamTestJob")
                .start(itemStreamTestStep())
                .build();
    }

    @Bean
    public Step itemStreamTestStep() {
        return stepBuilderFactory
                .get("itemStreamTestStep")
                .<String, String>chunk(5)
                .reader(itemStreamTestReader())
                .writer(itemStreamTestWriter())
                .build();
    }

    private CustomItemStreamReader itemStreamTestReader() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(String.valueOf(i));
        }
        return new CustomItemStreamReader(items);
    }

    public ItemWriter<? super String> itemStreamTestWriter() {
        return new CustomItemStreamWriter();
    }
}
