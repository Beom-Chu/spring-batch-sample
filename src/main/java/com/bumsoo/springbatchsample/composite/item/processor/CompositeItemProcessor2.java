package com.bumsoo.springbatchsample.composite.item.processor;

import org.springframework.batch.item.ItemProcessor;

public class CompositeItemProcessor2 implements ItemProcessor<String, String> {

    int cnt = 0;
    @Override
    public String process(String s) {
        cnt++;
        return s + cnt;
    }
}
