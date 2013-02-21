package util;

import java.util.List;

/**
 * 空指针检查器
 * 
 * @author James-li
 *
 * 2013-2-21
 */
public class NullChecker {

	private List<Object> list = CollectionUtils.newArrayList();
	
	/**
	 * 添加受检对象
	 * @param object	对象
	 * @return this
	 */
	public NullChecker add(Object object) {
		list.add(object);
		return this;
	}
	
	/**
	 * 判断是否全部为null
	 * @return <code>true</code> if all is null,else return <code>false</code>
	 */
	public boolean isAllNull() {
		for(Object object : list) {
			if(ObjectUtils.isNotNull(object)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断是否有null
	 * @return <code>true</code> if has a null,else return <code>false</code>
	 */
	public boolean hasNull() {
		for(Object object : list) {
			if(ObjectUtils.isNull(object)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否没有null
	 * @return <code>true</code> if none of object is null,else return <code>false</code>
	 */
	public boolean isNoneOfNull() {
		return !hasNull();
	}
	
}
