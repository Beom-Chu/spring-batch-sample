package com.bumsoo.springbatchsample.jpa.cursor.item.reader;

import com.bumsoo.springbatchsample.dto.Customer2;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JpaCursorItemReaderTestJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    Job jpaCursorItemReaderTestJob() {
        return jobBuilderFactory
                .get("jpaCursorItemReaderTestJob")
                .incrementer(new RunIdIncrementer())
                .start(jpaCursorItemReaderTestStep())
                .build();
    }

    @Bean
    Step jpaCursorItemReaderTestStep() {
        return stepBuilderFactory
                .get("jpaCursorItemReaderTestStep")
                .chunk(10)
                .reader(jpaCursorItemReaderTestReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    private ItemReader<Customer2> jpaCursorItemReaderTestReader() {

        Map<String, Object> params = new HashMap<>();
        params.put("firstName" , "A%");

        return new JpaCursorItemReaderBuilder<Customer2>()
                .name("jpaCursorItemReaderTestReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from Customer2 c where firstName like :firstName")
                .parameterValues(params)
                .build();
    }
}
