package com.bumsoo.springbatchsample.flat.file.item.reader.test;

import com.bumsoo.springbatchsample.dto.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FlatFileItemReaderTestConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flatFileItemReaderTestJob() {
        return jobBuilderFactory
                .get("flatFileItemReaderTestJob")
                .start(flatFileItemReaderTestStep1())
                .build();
    }


    @Bean
    public Step flatFileItemReaderTestStep1() {
        return stepBuilderFactory
                .get("flatFileItemReaderTestStep1")
                .chunk(5)
                .reader(itemReader())
                .writer(list -> System.out.println("[[[list = " + list))
                .build();
    }

    @Bean
    public FlatFileItemReader itemReader() {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("/customer.csv"));

        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        lineMapper.setFieldSetMapper(new CustomerFieldSetMapper());

        itemReader.setLineMapper(lineMapper);
        itemReader.setLinesToSkip(1);

        return itemReader;
    }

}
