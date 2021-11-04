package ir.deepmine.asr.normalization;

import java.util.Objects;

class Tuple2<T1, T2> {
    public T1 Item1;
    public T2 Item2;

    Tuple2(T1 item1, T2 item2) {
        Item1 = item1;
        Item2 = item2;
    }

    protected boolean equals(Object x, Object y) {
        return Objects.equals(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Tuple2<?, ?>)) {
            return false;
        }
        Tuple2<?, ?> other = (Tuple2<?, ?>) obj;
        return equals(this.Item1, other.Item1) && equals(this.Item2, other.Item2);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.Item1 != null ? this.Item1.hashCode() : 0);
        hash = 79 * hash + (this.Item2 != null ? this.Item2.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return Item1.toString() + "," + Item2.toString();
    }
}
