package com.bumsoo.springbatchsample.job.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobRepositoryListener implements JobExecutionListener {

    private final JobRepository jobRepository;
    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "bumsoo").toJobParameters();

        JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);

        if(lastJobExecution != null) {
            for(StepExecution e : lastJobExecution.getStepExecutions()) {
                log.info("[[[status : {}", e.getStatus());
                log.info("[[[exitStatus : {}", e.getExitStatus());
                log.info("[[[stepName : {}", e.getStepName());
                log.info("[[[startTime : {}", e.getStartTime());
                log.info("[[[endTime : {}", e.getEndTime());
            }
        }
    }
}
