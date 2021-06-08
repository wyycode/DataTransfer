package com.wyy.function;

import java.util.function.Supplier;

/**
 * @Classname CachedSupplier
 * @Description TODO
 * @Date 2021/4/21 11:36
 * @Created by wyy
 */
public class CachedProvider<T> implements Supplier<T> {

    private T           data;
    private Supplier<T> supplier;

    private CachedProvider(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> CachedProvider init(Supplier<T> supplier) {
        return new CachedProvider(supplier);
    }

    @Override
    public T get() {
        if (data == null) {
            synchronized (this) {
                if (data == null) {
                    data = supplier.get();
                }
            }
        }
        return data;
    }

    public T reset() {
        T tmp = data;
        data = null;
        return tmp;
    }
}
