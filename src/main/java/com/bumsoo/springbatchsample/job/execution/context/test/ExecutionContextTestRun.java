package com.bumsoo.springbatchsample.job.execution.context.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class ExecutionContextTestRun implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job executionContextTestJob;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "bumsoo")
                .toJobParameters();

        jobLauncher.run(executionContextTestJob, jobParameters);
    }
}
