package com.bumsoo.springbatchsample.jpa.paging.item.reader;

import com.bumsoo.springbatchsample.jpa.paging.item.reader.entity.JpaCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class JpaPagingItemReaderTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    Job jpaPagingItemReaderTestJob() {
        return jobBuilderFactory
                .get("jpaPagingItemReaderTestJob")
                .incrementer(new RunIdIncrementer())
                .start(jpaPagingItemReaderTestStep())
                .build();
    }

    @Bean
    Step jpaPagingItemReaderTestStep() {
        return stepBuilderFactory
                .get("jpaPagingItemReaderTestStep")
                .<JpaCustomer, JpaCustomer>chunk(10)
                .reader(jpaPagingItemReaderTestReader())
                .writer(list -> list.forEach(o -> System.out.println(o.getUsername() + " : " + o.getJpaAddress().getLocation())))
                .build();
    }

    @Bean
    ItemReader<JpaCustomer> jpaPagingItemReaderTestReader() {
        return new JpaPagingItemReaderBuilder<JpaCustomer>()
                .name("jpaPagingItemReaderTestReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("select jc from JpaCustomer jc")
                .build();
    }
}
