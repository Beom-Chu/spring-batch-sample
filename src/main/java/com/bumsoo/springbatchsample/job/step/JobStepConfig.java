package com.bumsoo.springbatchsample.job.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobStepConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobStepParentJob() {
        return jobBuilderFactory
                .get("jobStepParentJob")
                .start(jobStepJobStep(null))
                .next(jobStepStep2())
                .build();
    }

    @Bean
    public Step jobStepJobStep(JobLauncher jobLauncher) {
        return stepBuilderFactory
                .get("jobStepJobStep")
                .job(jobStepChildJob())
                .launcher(jobLauncher)
                .parametersExtractor(jobParametersExtractor())
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        stepExecution.getExecutionContext().putString("name", "user1");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return null;
                    }
                })
                .build();
    }

    public DefaultJobParametersExtractor jobParametersExtractor() {
        DefaultJobParametersExtractor extractor = new DefaultJobParametersExtractor();
        extractor.setKeys(new String[]{"name"});
        return extractor;
    }


    @Bean
    public Job jobStepChildJob() {
        return jobBuilderFactory
                .get("jobStepChildJob")
                .start(jobStepStep1())
                .build();
    }

    @Bean
    public Step jobStepStep1() {
        return stepBuilderFactory
                .get("jobStepStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("JobStepConfig.jobStepStep1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step jobStepStep2() {
        return stepBuilderFactory
                .get("jobStepStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("JobStepConfig.jobStepStep2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
