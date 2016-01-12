package com.licong.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Url 编码器,解决URLEncoder行为不符合Url编码规范的问题</br>
 * UrlEncoder遵循application/x-www-form-urlencoded规范进行编码，通常用于表单提交时对表单内容进行编码。
 * 编码规则为：All characters except letters ('a'..'z', 'A'..'Z') and numbers ('0'..'9') and characters '.', '-', '*', '_' are converted into their hexadecimal value prepended by '%'. For example: '#' -> #. In addition, spaces are substituted by '+'
 * 而Url的规范为rfc3986，编码规则为对以下特殊字符用'%HH'替换，HH为对应的十六进制：
 * Reserved
 * Have to be encoded sometimes
 * ! * ' ( ) ; : @ & = + $ , / ? % # [ ]
 * 两者有细微不同之处，导致UrlEncoder编码的结果未必适用于Url。
 * 具体的区别在于：
 * UrlEncoder把' '(空格)转换为'+'，而Url的行为是用十六进制'%20'替代;
 * ' ' -> '+' -> '%20'
 * UrlEncoder对于'*'没有做处理，而Url的行为是用十六进制'%7E'替代;
 * '*' ->'*' -> '%7E'
 * UrlEncoder对于'~'进行了处理，而Url的行为不处理;
 * '~' -> '%7E' -> '~'
 * 因此对UrlEncoder的结果可以做如下处理以保证于rfc3986标准一致
 * URLEncoder.encode(s, ENCODING).replace( "+", "%20" ).replace("*", "%2A").replace( "%7E", "~" );
 * 对于该结果可以直接用URLEncoder.decode进行解码，不会有问题。
 * <p>
 *
 *
 * @author James-li
 *         <p>
 *         2013-2-13
 */
public class MyUrlEncoder {

    private static final String ENCODING = "UTF-8";

    /**
     * 编码
     *
     * @param toEncode 待编码字符串
     * @return 编码结果
     */
    public static String encode(String toEncode) {
        if (StringUtils.isEmpty(toEncode)) {
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
     *
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
     *
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
