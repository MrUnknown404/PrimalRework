package mrunknown404.primalrework.utils;

import java.util.function.Supplier;

public class DoubleCache<K0, K1, V> {
	protected K0 key0;
	protected K1 key1;
	protected V value;
	private final DoubleCachePredicate<K0, K1> isPredicate;
	
	private DoubleCache(DoubleCachePredicate<K0, K1> isPredicate) {
		this.isPredicate = isPredicate;
	}
	
	public static <K0, K1, V> DoubleCache<K0, K1, V> create(DoubleCachePredicate<K0, K1> isPredicate) {
		return new DoubleCache<K0, K1, V>(isPredicate);
	}
	
	public static <K0, K1, V> DoubleCache<K0, K1, V> and() {
		return new DoubleCache<K0, K1, V>(
				(okey0, okey1, key0, key1) -> (okey0 == key0 || (okey0 != null && okey0.equals(key0))) && (okey1 == key1 || (okey1 != null && okey1.equals(key1))));
	}
	
	public static <K0, K1, V> DoubleCache<K0, K1, V> or() {
		return new DoubleCache<K0, K1, V>(
				(okey0, okey1, key0, key1) -> (okey0 == key0 || (okey0 != null && okey0.equals(key0))) || (okey1 == key1 || (okey1 != null && okey1.equals(key1))));
	}
	
	public V set(K0 key0, K1 key1, V value) {
		this.key0 = key0;
		this.key1 = key1;
		this.value = value;
		return value;
	}
	
	public V computeIfAbsent(K0 key0, K1 key1, Supplier<V> value) {
		return is(key0, key1) ? this.value : set(key0, key1, value.get());
	}
	
	public boolean isEmpty() {
		return key0 == null && key1 == null;
	}
	
	public boolean is(K0 key0, K1 key1) {
		return isPredicate.test(this.key0, this.key1, key0, key1);
	}
	
	public V get() {
		return value;
	}
	
	@FunctionalInterface
	public interface DoubleCachePredicate<K0, K1> {
		boolean test(K0 ok0, K1 ok1, K0 k1, K1 k2);
	}
}
