package com.bumsoo.springbatchsample.scope;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TestJopScopeStepScopeJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     * @JobScope, @StepScope
     * 해당 스코프가 선언되면 빈의 생성이 어플리케이션 구동 시점이 아닌 빈의 실행시점에 이루어진다.
     * @Values를 주입해서 빈의 실행 시점에 값을 참조할 수 있으며 일종의 Lazy Binding이 가능해진다.
     * @Values를 사용할 경우 빈 선언문에 @JobScope, @StepScope를 정의하지 않으면 오류가 발생하므로 반드시 선언해야 한다.
     */
    @Bean
    public Job testJopScopeStepScopeJob(){
        return jobBuilderFactory
                .get("testJopScopeStepScopeJob")
                .start(testJopScopeStepScopeStep1(null))
                .next(testJopScopeStepScopeStep2())
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        jobExecution.getExecutionContext().putString("name1", "user11");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {

                    }
                }).build();
    }

    @Bean
    @JobScope
    public Step testJopScopeStepScopeStep1(@Value("#{jobParameters['message']}") String message) {
        System.out.println("[[[message = " + message);
        return stepBuilderFactory.get("testJopScopeStepScopeStep1")
                .tasklet(tasklet1(null))
                .build();
    }

    @Bean
    public Step testJopScopeStepScopeStep2() {
        return stepBuilderFactory.get("testJopScopeStepScopeStep2")
                .tasklet(tasklet2(null))
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        stepExecution.getExecutionContext().putString("name2", "user222");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return null;
                    }
                })
                .build();
    }


    @Bean
    @StepScope
    public Tasklet tasklet1(@Value("#{jobExecutionContext['name1']}") String name1) {
        System.out.println("[[[name1 = " + name1);
        return (stepContribution, chunkContext) -> {
            System.out.println("[[[TestJopScopeStepScopeJobConfig.tasklet1");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @StepScope
    public Tasklet tasklet2(@Value("#{stepExecutionContext['name2']}") String name2) {
        System.out.println("[[[name2 = " + name2);
        return (stepContribution, chunkContext) -> {
            System.out.println("[[[TestJopScopeStepScopeJobConfig.tasklet2");
            return RepeatStatus.FINISHED;
        };
    }
}
