package com.bumsoo.springbatchsample.listener.retry;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class CustomItemWriter implements ItemWriter<String> {

    int count = 0;
    @Override
    public void write(List<? extends String> list) throws Exception {
        for(String s : list) {
            System.out.println("[[[CustomItemWriter.write => count : " + count);
            if(count < 2) {
                if(count % 2 == 0) {
                    count++;
                } else {
                    count++;
                    throw new CustomRetryException("failed");
                }
            }
            System.out.println("[[[write = " + s);
        }

    }
}
