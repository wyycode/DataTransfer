package com.wyy.function;

/**
 * @Classname Predicate
 * @Description TODO
 * @Date 2021/4/21 16:37
 * @Created by wyy
 */
@FunctionalInterface
public interface Predicate<T> extends java.util.function.Predicate<T> {

    boolean apply(T t);

    default <R extends T> Predicate<R> and(Predicate<R> and) {
        return t -> test(t) && and.test(t);
    }

    default Predicate<T> or(Predicate<T> or) {
        return t -> test(t) || or.test(t);
    }

    @Override
    default Predicate<T> negate() {
        return t -> !test(t);
    }

    @Override
    default boolean test(T o) {
        return apply(o);
    }
}
