package com.bumsoo.springbatchsample.job.execution.test;

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
public class JobExecutionConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobExecutionTestJob() {
        return jobBuilderFactory
                .get("jobExecutionTestJob")
                .start(jobExecutionTestStep1())
                .next(jobExecutionTestStep2())
                .build();
    }

    @Bean
    public Step jobExecutionTestStep1() {
        return stepBuilderFactory
                .get("jobExecutionTestStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[[[step1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step jobExecutionTestStep2() {
        return stepBuilderFactory
                .get("jobExecutionTestStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[[[step2");

                    /* 동일한 Job, Parameter는 실행이 불가능 하지만,
                     * Job이 실패한 경우 동일한 Job, Parameter로 재실행 가능 (성공할때까지).
                     * JobExecution은 계속 누적 됨 */

//                    throw new RuntimeException("에러 발생!");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
