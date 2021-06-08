package com.wyy.engine;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @Classname Setter
 * @Description TODO
 * @Date 2021/5/27 16:14
 * @Created by wyy
 */
public class T<L, R, V> {
    Function<L, V>   getter;
    BiConsumer<R, V> setter;

    private T(Function<L, V> getter, BiConsumer<R, V> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public static <L, R, V> T<L, R, V> t(Function<L, V> getter, BiConsumer<R, V> setter) {
        return new T<L, R, V>(getter, setter);
    }
}
