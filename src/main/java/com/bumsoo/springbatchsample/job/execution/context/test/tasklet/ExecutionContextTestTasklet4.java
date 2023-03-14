package com.bumsoo.springbatchsample.job.execution.context.test.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExecutionContextTestTasklet4 implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        log.info("[[[ ExecutionContextTestTasklet4 ");

        log.info("[[[ name : {}" , chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("name"));

        return RepeatStatus.FINISHED;
    }
}
