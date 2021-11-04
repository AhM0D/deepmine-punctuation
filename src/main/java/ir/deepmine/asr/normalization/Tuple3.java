package ir.deepmine.asr.normalization;

public class Tuple3<T1, T2, T3> extends Tuple2<T1, T2> {
	public T3 Item3;

	public Tuple3(T1 item1, T2 item2, T3 item3) {
		super(item1, item2);
		Item3 = item3;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (!(obj instanceof Tuple3<?, ?, ?>)) {
			return false;
		}
		Tuple3<?, ?, ?> other = (Tuple3<?, ?, ?>) obj;
		return equals(this.Item1, other.Item1) && equals(this.Item2, other.Item2)
				&& equals(this.Item3, other.Item3);
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = 79 * hash + (this.Item3 != null ? this.Item3.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return super.toString() + "," + Item3.toString();
	}
}
