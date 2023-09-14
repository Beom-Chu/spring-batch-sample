package com.bumsoo.springbatchsample.listener.skip;

import org.springframework.batch.core.SkipListener;

public class CustomSkipListener implements SkipListener<Integer, String> {
    @Override
    public void onSkipInRead(Throwable throwable) {
        System.out.println("[[[CustomSkipListener.onSkipInRead");
        System.out.println("[[[" + throwable.getMessage());
    }

    @Override
    public void onSkipInWrite(String s, Throwable throwable) {
        System.out.println("[[[CustomSkipListener.onSkipInWrite");
        System.out.println("[[[" + throwable.getMessage());
    }

    @Override
    public void onSkipInProcess(Integer integer, Throwable throwable) {
        System.out.println("[[[CustomSkipListener.onSkipInProcess");
        System.out.println("[[[" + throwable.getMessage());
    }
}
