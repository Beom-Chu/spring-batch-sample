package com.bumsoo.springbatchsample;

import com.bumsoo.springbatchsample.json.item.reader.JsonItemReaderConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {JsonItemReaderConfig.class, TestBatchConfig.class})
public class SimpleJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void simpleJob_test() throws Exception {

        //given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user1")
                .addLong("date", new Date().getTime())
                .toJobParameters();


        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters); /* Job 실행 테스트 */
        JobExecution jobExecutionStep = jobLauncherTestUtils.launchStep("jsonItemReaderStep"); /* Step 실행 테스트 */


        //then
        Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
        Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

        StepExecution stepExecution = ((List<StepExecution>) jobExecutionStep.getStepExecutions()).get(0);

        Assert.assertEquals(stepExecution.getCommitCount(), 2);
        Assert.assertEquals(stepExecution.getReadCount(), 4);
        Assert.assertEquals(stepExecution.getWriteCount(), 4);

    }

}
