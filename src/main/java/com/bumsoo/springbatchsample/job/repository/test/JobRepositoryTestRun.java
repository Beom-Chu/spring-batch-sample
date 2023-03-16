package com.bumsoo.springbatchsample.job.repository.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobRepositoryTestRun implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job jobRepositoryTestJob;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "bumsoo4")
                .toJobParameters();

        jobLauncher.run(jobRepositoryTestJob, jobParameters);
    }
}
