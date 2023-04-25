package com.bumsoo.springbatchsample.execution.test;

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
public class JobExecutionTestRun implements ApplicationRunner {
    private final JobLauncher jobLauncher;
    private final Job jobExecutionTestJob;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /* 동일한 Job, Parameter는 실행이 불가능 하지만,
        * Job이 실패한 경우 동일한 Job, Parameter로 재실행 가능 (성공할때까지).
        * JobExecution은 계속 누적 됨 */
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "testException2")
                .toJobParameters();

        jobLauncher.run(jobExecutionTestJob, jobParameters);
    }
}
