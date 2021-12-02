package com.qinC.msm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils01 implements InitializingBean {

    @Value("${tencent.msg.file.keyid}")
    private String keyid;

    @Value("${tencent.msg.file.keysecret}")
    private String keysecret;

    @Value("${tencent.msg.file.appid}")
    private String appid;

    @Value("${tencent.msg.file.appkey}")
    private String appkey;

    @Value("${tencent.msg.file.signname}")
    private String signname;

    @Value("${tencent.msg.file.templateid}")
    private String templateid;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String APP_ID;
    public static String APP_KEY;
    public static String SIGN_NAME;
    public static String TEMPLATE_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
        APP_ID = appid;
        APP_KEY = appkey;
        SIGN_NAME = signname;
        TEMPLATE_ID = templateid;
    }

}
