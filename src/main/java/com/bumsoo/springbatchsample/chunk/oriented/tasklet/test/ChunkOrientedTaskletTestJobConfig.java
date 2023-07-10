package com.bumsoo.springbatchsample.chunk.oriented.tasklet.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ChunkOrientedTaskletTestJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job chunkOrientedTaskletTestJob() {
        return jobBuilderFactory
                .get("chunkOrientedTaskletTestJob")
                .incrementer(new RunIdIncrementer())
                .start(chunkOrientedTaskletTestStep())
                .build();
    }

    @Bean
    public Step chunkOrientedTaskletTestStep() {
        return stepBuilderFactory
                .get("chunkOrientedTaskletTestStep")
                .<String, String>chunk(3)
                .reader(chunkOrientedTaskletReader())
                .processor(chunkOrientedTaskletProcessor())
                .writer(chunkOrientedTaskletWriter())
                .build();
    }

    @Bean
    public ItemReader<String> chunkOrientedTaskletReader() {
        return new ListItemReader<>(List.of("A","B","C","E","F","G"));
    }

    @Bean
    public ItemProcessor<String, String> chunkOrientedTaskletProcessor() {
        return s -> {
            System.out.println("[[[s = " + s);
            return s;
        };
    }

    @Bean
    public ItemWriter<String> chunkOrientedTaskletWriter() {
        return list -> System.out.println("[[[list = " + list);
    }
}
