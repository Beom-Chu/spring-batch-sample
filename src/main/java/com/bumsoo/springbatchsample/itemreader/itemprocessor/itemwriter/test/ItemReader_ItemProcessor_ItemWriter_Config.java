package com.bumsoo.springbatchsample.itemreader.itemprocessor.itemwriter.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class ItemReader_ItemProcessor_ItemWriter_Config {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job itemReader_ItemProcessor_ItemWriter_Job() {
        return jobBuilderFactory
                .get("itemReader_ItemProcessor_ItemWriter_Job")
                .incrementer(new RunIdIncrementer())
                .start(itemReader_ItemProcessor_ItemWriter_Step())
                .build();
    }

    @Bean
    public Step itemReader_ItemProcessor_ItemWriter_Step() {
        return stepBuilderFactory
                .get("itemReader_ItemProcessor_ItemWriter_Step")
                .<Customer, Customer>chunk(10)
                .reader(itemReader_ItemProcessor_ItemWriter_Reader())
                .processor(itemReader_ItemProcessor_ItemWriter_Processor())
                .writer(itemReader_ItemProcessor_ItemWriter_Writer())
                .build();
    }

    @Bean
    public ItemReader<Customer> itemReader_ItemProcessor_ItemWriter_Reader() {
        return new CustomItemReader(Arrays.asList(new Customer("name1"),new Customer("name2"),new Customer("name3")));
    }

    @Bean
    public ItemProcessor<Customer, Customer> itemReader_ItemProcessor_ItemWriter_Processor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter<Customer> itemReader_ItemProcessor_ItemWriter_Writer() {
        return new CustomIterWriter();
    }
}
