package com.amazonaws;

// Resource: https://hzhou.me/2017/08/23/A-simple-workaround-for-Cannot-find-a-Map-Key-deserializer/

public class KeyValueContainer<K, V> {
	private K key;
	private V value;

	public KeyValueContainer() {
	} // this is required by Jackson

	public KeyValueContainer(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return this.key;
	}
	public void setKey(K val) {
		this.key = val;
	}
	
	public V getValue() {
		return this.value;
	}
	public void setValue(V val) {
		this.value = val;
	}
}
