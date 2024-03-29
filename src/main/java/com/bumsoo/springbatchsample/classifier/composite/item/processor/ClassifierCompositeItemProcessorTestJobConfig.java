package com.bumsoo.springbatchsample.classifier.composite.item.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ClassifierCompositeItemProcessorTestJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job classifierCompositeItemProcessorTestJob() {
        return jobBuilderFactory
                .get("classifierCompositeItemProcessorTestJob")
                .incrementer(new RunIdIncrementer())
                .start(classifierCompositeItemProcessorTestStep())
                .build();
    }

    @Bean
    Step classifierCompositeItemProcessorTestStep() {
        return stepBuilderFactory
                .get("classifierCompositeItemProcessorTestStep")
                .<ProcessorInfo,ProcessorInfo>chunk(10)
                .reader(new ItemReader<>() {
                    int i = 0;
                    @Override
                    public ProcessorInfo read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        ProcessorInfo processorInfo = ProcessorInfo.builder().id(i).build();
                        return i > 3 ? null : processorInfo;
                    }
                })
                .processor(classifierCompositeItemProcessorTestProcessor())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    @Bean
    ItemProcessor<ProcessorInfo, ProcessorInfo> classifierCompositeItemProcessorTestProcessor() {

        ClassifierCompositeItemProcessor<ProcessorInfo, ProcessorInfo> processor = new ClassifierCompositeItemProcessor<>();

        ProcessorClassifier<ProcessorInfo, ItemProcessor<?, ? extends ProcessorInfo>> classifier = new ProcessorClassifier<>();

        Map<Integer, ItemProcessor<ProcessorInfo, ProcessorInfo>> processorMap = new HashMap<>();
        processorMap.put(1, new CustomItemProcessor1());
        processorMap.put(2, new CustomItemProcessor2());
        processorMap.put(3, new CustomItemProcessor3());
        classifier.setProcessorMap(processorMap);
        processor.setClassifier(classifier);

        return processor;
    }
}
