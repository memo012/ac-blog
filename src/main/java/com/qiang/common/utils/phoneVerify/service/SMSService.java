package com.qiang.common.utils.phoneVerify.service;

import com.github.qcloudsms.*;
import com.github.qcloudsms.httpclient.HTTPException;
import com.qiang.common.utils.Constant;
import com.qiang.common.utils.RedisOperator;
import com.qiang.common.utils.phoneVerify.component.PhoneRandomBuilder;
import com.qiang.common.utils.phoneVerify.util.SMSUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.service
 * @Description: 腾讯短信服务类
 * @Date: 2019/8/4 0004 11:02
 **/
@Service
public class SMSService {

    @Autowired
    private RedisOperator redisOperator;
    /**
     * 自定义短信内容发送
     * @param msg 短信内容
     * @param number 用户手机号
     * @return OK 成功  null 失败
     */
    public String sendMess(String msg, String number){
        try {
            SmsSingleSender ssender = new SmsSingleSender(SMSUtil.APPID, SMSUtil.APPKEY);
            SmsSingleSenderResult result = ssender.send(0, "86", number,
                    msg, "", "");
            System.out.print(result);
            return result.errMsg;
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 指定模板ＩＤ发送短信
     * @param number 用户手机号
     * @param mark 0 -- 注册验证   1 -- 修改密码验证
     * @return OK 成功  null 失败
     */
    public String sendMesModel(String number, Integer mark){
        try {
            // 验证码随机数
            String code = PhoneRandomBuilder.randomBuilder();
            // 把验证码存入缓存中
            redisOperator.set(Constant.USER_PHONE_CODE + number, code);
            // 设置过期时间（默认以秒为单位）
            redisOperator.expire(Constant.USER_PHONE_CODE + number, 300);

            String[] params = {code};
            SmsSingleSender ssender = new SmsSingleSender(SMSUtil.APPID, SMSUtil.APPKEY);
            if(mark == 0){
                SmsSingleSenderResult result = ssender.sendWithParam("86", number,
                        SMSUtil.SHORTNOTID, params, "", "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
                System.out.print(result);
                return result.errMsg;//OK
            }else if(mark == 1){
                SmsSingleSenderResult result = ssender.sendWithParam("86", number,
                        SMSUtil.UPDPWDID, params, "", "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
                System.out.print(result);
                return result.errMsg;//OK
            }
        }catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 群发自定义短信
     * @param msg 短信内容
     * @param numbers 用户手机号数组
     * @return OK 成功 null 失败
     */
    public String sendMesModel(String msg, String[] numbers){
        try {
            SmsMultiSender msender = new SmsMultiSender(SMSUtil.APPID, SMSUtil.APPKEY);
            SmsMultiSenderResult result =  msender.send(0, "86", numbers,
                    msg, "", "");
            System.out.print(result);
            return result.errMsg;
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 指定模板ID群发
     * @param numbers 用户手机号数组
     * @return OK 成功 null 失败
     */
    public String sendMesModel(String[] numbers){
        try {
            String[] params = {"hello" , "1" };
            SmsMultiSender msender = new SmsMultiSender(SMSUtil.APPID, SMSUtil.APPKEY);
            SmsMultiSenderResult result =  msender.sendWithParam("86", numbers,
                    SMSUtil.SHORTNOTID, params, SMSUtil.NOTESIGN, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.print(result);
            return result.errMsg;
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 发送语音消息
     * @param number 用户手机号
     * @param msg 语音消息内容
     * @return OK 成功 null 失败
     */
    public String sendMesVoice(String msg, String number){
        try {
            SmsVoiceVerifyCodeSender vvcsender = new SmsVoiceVerifyCodeSender(SMSUtil.APPID, SMSUtil.APPKEY);
            SmsVoiceVerifyCodeSenderResult result = vvcsender.send("86",number,
                    msg, 2, "");
            System.out.print(result);
            return result.errMsg;
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return null;
    }

}

