package com.bumsoo.springbatchsample.skip;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SkipTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private int cnt = 0;

    @Bean
    Job skipTestJob() {
        return jobBuilderFactory
                .get("skipTestJob")
                .incrementer(new RunIdIncrementer())
                .start(skipTestStep())
                .build();
    }

    @Bean
    Step skipTestStep() {
        return stepBuilderFactory
                .get("skipTestStep")
                .<String, String>chunk(5)
                .reader(new ItemReader<>() {
                    int i = 0;

                    @Override
                    public String read() throws Exception {
                        System.out.println("SkipTestJobConfig.read");
                        i++;
                        if (i == 3) {
                            throw new SkippableException("Read failed cnt : " + ++cnt);
                        }
                        String s = i > 20 ? null : String.valueOf(i);
                        return s;
                    }
                })
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String s) throws Exception {

                        System.out.println("[[[Process : s = " + s);

                        if(s.equals("6") || s.equals("7")) {
                            throw new SkippableException("Process failed cnt : " + ++cnt);
                        } else {
                            return String.valueOf(Integer.valueOf(s) * -1);
                        }
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> list) throws Exception {
                        for(String s : list) {
                            if(s.equals("-12")) {
                                throw new SkippableException("Write failed cnt : " + ++cnt);
                            } else {
                                System.out.println("[[[Write : s = " + s);
                            }
                        }
                    }
                })
                .faultTolerant() /* 내결함성 기능 활성화 */
                .skip(SkippableException.class)
                .skipLimit(3)
                .build();
    }
}
