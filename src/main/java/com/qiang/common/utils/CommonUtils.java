package com.qiang.common.utils;

import java.util.Random;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.common.utils
 * @Description: 工具类
 * @Date: 2019/7/31 0031 18:41
 **/
public class CommonUtils {

    /**
     * 获取一个类BASE64编码的随机字符串
     *
     * @param num
     * @return
     */
    public static String getRandomString(int num) {
        Random rd = new Random();
        StringBuilder content = new StringBuilder(num);

        for (int i = 0; i < num; i++) {
            int n;
            while (true) {
                n = rd.nextInt('z' + 1);
                if (n >= '0' && n <= '9')
                    break;
                if (n >= 'a' && n <= 'z')
                    break;
                if (n >= 'A' && n <= 'Z')
                    break;
            }
            content.append((char) n);
        }
        return content.toString();
    }

    /**
     * 获取一个从min到max的随机整数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNumber(int min, int max) {
        Random rd = new Random();
        int ret = rd.nextInt(max);

        while (ret < min)
            ret += rd.nextInt(max - min);

        return ret;
    }

}
