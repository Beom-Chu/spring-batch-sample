package com.bumsoo.springbatchsample.listener.job.step;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

public class CustomAnnotationJobExecutionListener {
    @BeforeJob
    public void bJob(JobExecution jobExecution) {
        System.out.println("CustomAnnotationJobExecutionListener.bJob");
        System.out.println("JobName : " + jobExecution.getJobInstance().getJobName());
    }

    @AfterJob
    public void aJob(JobExecution jobExecution) {
        System.out.println("CustomAnnotationJobExecutionListener.aJob");
        long startTime = jobExecution.getStartTime().getTime();
        long endTime = jobExecution.getEndTime().getTime();
        System.out.println("총 소요시간 : " + (endTime - startTime));
    }
}
