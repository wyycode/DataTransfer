package com.wyy.function;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Classname CachedSupplier
 * @Description TODO
 * @Date 2021/4/21 11:36
 * @Created by wyy
 */
public class CachedConvertor<T, R> implements Supplier<R> {

    private R              data;
    private T              t;
    private Function<T, R> supplier;

    private CachedConvertor(Function<T, R> supplier, T t) {
        this.supplier = supplier;
        this.t = t;
    }

    public static <T, R> CachedConvertor init(Function<T, R> supplier, T t) {
        return new CachedConvertor(supplier, t);
    }

    @Override
    public R get() {
        if (data == null) {
            synchronized (this) {
                if (data == null) {
                    data = supplier.apply(t);
                }
            }
        }
        return data;
    }

    public R reset() {
        R tmp = data;
        data = null;
        return tmp;
    }
}
