package com.wyy.engine;

/**
 * @Classname Pairs
 * @Description TODO
 * @Date 2021/5/24 15:31
 * @Created by wyy
 */
public class Pair<L, R> {

    private L l;
    private R r;

    private Pair(L l, R r) {
        this.l = l;
        this.r = r;
    }

    public static <L, R> Pair<L, R> init(L l, R r) {
        Pair<L, R> pair = new Pair<>(l, r);
        return pair;
    }

    public L getL() {
        return l;
    }

    public R getR() {
        return r;
    }

    public void setR(R r) {
        this.r = r;
    }
}
