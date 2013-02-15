package ipseeker;

/**
 * 测试类
 * 
 * @author James-li
 *
 * 2013-2-13
 */
public class Test {
	
	public static final void main(String[] args) {
		IPSeeker seeker = IPSeeker.getInstance();
		// 执行256*256=65536次
		for (int j = 0; j < 255; j++) {
			StringBuffer ipBuf = new StringBuffer("218.66.");
			ipBuf.append(j).append(".");
			for (int i = 0; i < 255; i++) {
				ipBuf.append(i);
				System.out.println(seeker.getAddress(ipBuf.toString()));
			}
		}
	}
}
