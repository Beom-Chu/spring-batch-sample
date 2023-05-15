package com.bumsoo.springbatchsample.flow.transition;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlowTransitionJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowTransitionJob() {
        return jobBuilderFactory.get("flowTransitionJob")
                .start(flowTransitionStep1())
                    .on("FAILED")
                    .to(flowTransitionStep2())
                    .on("FAILED")
                    .stop()
                .from(flowTransitionStep1())
                    .on("*")
                    .to(flowTransitionStep3())
                    .next(flowTransitionStep4())
                .from(flowTransitionStep2())
                    .on("*")
                    .to(flowTransitionStep5())
                .end()
                .build();
    }


    @Bean
    public Step flowTransitionStep1() {
        return stepBuilderFactory.get("flowTransitionStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowTransitionJobConfig.flowTransitionStep1");
                    stepContribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step flowTransitionStep2() {
        return stepBuilderFactory.get("flowTransitionStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowTransitionJobConfig.flowTransitionStep2");
                    stepContribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step flowTransitionStep3() {
        return stepBuilderFactory.get("flowTransitionStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowTransitionJobConfig.flowTransitionStep3");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step flowTransitionStep4() {
        return stepBuilderFactory.get("flowTransitionStep4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowTransitionJobConfig.flowTransitionStep4");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step flowTransitionStep5() {
        return stepBuilderFactory.get("flowTransitionStep5")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowTransitionJobConfig.flowTransitionStep5");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
