package com.wyy.engine;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Classname Transfor
 * @Description TODO
 * @Date 2021/5/26 15:24
 * @Created by wyy
 */
public class Transfer<V, D> {
    boolean          use;
    List<L>          ls;
    List<T>          transfers;
    Function<?, D>   function;
    Supplier<D>      supplier;
    D                data;

    BiConsumer<V, D> consumer;

    public Transfer(boolean use, Function<?, D> function, BiConsumer<V, D> consumer) {
        this.consumer = consumer;
        this.function = function;
    }

    public Transfer(boolean use, Supplier<D> supplier, BiConsumer<V, D> consumer) {
        this.consumer = consumer;
        this.supplier = supplier;
    }

    public Transfer(boolean use, D d, BiConsumer<V, D> consumer) {
        this.consumer = consumer;
        this.data = d;
    }

    public Transfer(boolean use, List<L> ls) {
        this.ls = ls;
    }

    public void setTransfers(List<T> transfers) {
        this.transfers = transfers;
    }
}
