package com.bumsoo.springbatchsample.json.file.item.writer;

import com.bumsoo.springbatchsample.dto.Customer2;
import com.bumsoo.springbatchsample.stax.event.item.writer.Customer2RowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JsonFileItemWriterTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    Job jsonFileItemWriterTestJob() {
        return jobBuilderFactory
                .get("jsonFileItemWriterTestJob")
                .incrementer(new RunIdIncrementer())
                .start(jsonFileItemWriterTestStep())
                .build();

    }

    @Bean
    Step jsonFileItemWriterTestStep() {
        return stepBuilderFactory
                .get("jsonFileItemWriterTestStep")
                .<Customer2,Customer2>chunk(10)
                .reader(jsonFileItemWriterTestReader())
                .writer(jsonFileItemWriterTestWriter())
                .build();
    }

    @Bean
    ItemWriter<Customer2> jsonFileItemWriterTestWriter() {
        return new JsonFileItemWriterBuilder<Customer2>()
                .name("jsonFileItemWriterTestWriter")
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource("src\\main\\resources\\jsonFileItemWriterTest.json"))
                .build();
    }


    @Bean
    ItemReader<Customer2> jsonFileItemWriterTestReader() {
        JdbcPagingItemReader<Customer2> reader = new JdbcPagingItemReader<>();

        reader.setDataSource(dataSource);
        reader.setFetchSize(10);
        reader.setRowMapper(new Customer2RowMapper());

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, firstName, lastName, birthDate");
        queryProvider.setFromClause("from customer2");
        queryProvider.setWhereClause("where firstName like :firstName");

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        reader.setQueryProvider(queryProvider);

        HashMap<String, Object> params = new HashMap<>();
        params.put("firstName", "A%");
        reader.setParameterValues(params);

        return reader;
    }
}
