package com.bumsoo.springbatchsample.step.builder.factory.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.SimplePartitioner;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StepBuilderConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepBuilderJob() {
        return jobBuilderFactory
                .get("StepBuilderJob")
                .incrementer(new RunIdIncrementer())
                .start(stepBuilderTaskletStep()) /* TaskletStep을 생성 */
                .next(stepBuilderSimpleStep()) /* TaskletStep을 생성하며 내부적으로 청크 기반의 작업을 처리 */
                .next(stepBuilderPartitionStep()) /* 멀티스레드 방식으로 Job을 실행 */
                .next(stepBuilderJobStep()) /* Step 안에서 Job을 실행 */
                .build();

    }

    @Bean
    public Step stepBuilderTaskletStep() {
        return stepBuilderFactory
                .get("stepBuilderTaskletStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("StepBuilderConfig.stepBuilderTaskletStep");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step stepBuilderSimpleStep() {
        return stepBuilderFactory
                .get("stepBuilderSimpleStep")
                .<Integer, Integer>chunk(10)
                .reader(new ListItemReader<>(List.of(1, 2, 3, 4, 5)))
                .writer(list -> System.out.println("[[[list = " + list))
                .build();

    }

    @Bean
    public Step stepBuilderPartitionStep() {
        return stepBuilderFactory
                .get("stepBuilderPartitionStep")
                .partitioner("stepBuilderPartitionSubStep",new SimplePartitioner())
                .step(stepBuilderFactory.get("stepBuilderPartitionSubStep")
                        .tasklet((stepContribution, chunkContext) -> {
                            System.out.println("stepBuilderPartitionStep.stepBuilderPartitionSubStep");
                            return RepeatStatus.FINISHED;
                        }).build())
                .gridSize(3)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }


    @Bean
    public Step stepBuilderJobStep() {
        return stepBuilderFactory
                .get("stepBuilderJobStep")
                .job(stepBuilderJobStepSubJob())
                .build();
    }

    @Bean
    public Job stepBuilderJobStepSubJob() {
        return jobBuilderFactory
                .get("stepBuilderJobStepSubJob")
                .start(stepBuilderFactory.get("stepBuilderJobStepSubJobSubStep")
                        .tasklet((stepContribution, chunkContext) -> {
                            System.out.println("[[[stepBuilderJobStepSubJobSubStep");
                            return RepeatStatus.FINISHED;
                        }).build())
                .build();
    }
}
