package com.bumsoo.springbatchsample.job.limit.allow.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LimitAllowStartConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job limitAllowStartJob() {
        return jobBuilderFactory
                .get("limitAllowStartJob")
                .start(limitAllowStartStep1())
                .next(limitAllowStartStep2())
                .build();
    }

    @Bean
    public Step limitAllowStartStep1() {
        return stepBuilderFactory
                .get("limitAllowStartStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("[[[step Name = " + stepContribution.getStepExecution().getStepName());
                    return RepeatStatus.FINISHED;
                })
                .allowStartIfComplete(true) /* 해당 Step이 성공했더라도 재실행 되도록 하는 옵션 */
                .build();
    }

    @Bean
    public Step limitAllowStartStep2() {
        return stepBuilderFactory
                .get("limitAllowStartStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("[[[step Name = " + stepContribution.getStepExecution().getStepName());
                    throw new RuntimeException("step2 fail!");
//                    return RepeatStatus.FINISHED;
                })
                .startLimit(4) /* Step의 실행 횟수를 조정 */
                .build();
    }
}
