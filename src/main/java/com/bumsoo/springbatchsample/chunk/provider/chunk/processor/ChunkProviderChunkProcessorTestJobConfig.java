package com.bumsoo.springbatchsample.chunk.provider.chunk.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ChunkProviderChunkProcessorTestJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job chunkProviderChunkProcessorTestJob() {
        return jobBuilderFactory
                .get("ChunkProviderChunkProcessorTestJob")
                .incrementer(new RunIdIncrementer())
                .start(chunkProviderChunkProcessorTestStep1())
                .next(chunkProviderChunkProcessorTestStep2())
                .build();
    }

    @Bean
    public Step chunkProviderChunkProcessorTestStep1() {
        return stepBuilderFactory
                .get("chunkProviderChunkProcessorTestStep1")
                .<String, String>chunk(2)
                .reader(new ListItemReader<>(Arrays.asList("item1","item2","item3","item4","item5","item6")))
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String s) throws Exception {
                        return "my_" + s;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> list) throws Exception {
                        list.forEach(s -> System.out.println("[[[s = " + s));
                    }
                })
                .build();
    }


    @Bean
    public Step chunkProviderChunkProcessorTestStep2() {
        return stepBuilderFactory
                .get("chunkProviderChunkProcessorTestStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("[[[ChunkProviderChunkProcessorTestJobConfig.chunkProviderChunkProcessorTestStep2");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
