package com.bumsoo.springbatchsample.job.launcher;

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
public class JobLauncherConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobLauncherJob() {
        return jobBuilderFactory
                .get("jobLauncherJob")
                .start(jobLauncherStep1())
                .next(jobLauncherStep2())
                .build();
    }

    @Bean
    public Step jobLauncherStep1() {
        return stepBuilderFactory
                .get("jobLauncherStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[[[jobLauncherStep1");
                    Thread.sleep(5000);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step jobLauncherStep2() {
        return stepBuilderFactory
                .get("jobLauncherStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[[[jobLauncherStep2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
