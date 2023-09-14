package com.bumsoo.springbatchsample.listener.skip;

public class CustomSkipException extends RuntimeException {
    public CustomSkipException() {
        super();
    }

    public CustomSkipException(String s) {
        super(s);
        System.out.println("CustomSkipException.CustomSkipException : " + s);
    }
}
