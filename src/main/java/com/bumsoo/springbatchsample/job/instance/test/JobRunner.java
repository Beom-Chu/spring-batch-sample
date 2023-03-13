package com.bumsoo.springbatchsample.job.instance.test;

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
public class JobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job jobInstanceTestJob;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /* 동일한 파라미터로 동일한 Job 실행 불가 */
        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("name", "user1")
                .addString("name", "user2")
                .toJobParameters();

        jobLauncher.run(jobInstanceTestJob, jobParameters);
    }
}
