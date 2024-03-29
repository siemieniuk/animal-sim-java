package siemieniuk.animals.math;

import lombok.Getter;

@Getter
public class Pair<L, R> {
    private final L left;
    private final R right;

    public Pair(L first, R second) {
        this.left = first;
        this.right = second;
    }

}
