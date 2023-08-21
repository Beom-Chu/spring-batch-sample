package com.bumsoo.springbatchsample.item.writer.adpater;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ItemWriterAdapterTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job itemWriterAdapterTestJob() {
        return jobBuilderFactory
                .get("itemWriterAdapterTestJob")
                .incrementer(new RunIdIncrementer())
                .start(itemWriterAdapterTestStep())
                .build();
    }

    @Bean
    Step itemWriterAdapterTestStep() {
        return stepBuilderFactory
                .get("itemWriterAdapterTestStep")
                .<String, String >chunk(10)
                .reader(new ItemReader<>() {
                    int i = 0;

                    @Override
                    public String read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        return i > 10 ? null : "item" + i;
                    }
                })
                .writer(itemWriterAdapterTestWriter())
                .build();
    }

    @Bean
    ItemWriter<String> itemWriterAdapterTestWriter() {
        ItemWriterAdapter<String> writer = new ItemWriterAdapter<>();
        writer.setTargetObject(itemWriterAdapterTestService());
        writer.setTargetMethod("customWrite");
        return writer;
    }

    @Bean
    Object itemWriterAdapterTestService() {
        return new ItemWriterAdapterTestService<String>();
    }
}
