package util;

import java.util.Set;

/**
 * 空指针检查器
 * 
 * @author James-li
 *
 * 2013-2-21
 */
public class NullChecker {

	private Set<Object> set = CollectionUtils.newHashSet();
	
	/**
	 * 添加受检对象
	 * @param object	对象
	 * @return this
	 */
	public NullChecker add(Object object) {
		set.add(object);
		return this;
	}
	
	/**
	 * 判断是否全部为null
	 * @return <code>true</code> if all is null,else return <code>false</code>
	 */
	public boolean isAllNull() {
		return set.contains(null) && 1 == set.size();
	}
	
	/**
	 * 判断是否有null
	 * @return <code>true</code> if has a null,else return <code>false</code>
	 */
	public boolean hasNull() {
		return set.contains(null);
	}
	
	/**
	 * 判断是否没有null
	 * @return <code>true</code> if none of object is null,else return <code>false</code>
	 */
	public boolean isNoneOfNull() {
		return !hasNull();
	}
	
	
	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(true == new NullChecker().add(null).add("").hasNull());
		System.out.println(false == new NullChecker().add("+").add("").hasNull());
		System.out.println(false == new NullChecker().add(null).add("").isNoneOfNull());
		System.out.println(true == new NullChecker().add("+").add("").isNoneOfNull());
		System.out.println(false == new NullChecker().add(null).add("").isAllNull());
		System.out.println(true == new NullChecker().add(null).add(null).isAllNull());
	}
	
}
