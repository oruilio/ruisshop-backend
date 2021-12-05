package com.rui.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rui.entity.User;
import com.rui.exception.Assert;
import com.rui.exception.ShopException;
import com.rui.form.UserRegisterForm;
import com.rui.result.ResponseEnum;
import com.rui.service.UserService;
import com.rui.util.MD5Util;
import com.rui.util.RegexValidateUtil;
import com.rui.util.ResultVOUtil;
import com.rui.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Rui
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/register/")
    public ResultVO register(@Valid @RequestBody UserRegisterForm userRegisterForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ShopException(ResponseEnum.USER_INFO_NULL.getMsg());
        }

        //判断手机号格式是否正确
        Assert.isTrue(RegexValidateUtil.checkMobile(userRegisterForm.getMobile()), ResponseEnum.MOBILE_ERROR);

        //判断手机号是否已经注册
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", userRegisterForm.getMobile());
        User one = this.userService.getOne(queryWrapper);
        if(one!=null){
            throw new ShopException(ResponseEnum.USER_MOBILE_EXIST.getMsg());
        }

        //判断校验码是否正确（注册时，会给用户发一个，在redis中存一个）
        //-从redis中获取校验码
        String code = (String) this.redisTemplate.opsForValue().get("uushop-sms-code-" + userRegisterForm.getMobile());
        //-判断和用户输入是否一致
        Assert.equals(code, userRegisterForm.getCode(), ResponseEnum.USER_CODE_ERROR);

        //注册
        User user = new User();
        user.setMobile(userRegisterForm.getMobile());
        user.setPassword(MD5Util.getSaltMD5(userRegisterForm.getPassword()));
        boolean save = this.userService.save(user);
        if(save) return ResultVOUtil.success(null);
        return ResultVOUtil.fail(null);
    }

}

