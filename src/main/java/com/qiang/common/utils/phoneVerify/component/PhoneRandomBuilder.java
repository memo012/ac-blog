package com.qiang.common.utils.phoneVerify.component;

import org.springframework.stereotype.Component;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.component
 * @Description: 手机验证码随机生成
 * @Date: 2019/8/4 0004 11:19
 **/
@Component
public class PhoneRandomBuilder {

    public static String randomBuilder(){
        String result = "";
        for(int i=0;i<4;i++){
            result += Math.round(Math.random() * 9);
        }

        return result;

    }

}
