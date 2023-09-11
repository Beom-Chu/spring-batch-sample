package com.bumsoo.springbatchsample.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class CustomJobExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("CustomJobExecutionListener.beforeJob");
        System.out.println("JobName : " + jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("CustomJobExecutionListener.afterJob");
        long startTime = jobExecution.getStartTime().getTime();
        long endTime = jobExecution.getEndTime().getTime();
        System.out.println("총 소요시간 : " + (endTime - startTime));
    }
}
