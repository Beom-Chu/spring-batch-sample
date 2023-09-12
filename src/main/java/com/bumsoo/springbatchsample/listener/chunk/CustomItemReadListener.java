package com.bumsoo.springbatchsample.listener.chunk;

import org.springframework.batch.core.ItemReadListener;

public class CustomItemReadListener implements ItemReadListener<Integer> {


    @Override
    public void beforeRead() {
        System.out.println("<<CustomItemReadListener.beforeRead");
    }

    @Override
    public void afterRead(Integer i) {
        System.out.println("<<CustomItemReadListener.afterRead");
    }

    @Override
    public void onReadError(Exception e) {
        System.out.println("<<CustomItemReadListener.onReadError");
    }
}
