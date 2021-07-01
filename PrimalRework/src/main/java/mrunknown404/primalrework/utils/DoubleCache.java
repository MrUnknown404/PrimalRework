package mrunknown404.primalrework.utils;

public class DoubleCache<K0, K1, V> {
	protected K0 key0;
	protected K1 key1;
	protected V value;
	
	public void set(K0 key0, K1 key1, V value) {
		this.key0 = key0;
		this.key1 = key1;
		this.value = value;
	}
	
	public boolean isEmpty() {
		return key0 == null && key1 == null;
	}
	
	public boolean is(K0 key0, K1 key1) {
		return (this.key0 == key0 || (this.key0 != null && this.key0.equals(key0))) && (this.key1 == key1 || (this.key1 != null && this.key1.equals(key1)));
	}
	
	public V get() {
		return value;
	}
}
