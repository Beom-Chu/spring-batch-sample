package com.bumsoo.springbatchsample.listener.skip;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SkipListenerTestJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job skipListenerTestJob() {
        return jobBuilderFactory
                .get("skipListenerTestJob")
                .incrementer(new RunIdIncrementer())
                .start(skipListenerTestStep())
                .build();
    }

    @Bean
    Step skipListenerTestStep() {
        return stepBuilderFactory
                .get("skipListenerTestStep")
                .<Integer, String>chunk(10)
                .reader(skipListenerTestReader())
                .processor(new ItemProcessor<Integer, String>() {
                    @Override
                    public String process(Integer i) throws Exception {
                        if(i == 4) {
                            throw new CustomSkipException("process skipped : " + i);
                        }
                        return "item" + i;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> list) throws Exception {
                        for (String s : list) {
                            if(s.equals("item5")) {
                                throw new CustomSkipException("write skipped : "+ s);
                            }
                            System.out.println(s);
                        }
                    }
                })
                .faultTolerant()
                .skip(CustomSkipException.class)
                .skipLimit(3)
                .listener(new CustomSkipListener())
                .build();
    }


    @Bean
    ItemReader<Integer> skipListenerTestReader() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new LinkedListItemReader<>(list);
    }
}
