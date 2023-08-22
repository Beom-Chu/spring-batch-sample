package com.bumsoo.springbatchsample.composite.item.processor;

import org.springframework.batch.item.ItemProcessor;

public class CompositeItemProcessor1 implements ItemProcessor<String, String> {

    @Override
    public String process(String s) {
        return s.toUpperCase();
    }
}
