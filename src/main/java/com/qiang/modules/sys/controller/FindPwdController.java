package com.qiang.modules.sys.controller;

import com.qiang.common.utils.BlogJSONResult;
import com.qiang.common.utils.Constant;
import com.qiang.common.utils.RedisOperator;
import com.qiang.common.utils.phoneVerify.service.SMSService;
import com.qiang.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.controller
 * @Description: 忘记密码controller
 * @Date: 2019/8/4 0004 15:52
 **/
@RestController
public class FindPwdController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private SMSService smsService;

    /**
     *  获取验证码(点击按钮) -- 忘记密码
     * @param phone 手机号
     * @return
     */
    @GetMapping("getUpdPwdCode")
    public BlogJSONResult getUpdPwdCode(@RequestParam("phone") String phone){
        String s = smsService.sendMesModel(phone, 1);
        System.out.println(s);
        if(s.equals("OK")){
            return BlogJSONResult.ok();
        }else{
            return BlogJSONResult.errorMsg("获取验证码失败");
        }
    }


    /**
     * 修改密码
     * @param password
     * @return
     */
    @GetMapping("findUsersPwd")
    public BlogJSONResult findUsersPwd(@RequestParam("phone") String phone, @RequestParam("password") String password){
        int i = userService.updUserPwd(phone, password);
        if(i > 0){
            redisOperator.del(Constant.USER_PHONE_CODE+phone);
            return BlogJSONResult.ok();
        }
        return BlogJSONResult.errorMsg("修改失败");
    }

}
