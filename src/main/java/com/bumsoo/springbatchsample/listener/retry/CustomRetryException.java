package com.bumsoo.springbatchsample.listener.retry;

public class CustomRetryException extends Exception {
    public CustomRetryException(String msg) {
        super(msg);
        System.out.println("[[[CustomRetryException.CustomRetryException");
    }
}
