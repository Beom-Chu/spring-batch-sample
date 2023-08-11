package com.bumsoo.springbatchsample.flat.file.item.writer;

import com.bumsoo.springbatchsample.dto.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FlatFileItemWriterTestConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flatFileItemWriterTestJob() {
        return jobBuilderFactory
                .get("flatFileItemWriterTestJob")
                .start(flatFileItemWriterTestStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step flatFileItemWriterTestStep() {
        return stepBuilderFactory
                .get("flatFileItemWriterTestStep")
                .<Customer,Customer>chunk(2)
                .reader(flatFileItemWriterTestItemReader())
//                .writer(flatFileItemWriterTestItemWriter_delimited())
                .writer(flatFileItemWriterTestItemWriter_format())
                .build();
    }

    @Bean
    public ItemReader<? extends Customer> flatFileItemWriterTestItemReader() {
        System.out.println("[[[FlatFileItemWriterTestConfig.flatFileItemWriterTestItemReader");
        List<Customer> customers = Arrays.asList(
                new Customer("bum", 15, "2005"),
                new Customer("soo", 20, "2006"),
                new Customer("ji", 25, "2007"),
                new Customer("sum", 30, "2008")
        );

        return new ListItemReader<>(customers);
    }

    /**
     * delimited
     */
    @Bean
    public ItemWriter<? super Customer> flatFileItemWriterTestItemWriter_delimited() {
        System.out.println("[[[FlatFileItemWriterTestConfig.flatFileItemWriterTestItemWriter_delimited");
        return new FlatFileItemWriterBuilder<>()
                .name("flatFileItemWriterTestItemWriter_delimited")
                .resource(new FileSystemResource("src\\main\\resources\\FlatFileWriterTest.csv"))
                .delimited().delimiter(",")
                .names("name", "age", "year")
//                .append(true) /* 존재하는 파일에 내용 추가하기 */
//                .shouldDeleteIfExists(true) /* 파일이 이미 존재하면 삭제 */
//                .shouldDeleteIfEmpty(true) /* 파일 내용이 비어 있으면 삭제 */
                .build();
    }

    /**
     * format
     */
    @Bean
    public ItemWriter<? super Customer> flatFileItemWriterTestItemWriter_format() {
        System.out.println("[[[FlatFileItemWriterTestConfig.flatFileItemWriterTestItemWriter_format");
        return new FlatFileItemWriterBuilder<>()
                .name("flatFileItemWriterTestItemWriter_format")
                .resource(new FileSystemResource("src\\main\\resources\\FlatFileWriterTest_format.txt"))
                .formatted()
                .format("%-4s%-3d%-5s")
                .names("name", "age", "year")
                .append(true) /* 존재하는 파일에 내용 추가하기 */
//                .shouldDeleteIfExists(true) /* 파일이 이미 존재하면 삭제 */
//                .shouldDeleteIfEmpty(true) /* 파일 내용이 비어 있으면 삭제 */
                .build();
    }
}
