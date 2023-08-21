package com.bumsoo.springbatchsample.item.writer.adpater;

public class ItemWriterAdapterTestService<T> {

    void customWrite(T item) {
        System.out.println("[[[item = " + item);
    }
}
