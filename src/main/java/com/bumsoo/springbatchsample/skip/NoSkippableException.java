package com.bumsoo.springbatchsample.skip;

public class NoSkippableException extends Exception {
    public NoSkippableException(String s) {
        System.out.println(s);
    }
}
