package com.bumsoo.springbatchsample.chunk.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ChunkConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job chunkTestJob() {
        return jobBuilderFactory
                .get("chunkTestJob")
                .start(chunkTestStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    /* chunk를 사용하면 reader, processor는 개별 처리되지만
    * writer는 chunk size 만큼의 list로 처리가 된다.
    * Transaction도 chunk size 단위로 처리 */
    @Bean
    public Step chunkTestStep() {
        return stepBuilderFactory
                .get("chunkTestStep")
                .<String, String> chunk(3)
                .reader(new ListItemReader<>(Arrays.asList("item1","item2","item3","item4","item5")))
                .processor((ItemProcessor<? super String, ? extends String>) s -> {
                    System.out.println("[[[processor : s = " + s);
                    return s;
                })
                .writer(list -> {
                    System.out.println("[[[writer : list = " + list);
                })
                .build();
    }
}
