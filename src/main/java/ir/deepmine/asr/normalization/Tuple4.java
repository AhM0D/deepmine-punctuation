package ir.deepmine.asr.normalization;

public class Tuple4<T1, T2, T3, T4> extends Tuple3<T1, T2, T3>{
	public T4 Item4;

	public Tuple4(T1 item1, T2 item2, T3 item3, T4 item4) {
		super(item1, item2, item3);
		Item4 = item4;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (!(obj instanceof Tuple4<?, ?, ?, ?>)) {
			return false;
		}
		Tuple4<?, ?, ?, ?> other = (Tuple4<?, ?, ?, ?>) obj;
		return equals(this.Item1, other.Item1) && equals(this.Item2, other.Item2) && equals(this.Item3, other.Item3) && equals(this.Item4, other.Item4);
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = 79 * hash + (this.Item4 != null ? this.Item4.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return super.toString() + "," + Item4.toString();
	}
}
