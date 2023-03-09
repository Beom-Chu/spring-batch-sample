package com.bumsoo.springbatchsample;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory
                .get("helloJob")
                .start(helloStep1())
                .next(helloStep2())
                .build();
    }

    @Bean
    public Step helloStep1() {
        return stepBuilderFactory
                .get("helloStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("hello spring batch 1");
                    /* RepeatStatus.FINISHED = 반복종료
                    null도 동일하게 처리됨
                    RepeatStatus.CONTINUABLE로 하면 Step 계속 반복 */
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step helloStep2() {
        return stepBuilderFactory
                .get("helloStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("hello spring batch 22");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
