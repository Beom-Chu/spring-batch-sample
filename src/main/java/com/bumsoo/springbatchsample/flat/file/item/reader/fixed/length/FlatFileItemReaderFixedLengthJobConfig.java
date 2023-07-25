package com.bumsoo.springbatchsample.flat.file.item.reader.fixed.length;

import com.bumsoo.springbatchsample.dto.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class FlatFileItemReaderFixedLengthJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flatFileItemReaderFixedLengthJob() {
        return jobBuilderFactory
                .get("flatFileItemReaderFixedLengthJob")
                .incrementer(new RunIdIncrementer())
                .start(flatFileItemReaderFixedLengthStep())
                .build();
    }

    @Bean
    public Step flatFileItemReaderFixedLengthStep() {
        return stepBuilderFactory
                .get("flatFileItemReaderFixedLengthStep")
                .chunk(3)
                .reader(flatFileItemReaderFixedLengthReader())
                .writer(list -> System.out.println("[[[list = " + list))
                .build();
    }

    @Bean
    public FlatFileItemReader<Customer> flatFileItemReaderFixedLengthReader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("flatFileItemReaderFixedLengthReader")
                .resource(new ClassPathResource("/customer_length.csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(Customer.class)
                .linesToSkip(1)
                .fixedLength()
                .addColumns(new Range(1,5))
                .addColumns(new Range(6,9))
                .addColumns(new Range(10,11))
                .names("name", "year", "age")
                .build();
    }
}
