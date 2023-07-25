package com.bumsoo.springbatchsample.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TestFlowJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Bean
    public Job testFlowJob() {
        return jobBuilderFactory
                .get("testFlowJob")
                .start(testFlowJobFlow())
                .next(testFlowJobStep3())
                .end() /* Flow가 포함되어 있으면 end() 필수 */
                .build();
    }

    @Bean
    public Flow testFlowJobFlow() {
        FlowBuilder<Flow> testFlowJobFlow = new FlowBuilder<>("testFlowJobFlow");

        testFlowJobFlow
                .start(testFlowJobStep1())
                .next(testFlowJobStep2())
                .end(); /* Flow가 포함되어 있으면 end() 필수 */

        return testFlowJobFlow.build();
    }

    @Bean
    public Step testFlowJobStep1() {
        return stepBuilderFactory
                .get("testFlowJobStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[[[testFlowJobStep1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step testFlowJobStep2() {
        return stepBuilderFactory
                .get("testFlowJobStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[[[testFlowJobStep2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step testFlowJobStep3() {
        return stepBuilderFactory
                .get("testFlowJobStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[[[testFlowJobStep3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


}
