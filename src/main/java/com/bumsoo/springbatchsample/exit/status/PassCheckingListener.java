package com.bumsoo.springbatchsample.exit.status;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class PassCheckingListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExitStatus exitStatus = stepExecution.getExitStatus();

        if(!exitStatus.equals(ExitStatus.FAILED)) {
            return new ExitStatus("PASS");
        }

        return null;
    }
}
