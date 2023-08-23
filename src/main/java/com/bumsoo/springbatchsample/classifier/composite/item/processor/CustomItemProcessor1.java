package com.bumsoo.springbatchsample.classifier.composite.item.processor;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor1 implements ItemProcessor<ProcessorInfo, ProcessorInfo> {
    @Override
    public ProcessorInfo process(ProcessorInfo processorInfo) throws Exception {
        System.out.println("[[[CustomItemProcessor1.process");
        return processorInfo;
    }
}
