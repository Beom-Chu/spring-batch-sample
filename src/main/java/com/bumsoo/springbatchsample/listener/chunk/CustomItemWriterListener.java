package com.bumsoo.springbatchsample.listener.chunk;

import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

public class CustomItemWriterListener implements ItemWriteListener<String> {
    @Override
    public void beforeWrite(List<? extends String> list) {
        System.out.println("{{CustomItemWriterListener.beforeWrite");
    }

    @Override
    public void afterWrite(List<? extends String> list) {
        System.out.println("{{CustomItemWriterListener.afterWrite");
    }

    @Override
    public void onWriteError(Exception e, List<? extends String> list) {
        System.out.println("{{CustomItemWriterListener.onWriteError");
    }
}
