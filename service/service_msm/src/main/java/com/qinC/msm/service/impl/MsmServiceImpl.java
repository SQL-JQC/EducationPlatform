package com.qinC.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qinC.msm.service.MsmService;
import com.qinC.msm.utils.ConstantPropertiesUtils;
//import com.qinC.msm.utils.ConstantPropertiesUtils01;
//import com.tencentcloudapi.common.Credential;
//import com.tencentcloudapi.common.exception.TencentCloudSDKException;
//import com.tencentcloudapi.common.profile.ClientProfile;
//import com.tencentcloudapi.common.profile.HttpProfile;
//import com.tencentcloudapi.sms.v20190711.SmsClient;
//import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
//import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean send(String code, Map<String, Object> param, String phone) {
//        try {
//            if (StringUtils.isEmpty(phone)) {
//                return false;
//            }
//
//            Credential cred = new Credential(ConstantPropertiesUtils01.ACCESS_KEY_ID, ConstantPropertiesUtils01.ACCESS_KEY_SECRET);
//            // 实例化一个http选项，可选，没有特殊需求可以跳过
//            HttpProfile httpProfile = new HttpProfile();
//            httpProfile.setReqMethod("POST");
//            httpProfile.setConnTimeout(60);
//            httpProfile.setEndpoint("sms.tencentcloudapi.com");
//
//            ClientProfile clientProfile = new ClientProfile();
//            clientProfile.setHttpProfile(httpProfile);
//
//            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
//
//            SendSmsRequest req = new SendSmsRequest();
//
//            String sdkAppId = ConstantPropertiesUtils01.APP_ID;
//            req.setSmsSdkAppid(sdkAppId);
//
//            String signName = ConstantPropertiesUtils01.SIGN_NAME;
//            req.setSign(signName);
//
//            String templateId = ConstantPropertiesUtils01.TEMPLATE_ID;
//            req.setTemplateID(templateId);
//
//            String[] phoneNumberSet = {"+86" + phone};
//            req.setPhoneNumberSet(phoneNumberSet);
//
//            String[] templateParamSet = {code};
//            req.setTemplateParamSet(templateParamSet);
//
//            SendSmsResponse res = client.SendSms(req);
//
//            return true;
//        } catch (TencentCloudSDKException e) {
//            e.printStackTrace();
//            return false;
//        }

//        if(StringUtils.isEmpty(phone)) {
//            return false;
//        }
//
//        Credential cred = new Credential(ConstantPropertiesUtils01.ACCESS_KEY_ID, ConstantPropertiesUtils01.ACCESS_KEY_SECRET);
//
//        HttpProfile httpProfile = new HttpProfile();
//        httpProfile.setEndpoint("sms.tencentcloudapi.com");
//
//        ClientProfile clientProfile = new ClientProfile();
//        clientProfile.setHttpProfile(httpProfile);
//
//        SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
//
//        SendSmsRequest req = new SendSmsRequest();
//
//        String[] phoneNumberSet1 = {"+86" + phone};//电话
//        String[] templateParamSet1 = {code};//验证码
//        req.set("PhoneNumberSet", phoneNumberSet1);//电话
//        req.set("TemplateParamSet", templateParamSet1);//验证码
//        req.set("TemplateID", "1209788");//模板
//        req.set("SmsSdkAppid", "1400599060");//在添加应用看生成的实际SdkAppid
//        req.set("Sign", "GenerProgram");//签名
//
//        try {
//            SendSmsResponse resp = client.SendSms(req);//发送请求
//            return true;
//        } catch (TencentCloudSDKException e) {
//            e.printStackTrace();
//            return false;
//        }

        if(StringUtils.isEmpty(phone)) {
            return false;
        }

        DefaultProfile profile = DefaultProfile.getProfile("default", ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone); //手机号
        request.putQueryParameter("SignName","我的未央在线教育平台网站"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode","SMS_228017530"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证码数据，转换json数据传递

        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
