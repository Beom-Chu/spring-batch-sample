package com.bumsoo.springbatchsample.chunk.composite.writer;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TestCompositeWriterJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job testCompositeWriterJob() {
        return jobBuilderFactory
                .get("TestCompositeWriterJob")
                .incrementer(new RunIdIncrementer())
                .start(testCompositeWriterStep())
                .build();
    }

    @Bean
    public Step testCompositeWriterStep() {
        return stepBuilderFactory
                .get("testCompositeWriterStep")
                .<Integer, Integer>chunk(1000)
                .reader(new ListItemReader<>(List.of(1,2,3,4,5)))
                .writer(compositeWriter())
                .build();
    }

    @Bean
    public ItemWriter<Integer> compositeWriter() {
        return items -> {
            // CompositeItemWriter를 생성하고 Writer1과 Writer2를 등록합니다.
            CompositeItemWriter<Integer> compositeWriter = new CompositeItemWriter<>();
            compositeWriter.setDelegates(Arrays.asList(writer1(), writer2()));

            // 등록된 Writer들에게 아이템을 전달하여 처리합니다.
            compositeWriter.write(items);
        };
    }


    @Bean
    public ItemWriter<Integer> writer1() {
        return items -> {
            // Writer1의 처리 로직을 구현합니다.
            System.out.println("Writer1 처리: " + items);
        };
    }

    @Bean
    public ItemWriter<Integer> writer2() {
        return items -> {
            // Writer2의 처리 로직을 구현합니다.
            System.out.println("Writer2 처리");
            items.forEach(System.out::println);
        };
    }
}
