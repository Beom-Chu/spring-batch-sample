package com.bumsoo.springbatchsample.stax.event.item.writer;

import com.bumsoo.springbatchsample.dto.Customer2;
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
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class StaxEventItemWriterTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    Job staxEventItemWriterTestJob() {
        return jobBuilderFactory
                .get("staxEventItemWriterTestJob")
                .incrementer(new RunIdIncrementer())
                .start(staxEventItemWriterTestStep())
                .build();

    }

    @Bean
    Step staxEventItemWriterTestStep() {
        return stepBuilderFactory
                .get("staxEventItemWriterTestStep")
                .<Customer2,Customer2>chunk(10)
                .reader(staxEventItemWriterTestReader())
                .writer(staxEventItemWriterTestWriter())
                .build();
    }

    @Bean
    ItemWriter<Customer2> staxEventItemWriterTestWriter() {
        return new StaxEventItemWriterBuilder<Customer2>()
                .name("staxEventItemWriterTestWriter")
                .marshaller(staxEventItemWriterTestMarshaller())
                .resource(new FileSystemResource("src\\main\\resources\\StaxEventItemWriterTest.xml"))
                .rootTagName("customer2")
                .overwriteOutput(true)
                .build();
    }

    @Bean
    Marshaller staxEventItemWriterTestMarshaller() {
        Map<String, Class<?>> aliases = new HashMap<>();
        aliases.put("customer2", Customer2.class);
        aliases.put("id", Long.class);
        aliases.put("firstName", String.class);
        aliases.put("lastName", String.class);
        aliases.put("birthDate", String.class);

        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setAliases(aliases);

        return xStreamMarshaller;
    }

    @Bean
    ItemReader<Customer2> staxEventItemWriterTestReader() {
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
