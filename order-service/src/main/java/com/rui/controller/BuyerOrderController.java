package com.rui.controller;

import com.rui.exception.ShopException;
import com.rui.form.OrderForm;
import com.rui.result.ResponseEnum;
import com.rui.service.OrderMasterService;
import com.rui.util.ResultVOUtil;
import com.rui.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2021-12-03
 */
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    //创建订单
    //创建一个实体类Form对应从前端传到后端的数据
    @PostMapping("/create")
    public ResultVO create(@Valid @RequestBody OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ShopException(ResponseEnum.ORDER_CREATE_ERROR.getMsg());
        }
        String orderId = this.orderMasterService.create(orderForm);
        if(orderId!=null) {
            Map<String,String> map = new HashMap<>();
            map.put("orderId",orderId);
            return ResultVOUtil.success(map);
        }
        return ResultVOUtil.fail(null);
    }
}

