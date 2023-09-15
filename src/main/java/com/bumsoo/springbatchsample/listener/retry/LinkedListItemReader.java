package com.bumsoo.springbatchsample.listener.retry;

import com.bumsoo.springbatchsample.listener.skip.CustomSkipException;
import org.springframework.aop.support.AopUtils;
import org.springframework.batch.item.ItemReader;

import java.util.LinkedList;
import java.util.List;

public class LinkedListItemReader<T> implements ItemReader<T> {

    private List<T> list;

    public LinkedListItemReader(List<T> list) {
        if (AopUtils.isAopProxy(list)) {
            this.list = list;
        } else {
            this.list = new LinkedList<>(list);
        }
    }

    @Override
    public T read() throws CustomSkipException {

        if (!list.isEmpty()) {
            T remove = list.remove(0);
            return remove;
        }
        return null;
    }
}
