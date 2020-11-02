package com.qiang.common.utils.phoneVerify.util;

import org.springframework.stereotype.Component;

/**
 * @Author: zuiguangyin
 * @Despcription:
 * @Date: Created in 2020/11/2 23:45
 * @Modified by:
 */
@Component
public class SMSUtil {

    /**
     * 短信应用SDK AppID
     */

    public static final Integer APPID = 1400247771;

    /**
     * 短信应用SDK AppKEY
     */
    public static final String APPKEY = "";

    /**
     * 短信模板id（注册）AppKEY
     */
    public static final Integer SHORTNOTID = 3878379;

    /**
     * 短信模板id （密码修改）
     */
    public static final Integer UPDPWDID = 3885707;

    /**
     * 短信签名
     */
    public static final String NOTESIGN = "强子博客";

}