package com.bumsoo.springbatchsample.simple.flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SimpleFlowJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleFlowJob() {
        return jobBuilderFactory
                .get("simpleFlowJob")
                .incrementer(new RunIdIncrementer())
                .start(simpleFlowJobFlow1())
                .on("COMPLETED").to(simpleFlowJobFlow2())
                .end()
                .build();
    }

    @Bean
    public Flow simpleFlowJobFlow1() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("simpleFlowJobFlow1");
        return flowBuilder
                .start(simpleFlowJobStep1())
                .next(simpleFlowJobStep2())
                .end();
    }

    @Bean
    public Flow simpleFlowJobFlow2() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("simpleFlowJobFlow2");
        return flowBuilder
                .start(simpleFlowJobFlow3())
                .next(simpleFlowJobStep5())
                .next(simpleFlowJobStep6())
                .end();
    }

    @Bean
    public Flow simpleFlowJobFlow3() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("simpleFlowJobFlow3");
        return flowBuilder
                .start(simpleFlowJobStep3())
                .next(simpleFlowJobStep4())
                .end();
    }

    @Bean
    public Step simpleFlowJobStep1() {
        return stepBuilderFactory.get("simpleFlowJobStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("SimpleFlowConfig.simpleFlowJobStep1");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step simpleFlowJobStep2() {
        return stepBuilderFactory.get("simpleFlowJobStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("SimpleFlowConfig.simpleFlowJobStep2");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step simpleFlowJobStep3() {
        return stepBuilderFactory.get("simpleFlowJobStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("SimpleFlowConfig.simpleFlowJobStep3");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step simpleFlowJobStep4() {
        return stepBuilderFactory.get("simpleFlowJobStep4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("SimpleFlowConfig.simpleFlowJobStep4");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step simpleFlowJobStep5() {
        return stepBuilderFactory.get("simpleFlowJobStep5")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("SimpleFlowConfig.simpleFlowJobStep5");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step simpleFlowJobStep6() {
        return stepBuilderFactory.get("simpleFlowJobStep6")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("SimpleFlowConfig.simpleFlowJobStep6");
                    return RepeatStatus.FINISHED;
                }).build();
    }

}
