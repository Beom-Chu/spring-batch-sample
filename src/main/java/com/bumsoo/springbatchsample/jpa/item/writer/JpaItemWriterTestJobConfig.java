package com.bumsoo.springbatchsample.jpa.item.writer;

import com.bumsoo.springbatchsample.dto.Customer2;
import com.bumsoo.springbatchsample.dto.Customer3;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JpaItemWriterTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    Job jpaItemWriterTestJob() {
        return jobBuilderFactory
                .get("jpaItemWriterTestJob")
                .incrementer(new RunIdIncrementer())
                .start(jpaItemWriterTestStep())
                .build();
    }

    @Bean
    Step jpaItemWriterTestStep() {
        return stepBuilderFactory
                .get("jpaItemWriterTestStep")
                .<Customer2, Customer3>chunk(10)
                .reader(jpaItemWriterTestReader())
                .processor(jpaItemWriterTestProcessor())
                .writer(jpaItemWriterTestWriter())
                .build();
    }

    @Bean
    ItemWriter<? super Customer3> jpaItemWriterTestWriter() {
        return new JpaItemWriterBuilder<>()
//                .usePersist(true)
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    ItemProcessor<Customer2, Customer3> jpaItemWriterTestProcessor() {
        return customer2 -> {
            System.out.println("[[[customer2 = " + customer2);
            Customer3 customer3 = new Customer3();
            BeanUtils.copyProperties(customer2, customer3);
            System.out.println("[[[customer3 = " + customer3);
            return customer3;
        };
    }

    @Bean
    ItemReader<Customer2> jpaItemWriterTestReader() {

        Map<String, Object> params = new HashMap<>();
        params.put("firstName" , "A%");

        return new JpaCursorItemReaderBuilder<Customer2>()
                .name("jpaItemWriterTestReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from Customer2 c where firstName like :firstName")
                .parameterValues(params)
                .build();
    }
}
