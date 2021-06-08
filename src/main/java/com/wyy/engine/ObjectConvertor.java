package com.wyy.engine;

import com.wyy.engine.eval.CalcContext;
import com.wyy.engine.utils.BeanPlusUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname ObjectConvertor
 * @Description TODO
 * @Date 2021/5/28 09:28
 * @Created by wyy
 */
public class ObjectConvertor<S, V> extends AbstractConvertor<S, V> {
    private S s;

    ObjectConvertor(S s, Class<V> vClass) {
        this.s = s;
        this.vClass = vClass;
        this.transfers = new ArrayList<>();
    }

    public static <S, V> ObjectConvertor<S, V> init(S s, Class<V> vClass) {
        return new ObjectConvertor<>(s, vClass);
    }

    public V convert() {
        if (s == null) {
            return null;
        }
        V v = BeanPlusUtil.toBean(s, vClass);
        final Map<Boolean, List<Transfer>> transferSplit = transfers.stream().filter(t -> t.use).collect(Collectors.groupingBy(t -> CollectionUtils.isEmpty(t.ls)));
        final List<Transfer> transferList = transferSplit.get(false);
        if (transferList != null) {
            for (Transfer transfer : transferList) {
                if (transfer.data != null) {
                    transfer.consumer.accept(v, transfer.data);
                } else if (transfer.supplier != null) {
                    transfer.consumer.accept(v, transfer.supplier.get());
                } else if (transfer.function != null) {
                    transfer.consumer.accept(v, transfer.function.apply(s));
                }
            }
        }

        CalcContext context = new CalcContext();
        Object tmp;
        if (transferList != null) {
            for (Transfer transfer : transferList) {
                tmp = s;
                final List<L> ls = transfer.ls;
                for (L l : ls) {
                    if (l.supplier != null) {
                        tmp = l.supplier.get();
                    } else {
                        tmp = context.apply(l.convertor, l.left.apply(tmp));
                    }
                }
                final List<T> transfers = transfer.transfers;
                for (T t : transfers) {
                    t.setter.accept(v, t.getter.apply(tmp));
                }
            }
        }
        return v;
    }
}
