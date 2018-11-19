package com.amazonaws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Resource: https://hzhou.me/2017/08/23/A-simple-workaround-for-Cannot-find-a-Map-Key-deserializer/

public class ObjectUtils {
	public static <K, V> List<KeyValueContainer<K, V>> toList(Map<K, V> map) {
		if (map == null || map.isEmpty()) {
			return new ArrayList<>();
		}
		return map.entrySet().stream().map(e -> new KeyValueContainer<>(e.getKey(), e.getValue()))
				.collect(Collectors.toList());
	}

	public static <K, V> Map<K, V> toMap(List<KeyValueContainer<K, V>> list) {
		if (list == null || list.isEmpty()) {
			return new HashMap<>();
		}
		return list.stream().collect(Collectors.toMap(KeyValueContainer::getKey, KeyValueContainer::getValue));
	}
}
