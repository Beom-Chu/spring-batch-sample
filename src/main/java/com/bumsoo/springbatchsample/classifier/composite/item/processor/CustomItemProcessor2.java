package com.bumsoo.springbatchsample.classifier.composite.item.processor;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor2 implements ItemProcessor<ProcessorInfo, ProcessorInfo> {
    @Override
    public ProcessorInfo process(ProcessorInfo processorInfo) throws Exception {
        System.out.println("[[[CustomItemProcessor2.process");
        return processorInfo;
    }
}
