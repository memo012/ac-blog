package com.qiang.common.utils;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Author: qiang
 * @Describe: 转码工具
 * @Date: 2018/7/22 21:17
 */
@Component
public class TransCodingUtil {

    /**
     * 中文转unicode编码
     *
     * @param gbString 汉字
     * @return unicode编码
     */
    public static String stringToUnicode(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }


    /**
     * unicode编码转中文
     *
     * @param dataStr unicode编码
     * @return 中文
     */
    public static String unicodeToString(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            // 16进制parse整形字符串。
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }


    /**
     * 将utf-8展开的16进制数转换成utf-8汉字
     *
     * @param strUtf16
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String utf16ToUtf8(String strUtf16) throws UnsupportedEncodingException {
        String strUtf8 = URLDecoder.decode(strUtf16, "UTF-8");
        return strUtf8;
    }


    /**
     * 判断是否为汉字
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            int char1 = str.charAt(i);
            //汉字范围
            if (char1 >= 19968 && char1 <= 171941) {
                return true;
            }
        }
        return false;
    }

}
