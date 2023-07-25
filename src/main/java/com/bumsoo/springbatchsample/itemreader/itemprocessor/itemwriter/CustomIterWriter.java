package com.bumsoo.springbatchsample.itemreader.itemprocessor.itemwriter;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class CustomIterWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> list) throws Exception {
        list.forEach(System.out::println);
    }
}
