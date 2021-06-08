package com.wyy.engine;


import com.wyy.function.CachedProvider;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Classname Line
 * @Description TODO
 * @Date 2021/5/26 11:02
 * @Created by wyy
 */
public class DL<K, L, R> {
    Function<L, K> left;
    Function<K, R> convertor;
    Supplier<R>    supplier;

    private DL(Function<L, K> left, Function<K, R> convertor) {
        this.left = left;
        this.convertor = convertor;
    }

    private DL(Function<L, K> left, Supplier<R> supplier) {
        this.left = left;
        if (supplier instanceof CachedProvider) {
            this.supplier = supplier;
        } else {
            this.supplier = CachedProvider.init(supplier);
        }
    }

    public static <K, L, R> DL<K, L, R> l(Function<L, K> left, Function<K, R> convertor) {
        return new DL(left, convertor);
    }

    public static <K, L, R> DL<K, L, R> l(Function<L, K> left, Supplier<R> supplier) {
        return new DL(left, supplier);
    }
}
