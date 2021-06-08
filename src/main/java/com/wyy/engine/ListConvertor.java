package com.wyy.engine;


import com.wyy.engine.eval.CollectionCalcContext;
import com.wyy.engine.utils.BeanPlusUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Classname ListConvertor
 * @Description TODO
 * @Date 2021/5/26 10:48
 * @Created by wyy
 */
public class ListConvertor<S, V> extends AbstractConvertor<S, V> {
    private List<S> sourceList;

    private ListConvertor(List<S> sourceList, Class<V> vClass) {
        this.sourceList = sourceList;
        this.vClass = vClass;
        this.transfers = new ArrayList<>();
    }

    public static <S, V> ListConvertor<S, V> init(List<S> sourceList, Class<V> vClass) {
        return new ListConvertor<>(sourceList, vClass);
    }

    private List<Pair<V, Object>> buildFirst(List<Transfer> directTransfers) {
        List<Pair<V, Object>> result = new ArrayList<>(sourceList.size());
        for (S s : sourceList) {
            final V v = BeanPlusUtil.toBean(s, vClass);
            result.add(Pair.init(v, s));
        }
        if (directTransfers != null) {
            for (Pair<V, Object> p : result) {
                for (Transfer transfer : directTransfers) {
                    final BiConsumer<V, Object> consumer = transfer.consumer;
                    final V v = p.getL();
                    if (transfer.data != null) {
                        consumer.accept(v, transfer.data);
                    } else if (transfer.supplier != null) {
                        consumer.accept(v, transfer.supplier.get());
                    } else if (transfer.function != null) {
                        consumer.accept(v, transfer.function.apply(p.getR()));
                    }
                }
            }
        }
        return result;
    }

    public List<V> convert() {

        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.EMPTY_LIST;
        }

        final Map<Boolean, List<Transfer>> transferSplit = transfers.stream().filter(t -> t.use)
                .collect(Collectors.groupingBy(t -> CollectionUtils.isEmpty(t.ls)));
        final List<Pair<V, Object>> list = buildFirst(transferSplit.get(true));
        Object[] s = new Object[list.size()];
        for (int i = 0; i < s.length; i++) {
            s[i] = list.get(i).getR();
        }

        CollectionCalcContext context = new CollectionCalcContext();

        final List<Transfer> transfers = transferSplit.get(false);
        if (transfers != null) {
            for (Transfer transfer : transfers) {
                apply(transfer, list, context, s);
            }
        }
        return list.stream().map(Pair::getL).collect(Collectors.toList());
    }

    private void apply(Transfer transfer, List<Pair<V, Object>> list, CollectionCalcContext context, Object[] s) {

        List<L> ls = transfer.ls;
        for (L l : ls) {
            final Function left = l.left;
            list.forEach(p -> p.setR(left.apply(p.getR())));

            Map apply;
            final Supplier<List> supplier = l.supplier;
            if (supplier != null) {
                apply = (Map) Optional.ofNullable(supplier.get()).orElse(Collections.emptyList()).stream()
                        .collect(Collectors.toMap(l.right, Function.identity()));
            } else {
                apply = context.apply(list.stream().map(Pair::getR).collect(Collectors.toList()), l.convertor, l.right);
            }

            for (Pair pair : list) {
                pair.setR(apply.get(pair.getR()));
            }
        }

        List<T> transfers = transfer.transfers;
        for (int i = 0; i < s.length; i++) {
            Pair<V, Object> pair = list.get(i);
            for (T t : transfers) {
                t.setter.accept(pair.getL(), t.getter.apply(pair.getR()));
            }
            pair.setR(s[i]);
        }
    }
}
