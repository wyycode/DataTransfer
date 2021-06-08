package com.wyy.engine.eval;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Classname CollectionCachedSupplier
 * @Description TODO
 * @Date 2021/5/20 11:38
 * @Created by wyy
 */
public class CollectionCachedProvider<L, T> {

    private Function<List, List> supplier;
    private Function<T, L> right;
    private Map<L, T> map = new HashMap<>();

    private CollectionCachedProvider(Function<List, List> supplier, Function<T, L> right) {
        this.supplier = supplier;
        this.right = right;
    }

    public static <L, T> CollectionCachedProvider<L, T> init(Function<List, List> supplier, Function<T, L> right) {
        return new CollectionCachedProvider<>(supplier, right);
    }

    public Map<L, T> calc(List<L> list) {
        list = list.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        Map<L, T> result = new HashMap<>(list.size());
        List<L> remaining = new ArrayList<>();
        for (L l : list) {
            if (map.containsKey(l)) {
                result.put(l, map.get(l));
            } else {
                remaining.add(l);
            }
        }

        if (remaining.size() > 0) {
            final Collection<T> tCollection = supplier.apply(remaining);
            if (CollectionUtils.isNotEmpty(tCollection)) {
                for (T t : tCollection) {
                    final L l = right.apply(t);
                    map.put(l, t);
                    result.put(l, t);
                }
            }
        }

        return result;
    }
}
