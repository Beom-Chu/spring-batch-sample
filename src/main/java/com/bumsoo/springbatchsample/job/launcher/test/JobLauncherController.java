package com.bumsoo.springbatchsample.job.launcher.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@RequiredArgsConstructor
public class JobLauncherController {

    private final Job jobLauncherJob;
    private final JobLauncher jobLauncher;

    private final BasicBatchConfigurer basicBatchConfigurer;

    /* Batch 동기 실행 : Default */
    @PostMapping("/batch-sync")
    public String launchSync(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", member.getId())
                .addDate("date", new Date())
                .toJobParameters();

        /* Default : 동기 실행 */
        jobLauncher.run(jobLauncherJob, jobParameters);

        return "batch-sync completed!!";
    }

    /* Batch 비동기 실행  */
    @PostMapping("/batch-async")
    public String launchAsync(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", member.getId())
                .addDate("date", new Date())
                .toJobParameters();

        SimpleJobLauncher jobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor()); /* 비동기 실행 */
        jobLauncher.run(jobLauncherJob, jobParameters);

        return "batch-async completed!!";
    }
}
