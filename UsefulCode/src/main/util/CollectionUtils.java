package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import static util.ObjectUtils.*;

/**
 * 集合工具类
 * 
 * @author James-li
 *
 * 2013-2-16
 */
public class CollectionUtils {

	/**
	 * 只检查容器是否为空，不涉及类型转换
	 * @param collection	受检容器
	 * @return	 <code>true</code> if collection is empty
	 */
	@SuppressWarnings("rawtypes")
	public static final boolean isEmpty(final Collection collection) {
		return null == collection || collection.isEmpty();
	}
	
	/**
	 * 只检查Map是否为空，不涉及类型转换
	 * @param map 受检Map
	 * @return <code>true</code> if map is empty
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(final Map map) {
		return map == null || map.isEmpty();
	}

	/**
	 * 数组转为List
	 * @param array	数组对象
	 * @return List
	 */
	public static final <T> List<T> arrayToList(final T[] array) {
		if (array == null) {
			return Collections.emptyList();
		}
		if (array.length == 0) {
			return Collections.emptyList();
		}
		return Arrays.asList(array);
	}
	
	/**
	 * 支持泛型的新建HashMap方法，书写方便
	 * @return 新建的HashMap
	 */
	public static final <K,V> HashMap<K,V> newHashMap() {
		return new HashMap<K,V>();
	}
	
	/**
	 * 支持泛型的新建ArrayList方法，书写方便
	 * @return 新建的ArrayList
	 */
	public static final <T> ArrayList<T> newArrayList() {
		return new ArrayList<T>();
	}
	
	/**
	 * 支持泛型的新建LinkedList方法，书写方便
	 * @return 新建的LinkedList
	 */
	public static final <T> LinkedList<T> newLinkedList() {
		return new LinkedList<T>();
	}
	
	/**
	 * 支持泛型的新建HashSet方法，书写方便
	 * @return 新建的HashSet
	 */
	public static final <T> HashSet<T> newHashSet() {
		return new HashSet<T>();
	}
	
	/**
	 * Map转List
	 * @param <K>	Map的KEY类型
	 * @param <T>	Map的VALUE类型
	 * @param map	Map对象
	 * @return
	 */
	public static final <K,T> List<T> mapToList(Map<K,T> map) {
		if(isNull(map) || map.isEmpty()) {
			return Collections.emptyList();
		}
		List<T> list = newArrayList();
		Set<Entry<K,T>> entrySet = map.entrySet();
		for(Entry<K,T> e : entrySet) {
			list.add(e.getValue());
		}
		return list;
	}
	
	/**
	 * Set转List
	 * @param set Set对象
	 * @return
	 */
	public static final <T> List<T> setToList(Set<T> set) {
		if(isNull(set) || set.isEmpty()) {
			return Collections.emptyList();
		}
		List<T> list = newArrayList();
		Iterator<T> it = set.iterator();
		while(it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
}
