package com.rui.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rui.exception.ShopException;
import com.rui.result.ResponseEnum;
import com.rui.service.SmsService;
import com.rui.util.SmsUtil;
import com.wxapi.WxApiCall.WxApiCall;
import com.wxapi.model.RequestModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//以下代码由第三方服务提供
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public boolean send(String mobile, String code) {
        RequestModel model = new RequestModel();
        model.setGwUrl(SmsUtil.Url);
        model.setAppkey(SmsUtil.Appkey);
        Map queryMap = new HashMap();
        queryMap.put("sign",SmsUtil.Sign); //访问参数
        queryMap.put("mobile",mobile); //访问参数
        queryMap.put("content","您本次的验证码是："+code); //访问参数
        model.setQueryParams(queryMap);
        try {
            WxApiCall call = new WxApiCall();
            call.setModel(model);
            call.request();
            String request = call.request();
            Gson gson = new Gson();
            Map<String,String> map = gson.fromJson(request,
                    new TypeToken<Map<String,String>>(){}.getType());
            System.out.println(map);
            if(map.get("code").equals("10010"))return true;
        } catch (JsonSyntaxException e) {
            throw new ShopException(ResponseEnum.SMS_SEND_ERROR.getMsg());
        }
        return false;
    }
}
