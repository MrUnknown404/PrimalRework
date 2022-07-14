package mrunknown404.primalrework.utils;

import java.util.function.Supplier;

public class Cache<K, V> {
	protected K key;
	protected V value;
	private final CachePredicate<K> isPredicate;
	
	private Cache(CachePredicate<K> isPredicate) {
		this.isPredicate = isPredicate;
	}
	
	public static <K, V> Cache<K, V> create(CachePredicate<K> isPredicate) {
		return new Cache<K, V>(isPredicate);
	}
	
	public static <K, V> Cache<K, V> create() {
		return new Cache<K, V>((k0, k1) -> k0 == k1 || (k0 != null && k0.equals(k1)));
	}
	
	public V set(K key, V value) {
		this.key = key;
		this.value = value;
		return value;
	}
	
	public V computeIfAbsent(K key, Supplier<V> value) {
		return is(key) ? this.value : set(key, value.get());
	}
	
	public boolean isEmpty() {
		return key == null;
	}
	
	public boolean is(K key) {
		return isPredicate.test(this.key, key);
	}
	
	public V get() {
		return value;
	}
	
	@FunctionalInterface
	public interface CachePredicate<K> {
		boolean test(K k0, K k1);
	}
}
