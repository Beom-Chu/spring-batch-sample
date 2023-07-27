package com.bumsoo.springbatchsample.json.item.reader;

import com.bumsoo.springbatchsample.dto.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class JsonItemReaderConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job jsonItemReaderJob() {
        return jobBuilderFactory
                .get("jsonItemReaderJob")
                .incrementer(new RunIdIncrementer())
                .start(jsonItemReaderStep())
                .build();
    }

    @Bean
    Step jsonItemReaderStep() {
        return stepBuilderFactory
                .get("jsonItemReaderStep")
                .<Customer, Customer>chunk(3)
                .reader(jsonItemReaderReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    private ItemReader<Customer> jsonItemReaderReader() {
        return new JsonItemReaderBuilder<Customer>()
                .name("jsonItemReaderReader")
                .resource(new ClassPathResource("/customer.json"))
                .jsonObjectReader(new JacksonJsonObjectReader<>(Customer.class))
                .build();
    }
}
