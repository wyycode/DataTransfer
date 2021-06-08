package com.wyy.engine.eval;

import java.util.Optional;
import java.util.WeakHashMap;
import java.util.function.Function;

/**
 * @Classname CachedProviderFunction
 * @Description TODO
 * @Date 2021/5/8 16:28
 * @Created by wyy
 */
public class CalcContext {

    private WeakHashMap<String, Object> map = new WeakHashMap<>();

    public <T, R> Object apply(Function<T, R> function, T t) {
        String key = System.identityHashCode(function) + "-" + System.identityHashCode(t);
        return Optional.ofNullable(map.get(key)).orElseGet(() -> {
            final R apply = function.apply(t);
            map.put(key, apply);
            return apply;
        });
    }
}
