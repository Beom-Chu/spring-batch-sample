package com.bumsoo.springbatchsample.retry.template;

import com.bumsoo.springbatchsample.retry.RetryableException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;

public class RetryTemplateTestProcessor implements ItemProcessor<String, Customer> {

    @Autowired
    private RetryTemplate retryTemplateTestRetryTemplate;

    private int cnt;

    @Override
    public Customer process(String o) throws Exception {

        Customer customer = retryTemplateTestRetryTemplate.execute(
                new RetryCallback<Customer, RuntimeException>() {
                    @Override
                    public Customer doWithRetry(RetryContext retryContext) throws RuntimeException {
                        if(o.equals("1") || o.equals("2")) {
                            cnt ++;
                            throw new RetryableException("failed cnt : " + cnt);
                        }
                        return new Customer(o);
                    }
                },
                new RecoveryCallback<Customer>() {
                    @Override
                    public Customer recover(RetryContext retryContext) throws Exception {
                        return new Customer(o);
                    }
                });
        return customer;
    }
}
