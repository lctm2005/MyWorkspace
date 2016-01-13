package util;

/**
 * String Util
 * 
 * @author James-li
 *
 * 2013-2-20
 */
public class StringUtils {

	private static final String EMPTY_STRING = "";	// 空字符串
	
	/**
	 * Determine if string is empty
	 * @param string
	 * @return <code>true</code> if string == null or string equals "",else return <code>false</code>
	 */
	public static final boolean isEmpty(final String string) {
		if(null == string || EMPTY_STRING.equals(string)) {
			return true;
		}
		return false;
	}

	/**
	 * Determine if string is not empty
	 * @param string
	 * @return <code>false</code> if string == null or string equals "",else return <code>true</code>
	 */
	public static final boolean isNotEmpty(final String string) {
		return !isEmpty(string);
	}
}
