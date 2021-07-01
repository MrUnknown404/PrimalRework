package mrunknown404.primalrework.utils;

public class Cache<K, V> {
	protected K key;
	protected V value;
	
	public void set(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public boolean isEmpty() {
		return key == null;
	}
	
	public boolean is(K key) {
		return this.key == key || (this.key != null && this.key.equals(key));
	}
	
	public V get() {
		return value;
	}
}
