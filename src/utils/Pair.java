package utils;

import java.util.Objects;

public class Pair<E1,E2> {
    public final E1 _1;
    public final E2 _2;

    public Pair(E1 _1, E2 _2){
        this._1 = _1;
        this._2 = _2;
    }

    @Override
    public String toString() {
        return "utils.Pair{" +
                "_1=" + _1 +
                ", _2=" + _2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(_1, pair._1) &&
                Objects.equals(_2, pair._2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2);
    }
}
