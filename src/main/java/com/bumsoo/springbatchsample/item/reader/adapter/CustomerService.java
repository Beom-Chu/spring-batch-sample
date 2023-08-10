package com.bumsoo.springbatchsample.item.reader.adapter;

public class CustomerService<T> {
    private int cnt = 0;
    public T joinMember() {
        if(cnt > 50) {
            return null;
        }
        return (T)("item" + cnt++);
    }
}
