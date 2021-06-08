package com.wyy.engine.eval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @Classname CachedProviderFunction
 * @Description TODO
 * @Date 2021/5/8 16:28
 * @Created by wyy
 */
public class CollectionCalcContext {

    private Map<Function, CollectionCachedProvider> map = new HashMap<>();

    public <L, T, V> Map<L, T> apply(List<L> leftList, Function<List, List> supplier, Function<T, L> right) {

        CollectionCachedProvider collectionCachedProvider = map.get(supplier);
        if (collectionCachedProvider == null) {
            collectionCachedProvider = CollectionCachedProvider.init(supplier, right);
            map.put(supplier, collectionCachedProvider);
        }

        return collectionCachedProvider.calc(leftList);

    }
}
