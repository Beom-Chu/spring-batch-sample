package com.bumsoo.springbatchsample.listener.retry;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

public class CustomRetryListener implements RetryListener {
    @Override
    public <T, E extends Throwable> boolean open(RetryContext retryContext, RetryCallback<T, E> retryCallback) {
        System.out.println("[[[CustomRetryListener.open");
        return true; /* true가 반환 되어야 재시도가 실행됨 */
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
        System.out.println("[[[CustomRetryListener.close");
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
        System.out.println("[[[CustomRetryListener.onError => Retry count : " + retryContext.getRetryCount());
    }
}
