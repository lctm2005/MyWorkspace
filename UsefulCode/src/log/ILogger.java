package log;

/**
 * 日志接口
 * 
 * @author James-li
 *
 * 2013-6-26
 */
public interface ILogger {

	/**
	 * 输出INFO日志
	 * @param info
	 */
	public void info(String info);
	
	/**
	 * 输出ERROR日志
	 * @param info
	 */
	public void error(String info);
	
	/**
	 * 输出ERROR日志
	 * @param info
	 * @param exception
	 */
	public void error(String info,Throwable exception);
	
	/**
	 * 输出DEBUG日志
	 * @param info
	 */
	public void debug(String info);
}
