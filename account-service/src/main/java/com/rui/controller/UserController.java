package com.rui.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rui.entity.User;
import com.rui.exception.Assert;
import com.rui.exception.ShopException;
import com.rui.form.UserLoginForm;
import com.rui.form.UserRegisterForm;
import com.rui.result.ResponseEnum;
import com.rui.service.UserService;
import com.rui.util.JwtUtil;
import com.rui.util.MD5Util;
import com.rui.util.RegexValidateUtil;
import com.rui.util.ResultVOUtil;
import com.rui.vo.ResultVO;
import com.rui.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    //注册用户
    @PostMapping("/register")
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

    //用户登陆
    //get请求无法传入JSON格式数据，参数中不能使用@RequestBody注解
    @GetMapping("/login")
    public ResultVO login(@Valid UserLoginForm userLoginForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ShopException(ResponseEnum.USER_INFO_NULL.getMsg());
        }

        //根据手机号查询数据库中的加密密码
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", userLoginForm.getMobile());
        User one = this.userService.getOne(queryWrapper);
        if(one == null){
            throw new ShopException(ResponseEnum.USER_MOBILE_NULL.getMsg());
        }
        //判断加密后的输入密码是否和数据库中密码一致
        boolean saltverifyMD5 = MD5Util.getSaltverifyMD5(userLoginForm.getPassword(), one.getPassword());
        if(!saltverifyMD5){
            throw new ShopException(ResponseEnum.PASSWORD_ERROR.getMsg());
        }

        //一致 - 返回数据库中的用户信息
        UserVO userVO = new UserVO();
        userVO.setMobile(one.getMobile());
        userVO.setUserId(one.getUserId());
        userVO.setPassword(one.getPassword());
        userVO.setToken(JwtUtil.createToken(one.getUserId(), one.getMobile()));
        return ResultVOUtil.success(userVO);
    }


    //验证token
    //token是前端默认封装传入的，非用户手动传入的
    @GetMapping("/checkToken/{token}")
    public ResultVO checkToken(@PathVariable("token") String token){
        boolean b = JwtUtil.checkToken(token);
        if(b) return ResultVOUtil.success(null);
        return ResultVOUtil.fail(null);
    }

}

