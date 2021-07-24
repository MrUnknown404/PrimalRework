package mrunknown404.primalrework.utils;

public class Pair<L, R> {
	private L left;
	private R right;
	
	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}
	
	public Pair<L, R> setL(L left) {
		this.left = left;
		return this;
	}
	
	public Pair<L, R> setR(R right) {
		this.right = right;
		return this;
	}
	
	public Pair<L, R> setLeft(L left) {
		this.left = left;
		return this;
	}
	
	public Pair<L, R> setRight(R right) {
		this.right = right;
		return this;
	}
	
	public L getL() {
		return left;
	}
	
	public R getR() {
		return right;
	}
	
	public L getLeft() {
		return left;
	}
	
	public R getRight() {
		return right;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Pair)) {
			return false;
		}
		
		Pair<?, ?> other = (Pair<?, ?>) obj;
		if (left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!left.equals(other.left)) {
			return false;
		}
		
		if (right == null) {
			if (other.right != null) {
				return false;
			}
		} else if (!right.equals(other.right)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return "(" + left + ", " + right + ")";
	}
}
