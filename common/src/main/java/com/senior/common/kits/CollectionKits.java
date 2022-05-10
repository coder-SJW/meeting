
package com.senior.common.kits;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.MoreObjects;

/**
 * 集合工具类
 * 
 * @author senior
 */
public class CollectionKits {
    private CollectionKits() {
    }

    public static <T> List<T> nullToEmpty(List<T> list) {
        return MoreObjects.firstNonNull(list, Collections.emptyList());
    }

    public static <T> Set<T> nullToEmpty(Set<T> set) {
        return MoreObjects.firstNonNull(set, Collections.emptySet());
    }

    public static <K, V> Map<K, V> nullToEmpty(Map<K, V> map) {
        return MoreObjects.firstNonNull(map, Collections.emptyMap());
    }
}
