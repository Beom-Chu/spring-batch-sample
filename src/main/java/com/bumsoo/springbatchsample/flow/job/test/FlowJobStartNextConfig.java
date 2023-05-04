package com.bumsoo.springbatchsample.flow.job.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlowJobStartNextConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJobStartNextJob() {
        return jobBuilderFactory
                .get("flowJobStartNextJob")
                .start(flowJobStartNextFlowA())
                .next(flowJobStartNextStep1())
                .next(flowJobStartNextFlowB())
                .next(flowJobStartNextStep4())
                .end()
                .build();
    }


    @Bean
    public Flow flowJobStartNextFlowA() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flowJobStartNextFlowA");
        flowBuilder.start(flowJobStartNextStep1())
                .next(flowJobStartNextStep2())
                .end();

        return flowBuilder.build();
    }

    @Bean
    public Flow flowJobStartNextFlowB() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flowJobStartNextFlowB");
        flowBuilder.start(flowJobStartNextStep3())
                .next(flowJobStartNextStep4())
                .end();

        return flowBuilder.build();
    }

    @Bean
    public Step flowJobStartNextStep1() {
        return stepBuilderFactory
                .get("flowJobStartNextStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowJobStartNextConfig.flowJobStartNextStep1");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step flowJobStartNextStep2() {
        return stepBuilderFactory
                .get("flowJobStartNextStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowJobStartNextConfig.flowJobStartNextStep2");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step flowJobStartNextStep3() {
        return stepBuilderFactory
                .get("flowJobStartNextStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowJobStartNextConfig.flowJobStartNextStep3");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step flowJobStartNextStep4() {
        return stepBuilderFactory
                .get("flowJobStartNextStep4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("FlowJobStartNextConfig.flowJobStartNextStep4");
                    return RepeatStatus.FINISHED;
                }).build();
    }

}
