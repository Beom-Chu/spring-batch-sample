package com.bumsoo.springbatchsample.repeat;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RepeatConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job repeatTestJob() {
        return jobBuilderFactory
                .get("repeatTestJob")
                .incrementer(new RunIdIncrementer())
                .start(repeatTestStep())
                .build();
    }

    @Bean
    Step repeatTestStep() {
        return stepBuilderFactory
                .get("repeatTestStep")
                .<String,String>chunk(10)
                .reader(new ItemReader<>() {
                    int i = 0;
                    @Override
                    public String read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        return i > 3 ? null : "item" + i;
                    }
                })
                .processor(new ItemProcessor<>() {

                    RepeatTemplate repeatTemplate = new RepeatTemplate();

                    @Override
                    public String process(String s) {

//                        repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(2)); /* 횟수만큼 반복 */
//                        repeatTemplate.setCompletionPolicy(new TimeoutTerminationPolicy(1000)); /* 시간만큼 반복 */

                        /* 반복 종료 조건을 여러가지 혼합, 가장 먼저 달성되는 조건으로 종료됨 */
//                        CompositeCompletionPolicy completionCompletionPolicy = new CompositeCompletionPolicy();
//                        CompletionPolicy[] completionPolicies = new CompletionPolicy[]{
//                                new SimpleCompletionPolicy(2),
//                                new TimeoutTerminationPolicy(1000)
//                        };
//                        completionCompletionPolicy.setPolicies(completionPolicies);
//                        repeatTemplate.setCompletionPolicy(completionCompletionPolicy);
                        /* ---------------------------------------------------------------------------------------- */

                        repeatTemplate.setExceptionHandler(simpleLimitExceptionHandler()); /* 횟수만큼 에러 발생시 반복 종료 */

                        repeatTemplate.iterate(repeatContext -> {
                            System.out.println("[[[repeatTemplate is testing.");
                            throw new Exception("ERROR!!");
//                            return RepeatStatus.CONTINUABLE;
                        });

                        return s;
                    }
                })
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    @Bean
    ExceptionHandler simpleLimitExceptionHandler() {
        return new SimpleLimitExceptionHandler(3); /* 횟수만큼 에러 발생시 반복 종료 */
    }
}
