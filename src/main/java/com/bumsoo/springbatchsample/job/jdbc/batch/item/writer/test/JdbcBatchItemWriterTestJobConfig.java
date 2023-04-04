package com.bumsoo.springbatchsample.job.jdbc.batch.item.writer.test;

import com.bumsoo.springbatchsample.dto.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JdbcBatchItemWriterTestJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Job jdbcBatchItemWriterTestJob() {
        System.out.println("JdbcBatchItemWriterTestJobConfig.jdbcBatchItemWriterTestJob");

        return jobBuilderFactory
                .get("JdbcBatchItemWriterTestJob")
                .incrementer(new RunIdIncrementer())
                .start(jdbcBatchItemWriterTestStep())
                .build();
    }

    @Bean
    public Step jdbcBatchItemWriterTestStep() {
        System.out.println("JdbcBatchItemWriterTestJobConfig.jdbcBatchItemWriterTestStep");

        return stepBuilderFactory
                .get("JdbcBatchItemWriterTestStep")
                .<Customer, Customer>chunk(3)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    public ListItemReader<Customer> itemReader() {
        System.out.println("JdbcBatchItemWriterTestJobConfig.itemReader");

        List<Customer> customers = Arrays.asList(
                new Customer("bum", 15, "2005"),
                new Customer("soo", 20, "2006"),
                new Customer("ji", 25, "2007"),
                new Customer("sun", 30, "2008")
        );

        return new ListItemReader<>(customers);
    }

    public ItemWriter<? super Customer> itemWriter() {
        System.out.println("JdbcBatchItemWriterTestJobConfig.itemWriter");

        String sql = "insert into CUSTOMER values (:name, :age, :year)";
        JdbcBatchItemWriter<Customer> build = new JdbcBatchItemWriterBuilder<Customer>()
                .beanMapped()
                .dataSource(dataSource)
                .sql(sql)
                .build();

        build.afterPropertiesSet(); /* 정합성체크 : NamedParameter를 사용하려면 호출 해줘야 함 */

        return build;
    }
}
