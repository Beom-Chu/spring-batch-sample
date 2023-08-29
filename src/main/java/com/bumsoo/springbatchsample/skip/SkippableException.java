package com.bumsoo.springbatchsample.skip;

public class SkippableException extends Exception {
    public SkippableException(String s) {
        System.out.println(s);
    }
}
