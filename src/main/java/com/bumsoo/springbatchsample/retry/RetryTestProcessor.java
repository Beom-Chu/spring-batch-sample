package com.bumsoo.springbatchsample.retry;

import org.springframework.batch.item.ItemProcessor;

public class RetryTestProcessor implements ItemProcessor<String, String> {

    private int cnt = 0;
    @Override
    public String process(String o) throws Exception {
        cnt++;
        System.out.println("[[[cnt = " + cnt);
        throw new RetryableException();
//        return null;
    }
}
