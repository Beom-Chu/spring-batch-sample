package com.bumsoo.springbatchsample.execution.context.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExecutionContextTestTasklet1 implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        log.info("[[[ ExecutionContextTestTasklet1 ");

        /* ExecutionContext : JobExecution, StepExecution 객체의 상태를 저장하는 공유 객체
         * DB에 직렬화한 값으로 저장됨 {"key":"value"}
         * 공유 범위
         *     Step : StepExecution에 저장되면 Step간에 공유 X
         *     Job : JobExecution에 저장되며 Job간에 공유 X, 해당 Job의 Step 간에 공유 O
         * Job 재시작시 이미 처리한 Row 데이터는 건너뛰고 이후로 수행 하도록 할 때 상태 정보를 활용.
         */

        ExecutionContext jobExecutionContext = stepContribution.getStepExecution().getJobExecution().getExecutionContext();
        ExecutionContext stepExecutionContext = stepContribution.getStepExecution().getExecutionContext();

        String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
        String stepName = chunkContext.getStepContext().getStepName();

        if(jobExecutionContext.get("jobName") == null) {
            jobExecutionContext.put("jobName", jobName);
        }

        if(stepExecutionContext.get("stepName") == null) {
            stepExecutionContext.put("stepName", stepName);
        }

        log.info("[[[ jobName : {}", jobExecutionContext.get("jobName"));
        log.info("[[[ stepName : {}", stepExecutionContext.get("stepName"));

        return RepeatStatus.FINISHED;
    }
}
