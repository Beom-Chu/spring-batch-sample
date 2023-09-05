package com.bumsoo.springbatchsample.retry;

public class RetryableException extends RuntimeException {
    public RetryableException() {
        super();
        System.out.println("RetryableException.RetryableException");
    }

    public RetryableException(String message) {
        super(message);
        System.out.println("[[[RetryableException:message = " + message);
    }
}
