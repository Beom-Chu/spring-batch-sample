package com.bumsoo.springbatchsample.simple.job.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SimpleJobTestConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJobTestJob() {
        return jobBuilderFactory
                .get("simpleJobTestJob")
                .start(simpleJobTestStep1())
                .next(simpleJobTestStep2())
//                .incrementer(new RunIdIncrementer())            /* JobParameter 의 값을 자동을 증가해 주는 JobParametersIncrementer 설정 */
                .incrementer(new CustomJobParametersIncrementer())
//                .preventRestart()                               /* Job 의 재 시작 가능 여부 설정, 기본값 true */
//                .validator(new DefaultJobParametersValidator(   /* JobParameter 를 실행하기 전에 올바른 구성이 되었는지 검증하는 JobParametersValidator 설정 */
//                        new String[]{"name"}            /* requiredKeys : 필수 */
//                        ,new String[]{"age", "year"}    /* optionalKeys : 선택 */
//                ))
//                .validator(new CustomJobParametersValidator()) /* DefaultJobParametersValidator 사용해도 되고 Custom 생성도 가능 */
                .listener(new JobExecutionListener() {          /* Job 라이프 사이클의 특정 시점에 콜백 제공받도록 JobExecutionListener 설정  */
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        System.out.println("SimpleJobTestConfig.beforeJob");
                    }
                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        System.out.println("SimpleJobTestConfig.afterJob");
                    }
                })
                .build();
    }

    @Bean
    public Step simpleJobTestStep1(){
        return stepBuilderFactory
                .get("simpleJobTestStep1")
                .tasklet((stepContribution, chunkContext) -> {

                    System.out.println("SimpleJobTestConfig.simpleJobTestStep1");

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    public Step simpleJobTestStep2(){
        return stepBuilderFactory
                .get("simpleJobTestStep2")
                .tasklet((stepContribution, chunkContext) -> {

                    System.out.println("SimpleJobTestConfig.simpleJobTestStep2");

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
