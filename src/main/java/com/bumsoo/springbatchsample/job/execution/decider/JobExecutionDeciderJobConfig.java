package com.bumsoo.springbatchsample.job.execution.decider;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobExecutionDeciderJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    /**
     * JobExecutionDecider 테스트
     * ExitStatus를 조작하거나 StepExecuteListener를 등록할 필요 없이 Transition 처리를 위한 전용 클래스
     * Step과 Transition 역할을 명확히 분리해서 설정 할 수 있다.
     * Step의 ExitStatus가 아닌 JobExecutionDecider의 FlowExecutionStatus 상태값을 새롭게 설정해서 반환한다.
     */
    @Bean
    public Job jobExecutionDeciderJob() {
        return jobBuilderFactory.get("jobExecutionDeciderJob")
                .incrementer(new RunIdIncrementer())
                .start(jobExecutionDeciderStartStep())
                .next(decider())
                .from(decider()).on("ODD").to(jobExecutionDeciderOddStep())
                .from(decider()).on("EVEN").to(jobExecutionDeciderEvenStep())
                .end()
                .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new CustomDecider();
    }

    @Bean
    public Step jobExecutionDeciderStartStep() {
        return stepBuilderFactory.get("jobExecutionDeciderStartStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("JobExecutionDeciderJobConfig.jobExecutionDeciderStartStep");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step jobExecutionDeciderOddStep() {
        return stepBuilderFactory.get("jobExecutionDeciderOddStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("JobExecutionDeciderJobConfig.jobExecutionDeciderOddStep");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step jobExecutionDeciderEvenStep() {
        return stepBuilderFactory.get("jobExecutionDeciderEvenStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("JobExecutionDeciderJobConfig.jobExecutionDeciderEvenStep");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
