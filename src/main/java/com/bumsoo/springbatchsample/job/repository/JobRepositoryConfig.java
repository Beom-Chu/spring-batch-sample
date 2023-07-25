package com.bumsoo.springbatchsample.job.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobRepositoryConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobExecutionListener jobRepositoryListener;

    @Bean
    public Job jobRepositoryTestJob() {
        return jobBuilderFactory
                .get("jobRepositoryTestJob")
                .start(jobRepositoryTestStep1())
                .next(jobRepositoryTestStep2())
                .listener(jobRepositoryListener)
                .build();
    }

    @Bean
    public Step jobRepositoryTestStep1() {
        return stepBuilderFactory
                .get("jobRepositoryTestStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[[[jobRepositoryTestStep1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step jobRepositoryTestStep2() {
        return stepBuilderFactory
                .get("jobRepositoryTestStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[[[jobRepositoryTestStep2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
