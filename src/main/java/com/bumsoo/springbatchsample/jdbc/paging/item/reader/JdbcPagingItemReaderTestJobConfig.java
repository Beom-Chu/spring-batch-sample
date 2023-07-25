package com.bumsoo.springbatchsample.jdbc.paging.item.reader;

import com.bumsoo.springbatchsample.dto.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JdbcPagingItemReaderTestJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Job jdbcPagingItemReaderTestJob() throws Exception {
        return jobBuilderFactory
                .get("JdbcPagingItemReaderTestJob")
                .start(jdbcPagingItemReaderTestStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step jdbcPagingItemReaderTestStep() throws Exception {
        return stepBuilderFactory
                .get("jdbcPagingItemReaderTestStep")
                .<Customer, Customer>chunk(5)
                .reader(jdbcPagingItemReaderTestReader())
                .writer(list -> System.out.println("[[[list = " + list))
                .build();
    }

    @Bean
    public JdbcPagingItemReader<Customer> jdbcPagingItemReaderTestReader() throws Exception {
        HashMap<String, Object> param = new HashMap<>();
        param.put("age", "20");
        param.put("year", "2005");

        return new JdbcPagingItemReaderBuilder<Customer>()
                .name("jdbcPagingItemReaderTestReader")
                .pageSize(5)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Customer.class))
                .queryProvider(jdbcPagingItemReaderTestQueryProvider())
                .parameterValues(param)
                .build();
    }

    @Bean
    public PagingQueryProvider jdbcPagingItemReaderTestQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);

        queryProvider.setSelectClause("name, age, year");
        queryProvider.setFromClause("CUSTOMER");
        queryProvider.setWhereClause("age >= :age and year <= :year");
        queryProvider.setSortKey("age");

        return queryProvider.getObject();
    }
}
