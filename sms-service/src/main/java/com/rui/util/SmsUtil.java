package com.rui.util;

import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Component
@ConfigurationProperties(prefix = "jdwx")
public class SmsUtil implements InitializingBean {
    private String url;
    private String appkey;
    private String sign;

    public static String Url;
    public static String Appkey;
    public static String Sign;

    @Override
    public void afterPropertiesSet() throws Exception {
        Url = this.url;
        Appkey = this.appkey;
        Sign = this.sign;
    }
}
