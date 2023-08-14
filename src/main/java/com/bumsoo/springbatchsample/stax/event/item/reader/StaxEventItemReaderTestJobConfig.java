package com.bumsoo.springbatchsample.stax.event.item.reader;

import com.bumsoo.springbatchsample.dto.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class StaxEventItemReaderTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job staxEventItemReaderJob() {
        return jobBuilderFactory
                .get("staxEventItemReaderJob")
                .incrementer(new RunIdIncrementer())
                .start(staxEventItemReaderStep())
                .build();
    }

    @Bean
    Step staxEventItemReaderStep() {
        return stepBuilderFactory
                .get("staxEventItemReaderStep")
                .chunk(3)
                .reader(staxEventItemReaderReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    ItemReader<? extends Customer> staxEventItemReaderReader() {
        return new StaxEventItemReaderBuilder<Customer>()
                .name("staxEventItemReaderReader")
                .resource(new ClassPathResource("/customer.xml"))
                .addFragmentRootElements("customer")
                .unmarshaller(staxEventItemReaderUnmarshaller())
                .build();

    }

    Unmarshaller staxEventItemReaderUnmarshaller() {
        Map<String, Class<?>> aliases= new HashMap<>();
        aliases.put("customer", Customer.class);
        aliases.put("name", String.class);
        aliases.put("age", Integer.class);
        aliases.put("year", String.class);

        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setAliases(aliases);

        return xStreamMarshaller;
    }


}
