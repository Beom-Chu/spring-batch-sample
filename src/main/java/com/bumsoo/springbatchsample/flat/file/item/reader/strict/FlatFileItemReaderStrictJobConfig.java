package com.bumsoo.springbatchsample.flat.file.item.reader.strict;

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
public class FlatFileItemReaderStrictJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flatFileItemReaderStrictJob() {
        return jobBuilderFactory
                .get("flatFileItemReaderStrictJob")
                .incrementer(new RunIdIncrementer())
                .start(flatFileItemReaderStrictStep())
                .build();
    }

    @Bean
    public Step flatFileItemReaderStrictStep() {
        return stepBuilderFactory
                .get("flatFileItemReaderStrictStep")
                .chunk(3)
                .reader(flatFileItemReaderStrictReader())
                .writer(list -> System.out.println("[[[list = " + list))
                .build();
    }

    @Bean
    public FlatFileItemReader<Customer> flatFileItemReaderStrictReader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("flatFileItemReaderStrictReader")
                .resource(new ClassPathResource("/customer_length_strict.csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(Customer.class)
                .linesToSkip(1)
                .fixedLength()
                .strict(false) /* Tokenizer가 라인 길이를 검증하지 않음 */
                .addColumns(new Range(1,5))
                .addColumns(new Range(6,9))
                .addColumns(new Range(10,11))
                .names("name", "year", "age")
                .build();
    }
}
