package urlencode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Url 编码器</br>
 * 解决URLEncoder行为不符合Url编码规范的问题
 * 
 * @author James-li
 *
 * 2013-2-13
 */
public class MyUrlEncoder {
	
	private static final String ENCODING = "UTF-8";

	/**
	 * 编码
	 * @param toEncode	待编码字符串
	 * @return 编码结果
	 */
	public static String encode(String toEncode) {
		if (toEncode == null || "".equals(toEncode)) {
			return "";
		}
		try {
			return URLEncoder.encode(toEncode, ENCODING)
					.replace("+", "%20").replace("*", "%2A")
					.replace("%7E", "~");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 解码
	 * @param toDecode 待解码字符串
	 * @return 解码结果
	 */
	public static String decode(String toDecode) {
		if (toDecode == null || "".equals(toDecode)) {
			return "";
		}
		try {
			return URLDecoder.decode(toDecode, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Main Method
	 * @param args 参数
	 */
	public static void main(String[] args) {
		String ori = "你们好 ~!@#$%^&*()_+-={}[]\';|:?><,./%1234567890qwertyupoiasdfkjl;ghzcvxcvbmn";
		System.out.println("ToEncode：" + ori);
		String encoded = encode(ori);
		System.out.println("Encode Result：" + encoded);
		String dst = decode(encoded);
		System.out.println("Docode Result：" + dst);
		System.out.println("ToEncode equals Docode Result ：" + ori.equals(dst));
	}
}
