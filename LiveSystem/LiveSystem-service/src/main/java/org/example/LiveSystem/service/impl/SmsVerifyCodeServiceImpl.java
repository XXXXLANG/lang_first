package org.example.LiveSystem.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import org.example.LiveSystem.constants.Constants;
import org.example.LiveSystem.service.SmsVerifyCodeService;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;

/**
 * 验证码业务组件
 *
 * @author jun
 * @date 2021/11/8
 */
@Service
public class SmsVerifyCodeServiceImpl implements SmsVerifyCodeService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void sendVerifyCode(String phone, Integer codeType) throws Exception {
        // 根据不同的验证码类型来发送不同的验证码
        if(Constants.Sms.TYPE_REGIST_OR_LOGIN.equals(codeType)){
            // 登录注册的验证码
            sendLoginVerifyCode(phone);
        }else if(Constants.Sms.TYPE_UP_PASS.equals(codeType)){
            // 修改密码的短信验证码
            sendUpdatePasswordVerifyCode(phone);
        }else if(Constants.Sms.TYPE_SUBMIT_CHECK.equals(codeType)){
            // 提交订单时的验证码
            sendSubmitOrderVerifyCode(phone);
        }
    }

    @Override
    public String getVerifyCode(String phone) {
        String smsRedisKey = getSmsRedisKey(phone);
        String code = stringRedisTemplate.opsForValue().get(smsRedisKey);
        return code;
    }

    @Override
    public void removeVerifyCode(String phone) {
        String smsRedisKey = getSmsRedisKey(phone);
        stringRedisTemplate.delete(smsRedisKey);
    }

    private void sendSubmitOrderVerifyCode(String phone) {

    }

    private void sendUpdatePasswordVerifyCode(String phone) {

    }

    private void sendLoginVerifyCode(String phone) throws Exception {
        // 先从redis中获取验证码，如果能获取到，直接返回（验证码在有效期内）
        String smsRedisKey = getSmsRedisKey(phone);
        String code = stringRedisTemplate.opsForValue()
                .get(smsRedisKey);
        if(!Objects.isNull(code)){
            return;
        }
        // 生成一个6位随机数字验证码
        code = RandomUtil.randomNumbers(6);
        // 发送给指定的手机号
        String signName = "在线教室直播互动系统";
        String templateCode = "1";
        //sendSms(phone,signName,code,templateParam);
        // 保存到redis
        stringRedisTemplate.opsForValue()
                .set(smsRedisKey,code,5, TimeUnit.MINUTES);
    }

    private String getSmsRedisKey(String phone) {
        return Constants.Redis.PREFIX_SMS + phone;
    }

    private SendSMSResult sendSms(String phone, String signName, String templateCode, String code) throws Exception {
        String masterSecret = "";
        String appKey = "";
        // 调用极光sdk
        SMSClient client = new SMSClient(masterSecret, appKey);
        SMSPayload payload = SMSPayload.newBuilder()
                .setMobileNumber(phone)
                .setTempId(Integer.parseInt(templateCode))
                .addTempPara("username",signName)
                .addTempPara("context",code)
                .build();
        SendSMSResult res = client.sendSMSCode(payload);
        return res;
    }

}
