package com.bumsoo.springbatchsample.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomStepExecutionListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("CustomStepExecutionListener.beforeStep");

        String stepName = stepExecution.getStepName();
        System.out.println("[[[stepName = " + stepName);

        stepExecution.getExecutionContext().put("name" , "user1");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("CustomStepExecutionListener.afterStep");

        ExitStatus exitStatus = stepExecution.getExitStatus();
        BatchStatus batchStatus = stepExecution.getStatus();
        String name = (String) stepExecution.getExecutionContext().get("name");

        System.out.println("[[[exitStatus = " + exitStatus);
        System.out.println("[[[batchStatus = " + batchStatus);
        System.out.println("[[[name = " + name);

        return ExitStatus.COMPLETED;
    }
}
