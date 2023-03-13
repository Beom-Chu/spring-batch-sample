package com.bumsoo.springbatchsample.job.instance.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobInstanceConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobInstanceTestJob() {
        return jobBuilderFactory
                .get("jobInstanceTestJob")
                .start(jobInstanceTestStep1())
                .next(jobInstanceTestStep2())
                .build();
    }

    @Bean
    public Step jobInstanceTestStep1() {
        return stepBuilderFactory
                .get("jobInstanceTestStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[[[step1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step jobInstanceTestStep2() {
        return stepBuilderFactory
                .get("jobInstanceTestStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[[[step2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
