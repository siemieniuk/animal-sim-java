package com.siemieniuk.animals.math;

public class Pair<L, R> {
    private final L left;
    private final R right;

    public Pair(L first, R second) {
        this.left = first;
        this.right = second;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
