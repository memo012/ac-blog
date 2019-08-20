package com.qiang.modules.sys.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.shiro
 * @Description:shiroMD5加密类
 * @Date: 2019/6/21 0021 13:17
 **/
public class ShiroMD5 {

    public static Object MD5(String username,String password){
        String hashAlgorithName = "MD5";
        int hashIterations = 1024;//加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
        return obj;
    }

    public static void main(String[] args) {
        System.out.println(MD5("15383466854", "123456"));
    }
}
