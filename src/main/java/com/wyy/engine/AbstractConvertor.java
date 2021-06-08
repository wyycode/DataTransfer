package com.wyy.engine;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Classname ObjectConvertor
 * @Description TODO
 * @Date 2021/5/28 09:28
 * @Created by wyy
 */
public class AbstractConvertor<S, V> {
    protected Class<V>           vClass;
    protected transient Transfer current;
    protected List<Transfer>     transfers;

    public AbstractConvertor<S, V> links(boolean use, L... ls) {
        if (current != null) {
            throw new NullPointerException();
        }
        current = new Transfer(use, Arrays.asList(ls));
        return this;
    }

    public AbstractConvertor<S, V> transfers(boolean use, T... transfers) {
        Objects.requireNonNull(transfers);
        if (current != null) {
            current.setTransfers(Arrays.asList(transfers));
            this.transfers.add(current);
            current = null;
        } else {
            for (T t : transfers) {
                this.transfers.add(new Transfer(use, t.getter, t.setter));
            }
        }
        return this;
    }

    public <D> AbstractConvertor<S, V> transfer(boolean use, Function<S, D> function, BiConsumer<V, D> setter) {
        this.transfers.add(new Transfer(use, function, setter));
        return this;
    }

    public <D> AbstractConvertor<S, V> transfer(boolean use, Supplier<D> function, BiConsumer<V, D> setter) {
        this.transfers.add(new Transfer(use, function, setter));
        return this;
    }

    public <D> AbstractConvertor<S, V> directTransfer(boolean use, D d, BiConsumer<V, D> setter) {
        this.transfers.add(new Transfer(use, d, setter));
        return this;
    }

    public AbstractConvertor<S, V> links(L... ls) {
        if (current != null) {
            throw new NullPointerException();
        }
        current = new Transfer(true, Arrays.asList(ls));
        return this;
    }

    public AbstractConvertor<S, V> transfers(T... transfers) {
        Objects.requireNonNull(transfers);
        if (current != null) {
            current.setTransfers(Arrays.asList(transfers));
            this.transfers.add(current);
            current = null;
        } else {
            for (T t : transfers) {
                this.transfers.add(new Transfer(true, t.getter, t.setter));
            }
        }
        return this;
    }

    public <D> AbstractConvertor<S, V> transfer(Function<S, D> function, BiConsumer<V, D> setter) {
        this.transfers.add(new Transfer(true, function, setter));
        return this;
    }

    public <D> AbstractConvertor<S, V> transfer(Supplier<D> function, BiConsumer<V, D> setter) {
        this.transfers.add(new Transfer(true, function, setter));
        return this;
    }

    public <D> AbstractConvertor<S, V> directTransfer(D d, BiConsumer<V, D> setter) {
        this.transfers.add(new Transfer(true, d, setter));
        return this;
    }

}
