package com.rui.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rui.entity.Admin;
import com.rui.exception.ShopException;
import com.rui.form.AdminForm;
import com.rui.result.ResponseEnum;
import com.rui.service.AdminService;
import com.rui.util.JwtUtil;
import com.rui.util.ResultVOUtil;
import com.rui.vo.AdminVO;
import com.rui.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/login")
    public ResultVO login(@Valid AdminForm adminForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ShopException(ResponseEnum.USER_INFO_NULL.getMsg());
        }
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", adminForm.getUsername());
        queryWrapper.eq("password", adminForm.getPassword());
        Admin one = this.adminService.getOne(queryWrapper);
        if(one == null){
            throw new ShopException(ResponseEnum.LOGIN_ERROR.getMsg());
        }
        AdminVO adminVO = new AdminVO();
        BeanUtils.copyProperties(one, adminVO);
        adminVO.setToken(JwtUtil.createToken(one.getAdminId(), one.getUsername()));
        return ResultVOUtil.success(adminVO);
    }
}

