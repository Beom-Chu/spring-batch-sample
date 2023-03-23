package com.bumsoo.springbatchsample.flat.file.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class FlatFileItemReaderTestRun implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job flatFileItemReaderTestJob;

    private final Environment env;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        String name = env.getProperty("name");

        System.out.println("[[[env.getProperty(\"name\") = " + name);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", name)
                .toJobParameters();

        jobLauncher.run(flatFileItemReaderTestJob, jobParameters);
    }
}
