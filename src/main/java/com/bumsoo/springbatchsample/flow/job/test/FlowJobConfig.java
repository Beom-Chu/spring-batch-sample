package com.bumsoo.springbatchsample.flow.job.test;

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
public class FlowJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJobTestJob() {
        return jobBuilderFactory
                .get("flowJobTestJob")
                .start(flowJobTestStep1())
                .on("COMPLETED").to(flowJobTestStep3())
                .from(flowJobTestStep1())
                .on("FAILED").to(flowJobTestStep2())
                .from(flowJobTestStep2())
                .on("COMPLETED").to(flowJobTestStep4())
                .end()
                .build();
    }

    @Bean
    public Step flowJobTestStep1() {
        return stepBuilderFactory
                .get("flowJobTestStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowJobConfig.flowJobTestStep1");
                    throw new RuntimeException("오류 발생!");
//                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    public Step flowJobTestStep2() {
        return stepBuilderFactory
                .get("flowJobTestStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowJobConfig.flowJobTestStep2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    public Step flowJobTestStep3() {
        return stepBuilderFactory
                .get("flowJobTestStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowJobConfig.flowJobTestStep3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    public Step flowJobTestStep4() {
        return stepBuilderFactory
                .get("flowJobTestStep4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowJobConfig.flowJobTestStep4");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
