package com.bumsoo.springbatchsample.jdbc.cursor.item.reader;

import com.bumsoo.springbatchsample.dto.Customer2;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcCursorItemReaderTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private int chunkSize = 10;
    private final DataSource dataSource;

    @Bean
    Job jdbcCursorItemReaderTestJob() {
        return jobBuilderFactory
                .get("jdbcCursorItemReaderTestJob")
                .incrementer(new RunIdIncrementer())
                .start(jdbcCursorItemReaderTestStep())
                .build();
    }

    @Bean
    Step jdbcCursorItemReaderTestStep() {
        return stepBuilderFactory
                .get("jdbcCursorItemReaderTestStep")
                .chunk(chunkSize)
                .reader(jdbcCursorItemReaderTestReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    @Bean
    ItemReader<Customer2> jdbcCursorItemReaderTestReader() {
        return new JdbcCursorItemReaderBuilder<Customer2>()
                .name("jdbcCursorItemReaderTestReader")
                .fetchSize(chunkSize)
                .sql("select id, firstName, lastName, birthDate from customer2 where firstName like ? order by lastName, firstName")
                .beanRowMapper(Customer2.class)
                .queryArguments("A%")
                .dataSource(dataSource)
                .build()
                ;
    }
}
