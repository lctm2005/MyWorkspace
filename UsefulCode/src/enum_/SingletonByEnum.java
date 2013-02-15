package enum_;

/**
 * 通过枚举实现单里
 * 
 * @author James-li
 * 
 *  2013-2-13
 */
public enum SingletonByEnum {
	
	/**
	 * singleton instance
	 */
	INSTANCE;
	
	private int count = 0;			//count number of instance
	
	SingletonByEnum() {
		count ++;
	}
	
	/**
	 * print number of instance
	 */
	public void count() {
		System.out.println(" number of instance is " + count);
	}

}
