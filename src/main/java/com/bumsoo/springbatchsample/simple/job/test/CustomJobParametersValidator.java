package com.bumsoo.springbatchsample.simple.job.test;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

public class CustomJobParametersValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        String name = jobParameters.getString("name");
        if(!StringUtils.hasText(name)) {
            throw new JobParametersInvalidException("name 파라미터가 없습니다.");
        }
    }
}
