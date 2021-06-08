package com.wyy.engine;


import com.wyy.function.CachedProvider;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Classname Line
 * @Description TODO
 * @Date 2021/5/26 11:02
 * @Created by wyy
 */
public class L<K, L, R> {
    Function<L, K> left;
    Function<R, K> right;
    Function convertor;
    Supplier supplier;

    private L(Function<L, K> left, Function<R, K> right, Function<List<K>, List<R>> convertor) {
        this.left = left;
        this.right = right;
        this.convertor = convertor;
    }

    private L(Function<L, K> left, Function<R, K> right, Supplier<List<R>> supplier) {
        this.left = left;
        this.right = right;
        if (supplier instanceof CachedProvider) {
            this.supplier = supplier;
        } else {
            this.supplier = CachedProvider.init(supplier);
        }
    }

    public static <K, L, R> com.wyy.engine.L<K, L, R> l(Function<L, K> left, Function<R, K> right, Function<List<K>, List<R>> convertor) {
        return new com.wyy.engine.L(left, right, convertor);
    }

    public static <K, L, R> com.wyy.engine.L<K, L, R> l(Function<L, K> left, Function<R, K> right, Supplier<List<R>> supplier) {
        return new com.wyy.engine.L(left, right, supplier);
    }

    private L(Function<L, K> left, Function<K, R> convertor) {
        this.left = left;
        this.convertor = convertor;
    }

    private L(Supplier<R> supplier) {
        if (supplier instanceof CachedProvider) {
            this.supplier = supplier;
        } else {
            this.supplier = CachedProvider.init(supplier);
        }
    }

    public static <K, L, R> com.wyy.engine.L<K, L, R> l(Function<L, K> left, Function<K, R> convertor) {
        return new com.wyy.engine.L(left, convertor);
    }

    public static <K, L, R> com.wyy.engine.L<K, L, R> l(Supplier<R> supplier) {
        return new com.wyy.engine.L(supplier);
    }
}
