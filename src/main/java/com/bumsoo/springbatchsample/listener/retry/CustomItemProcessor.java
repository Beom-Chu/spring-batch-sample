package com.bumsoo.springbatchsample.listener.retry;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Integer, String> {

    int count = 0;
    @Override
    public String process(Integer integer) throws Exception {
        System.out.println("[[[CustomItemProcessor.process => count : " + count);
        if(count < 2) {
            if(count % 2 == 0) {
                count++;
            } else {
                count++;
                throw new CustomRetryException("failed");
            }
        }
        return String.valueOf(integer);
    }
}
