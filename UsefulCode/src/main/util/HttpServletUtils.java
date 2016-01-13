package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 为javax.servlet.http包的一些类提供工具方法
 * 
 * @author James-li
 * 
 *         2013-2-21
 */
public class HttpServletUtils {

	/**
	 * 获取Cookie值
	 * 
	 * @param cookies
	 *            Cookie数组
	 * @param name
	 *            键
	 * @return name对应的value
	 */
	public static final String getCookie(Cookie[] cookies, String name) {
		if (ObjectUtils.isEmpty(cookies) || StringUtils.isEmpty(name)) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * 压缩Cookie
	 * 
	 * @param cookie
	 *            Cookie
	 * @param response
	 *            HttpServletResponse
	 */
	public static final void compressCookie(Cookie cookie,
			HttpServletResponse response) {
		if (new NullChecker().add(cookie).add(response).hasNull()) {
			return;
		}
		String value = cookie.getValue();
		if (StringUtils.isEmpty(value)) {
			return;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DeflaterOutputStream dos = new DeflaterOutputStream(bos);
		try {
			dos.write(value.getBytes());
			dos.close();
			String compress = new BASE64Encoder().encode(bos.toByteArray());
			bos.close();
			response.addCookie(new Cookie("compress", compress));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ObjectUtils.isNotNull(dos)) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ObjectUtils.isNotNull(bos)) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解压缩Cookie
	 * 
	 * @param cookie
	 *            Cookie
	 * @throws IOException
	 */
	public static final void unCompressCookie(Cookie cookie) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if (StringUtils.isEmpty(cookie.getValue())) {
			return;
		}
		byte[] compress = new BASE64Decoder().decodeBuffer(cookie.getValue());
		InflaterInputStream iis = new InflaterInputStream(
				new ByteArrayInputStream(compress));
		try {
			byte[] b = new byte[1024];
			int count;
			while ((count = iis.read(b)) >= 0) {
				bos.write(b, 0, count);
			}
			iis.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ObjectUtils.isNotNull(iis)) {
				try {
					iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ObjectUtils.isNotNull(bos)) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取Parameter
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            键
	 * @return name对应的value
	 */
	public static final String getParameter(HttpServletRequest request,
			String name) {
		if (new NullChecker().add(request).add(name).hasNull()) {
			return null;
		}
		return request.getParameter(name);
	}

	/**
	 * 获取Attribute
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            键
	 * @return name对应的value
	 */
	public static final String getAttribute(HttpServletRequest request,
			String name) {
		if (new NullChecker().add(request).add(name).hasNull()) {
			return null;
		}
		Object attribute = request.getAttribute(name);
		return (null == attribute || !String.class.isInstance(attribute)) ? null
				: attribute.toString();
	}

	/**
	 * 获取Attribute
	 * 
	 * @param session
	 *            HttpServletRequest
	 * @param name
	 *            键
	 * @return name对应的value
	 */
	public static final String getAttribute(HttpSession session, String name) {
		if (new NullChecker().add(session).add(name).hasNull()) {
			return null;
		}
		Object attribute = session.getAttribute(name);
		return (null == attribute || !String.class.isInstance(attribute)) ? null
				: attribute.toString();
	}

}
