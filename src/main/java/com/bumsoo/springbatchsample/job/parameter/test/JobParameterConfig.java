package com.bumsoo.springbatchsample.job.parameter.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobParameterConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobParameterTestJob() {
        return jobBuilderFactory
                .get("jobParameterTestJob")
                .start(jobParameterTestStep1())
                .next(jobParameterTestStep2())
                .build();
    }

    @Bean
    public Step jobParameterTestStep1() {
        return stepBuilderFactory
                .get("jobParameterTestStep1")
                .tasklet((stepContribution, chunkContext) -> {

                    /* Step에서 Job Parameter 활용 */

                    /* stepContribution 통해서는 JobParameters 객체로 반환 */
                    JobParameters jobParameters = stepContribution.getStepExecution().getJobParameters();
                    String str = jobParameters.getString("str");
                    Date dt = jobParameters.getDate("dt");
                    Long l = jobParameters.getLong("l");
                    Double db = jobParameters.getDouble("db");
                    log.info("[[[ str:{}, dt:{}, l:{}, db:{}", str, dt, l, db);

                    /* chunkContext 통해서는 Map 으로 반환 */
                    Map<String, Object> jobParametersMap = chunkContext.getStepContext().getJobParameters();
                    jobParametersMap.forEach((s, o) -> log.info("[[[ key:{}, value:{}",s.toString(), o.toString()));

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step jobParameterTestStep2() {
        return stepBuilderFactory
                .get("jobParameterTestStep2")
                .tasklet((stepContribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }
}
