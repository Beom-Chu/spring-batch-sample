package com.bumsoo.springbatchsample.retry;

import org.springframework.batch.item.ItemProcessor;

public class RetryTestProcessor implements ItemProcessor<String, String> {

    private int cnt = 0;
    @Override
    public String process(String o) throws Exception {

        System.out.println("[[[cnt = " + cnt);
        System.out.println("[[[o = " + o);

        if(o.equals("2") || o.equals("3")) {
            cnt++;
            throw new RetryableException("failed cnt : " + cnt);
        }
        return o;
    }
}
