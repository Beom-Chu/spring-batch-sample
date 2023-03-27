package com.bumsoo.springbatchsample.simple.job.test;

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
public class SimpleJobTestRun implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job simpleJobTestJob;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("age", 30L)
//                .addString("name", "Bum")
                .toJobParameters();

        jobLauncher.run(simpleJobTestJob, jobParameters);

    }
}
