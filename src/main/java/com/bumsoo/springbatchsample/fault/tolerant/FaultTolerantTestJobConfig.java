package com.bumsoo.springbatchsample.fault.tolerant;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FaultTolerantTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job faultTolerantTestJob() {
        return jobBuilderFactory
                .get("faultTolerantTestJob")
                .incrementer(new RunIdIncrementer())
                .start(faultTolerantTestStep())
                .build();
    }

    @Bean
    Step faultTolerantTestStep() {
        return stepBuilderFactory
                .get("faultTolerantTestStep")
                .<String, String>chunk(5)
                .reader(new ItemReader<String>() {
                    int i = 0;
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        System.out.println("FaultTolerantTestJobConfig.read");
                        i++;
                        if(i==1) {
                            throw new IllegalArgumentException("this exception is skipped.");
                        }
                        String s = i > 3 ? null : "item" + i;
                        System.out.println("[[[s = " + s);
                        return s;
                    }
                })
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String s) throws Exception {
                        System.out.println("FaultTolerantTestJobConfig.process");
                        throw new IllegalArgumentException("this exception is retried.");
//                        return null;
                    }
                })
                .writer(list -> list.forEach(System.out::println))
                .faultTolerant() /* 내결함성 기능 활성화 */
                .skip(IllegalArgumentException.class) /* 예외 발생시 Skip할 예외 타입 설정 */
                .skipLimit(2)/* Skip 제한 횟수 설정 */
                .retry(IllegalArgumentException.class) /* 예외 발생시 Retry할 예외 타입 설정 */
                .retryLimit(2) /* Retry 제한 횟수 설정 */
                .build();
    }
}
