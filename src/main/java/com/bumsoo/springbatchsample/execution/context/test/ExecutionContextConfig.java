package com.bumsoo.springbatchsample.execution.context.test;

import com.bumsoo.springbatchsample.execution.context.test.tasklet.ExecutionContextTestTasklet1;
import com.bumsoo.springbatchsample.execution.context.test.tasklet.ExecutionContextTestTasklet2;
import com.bumsoo.springbatchsample.execution.context.test.tasklet.ExecutionContextTestTasklet3;
import com.bumsoo.springbatchsample.execution.context.test.tasklet.ExecutionContextTestTasklet4;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ExecutionContextConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ExecutionContextTestTasklet1 executionContextTestTasklet1;
    private final ExecutionContextTestTasklet2 executionContextTestTasklet2;
    private final ExecutionContextTestTasklet3 executionContextTestTasklet3;
    private final ExecutionContextTestTasklet4 executionContextTestTasklet4;

    @Bean
    public Job executionContextTestJob() {
        return jobBuilderFactory
                .get("executionContextTestJob")
                .start(executionContextTestStep1())
                .next(executionContextTestStep2())
                .next(executionContextTestStep3())
                .next(executionContextTestStep4())
                .build();
    }

    @Bean
    public Step executionContextTestStep1() {
        return stepBuilderFactory
                .get("executionContextTestStep1")
                .tasklet(executionContextTestTasklet1)
                .build();
    }

    @Bean
    public Step executionContextTestStep2() {
        return stepBuilderFactory
                .get("executionContextTestStep2")
                .tasklet(executionContextTestTasklet2)
                .build();
    }

    @Bean
    public Step executionContextTestStep3() {
        return stepBuilderFactory
                .get("executionContextTestStep3")
                .tasklet(executionContextTestTasklet3)
                .build();
    }

    @Bean
    public Step executionContextTestStep4() {
        return stepBuilderFactory
                .get("executionContextTestStep4")
                .tasklet(executionContextTestTasklet4)
                .build();
    }
}
