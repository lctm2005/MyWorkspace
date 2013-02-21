package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
}
