package com.bumsoo.springbatchsample.listener.chunk;

import org.springframework.batch.core.ItemProcessListener;

public class CustomItemProcessListener implements ItemProcessListener<Integer, String> {
    @Override
    public void beforeProcess(Integer integer) {
        System.out.println("((CustomProcessListener.beforeProcess");
    }

    @Override
    public void afterProcess(Integer integer, String s) {
        System.out.println("((CustomProcessListener.afterProcess");
    }

    @Override
    public void onProcessError(Integer integer, Exception e) {
        System.out.println("((CustomProcessListener.onProcessError");
    }
}
