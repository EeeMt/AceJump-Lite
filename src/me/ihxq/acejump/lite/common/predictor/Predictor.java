package me.ihxq.acejump.lite.common.predictor;

public abstract class Predictor<T> {
    abstract boolean is(T t);

    public boolean isNot(T t) {
        return !is(t);
    }
}
