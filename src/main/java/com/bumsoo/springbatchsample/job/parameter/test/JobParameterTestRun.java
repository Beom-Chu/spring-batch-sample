package com.bumsoo.springbatchsample.job.parameter.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JobParameterTestRun implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job jobParameterTestJob;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("str", "user")
                .addDate("dt" , new Date())
                .addLong("l", 123451L)
                .addDouble("db", 1234.561)
                .toJobParameters();

        jobLauncher.run(jobParameterTestJob, jobParameters);
    }
}
