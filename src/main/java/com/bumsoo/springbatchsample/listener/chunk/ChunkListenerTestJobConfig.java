package com.bumsoo.springbatchsample.listener.chunk;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class ChunkListenerTestJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job chunkListenerTestJob() {
        return jobBuilderFactory
                .get("chunkListenerTestJob")
                .incrementer(new RunIdIncrementer())
                .start(chunkListenerTestStep())
                .build();
    }

    @Bean
    Step chunkListenerTestStep() {
        return stepBuilderFactory
                .get("chunkListenerTestStep")
                .<Integer, String>chunk(10)
                .listener(new CustomChunkListener())
                .listener(new CustomItemReadListener())
                .listener(new CustomItemProcessListener())
                .listener(new CustomItemWriterListener())
                .reader(new ListItemReader<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10)))
                .processor((ItemProcessor<Integer, String>) i -> {
//                    throw new Exception("Exception!!");
                    return "item" + i;
                })
                .writer(list -> {
//                    throw new Exception("Exception!!");
                    list.forEach(System.out::println);
                })
                .build();
    }

}
