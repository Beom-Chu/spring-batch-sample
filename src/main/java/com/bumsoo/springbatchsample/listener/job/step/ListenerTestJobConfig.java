package com.bumsoo.springbatchsample.listener.job.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ListenerTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CustomStepExecutionListener customStepExecutionListener;

    @Bean
    Job listenerTestJob() {
        return jobBuilderFactory
                .get("listenerTestJob")
                .incrementer(new RunIdIncrementer())
                .start(listenerTestStep1())
                .next(listenerTestStep2())
//                .listener(new CustomJobExecutionListener())
                .listener(new CustomAnnotationJobExecutionListener())
                .build();
    }

    @Bean
    Step listenerTestStep1() {
        return stepBuilderFactory
                .get("listenerTestStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("ListenerTestJobConfig.listenerTestStep1");
                    return RepeatStatus.FINISHED;
                })
                .listener(customStepExecutionListener)
                .build();
    }

    @Bean
    Step listenerTestStep2() {
        return stepBuilderFactory
                .get("listenerTestStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("ListenerTestJobConfig.listenerTestStep2");
                    return RepeatStatus.FINISHED;
                })
                .listener(customStepExecutionListener)
                .build();
    }
}
