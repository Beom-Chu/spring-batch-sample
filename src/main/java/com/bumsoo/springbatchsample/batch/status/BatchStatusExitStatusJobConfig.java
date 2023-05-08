package com.bumsoo.springbatchsample.batch.status;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
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
public class BatchStatusExitStatusJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

//    @Bean
//    public Job batchStatusExitStatusJob() {
//        return jobBuilderFactory
//                .get("batchStatusExitStatusJob")
//                .incrementer(new RunIdIncrementer())
//                .start(batchStatusExitStatusStep1())
//                .next(batchStatusExitStatusStep2())
//                .build();
//    }
    @Bean
    public Job batchStatusExitStatusJob() {
        return jobBuilderFactory
                .get("batchStatusExitStatusJob")
                .incrementer(new RunIdIncrementer())
                .start(batchStatusExitStatusStep1())
                .on("FAILED")
                .to(batchStatusExitStatusStep2())
                .end()
                .build();
    }

    @Bean
    public Step batchStatusExitStatusStep1() {
        return stepBuilderFactory
                .get("batchStatusExitStatusStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("BatchStatusExitStatusJobConfig.batchStatusExitStatusStep1");
                    stepContribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step batchStatusExitStatusStep2() {
        return stepBuilderFactory
                .get("batchStatusExitStatusStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("BatchStatusExitStatusJobConfig.batchStatusExitStatusStep2");
//                    stepContribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


}
