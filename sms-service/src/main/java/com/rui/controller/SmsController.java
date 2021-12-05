package com.rui.controller;

import com.rui.exception.Assert;
import com.rui.result.ResponseEnum;
import com.rui.service.SmsService;
import com.rui.util.RandomUtil;
import com.rui.util.RegexValidateUtil;
import com.rui.util.ResultVOUtil;
import com.rui.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/send/{mobile}")
    public ResultVO send(@PathVariable("mobile") String mobile){
        //验证手机格式
        Assert.notNull(mobile, ResponseEnum.PARAMETER_NULL);
        Assert.isTrue(RegexValidateUtil.checkMobile(mobile), ResponseEnum.MOBILE_ERROR);

        //生成6位随机验证码
        String code = RandomUtil.getSixBitRandom();

        //给指定手机号发送验证码
        boolean send = this.smsService.send(mobile, code);

        if(send){
            //在redis中存验证码
            this.redisTemplate.opsForValue().set("uushop-sms-code-"+mobile, code);
            return ResultVOUtil.success("短信发送成功！");
        }
        return ResultVOUtil.fail(null);
    }
}

