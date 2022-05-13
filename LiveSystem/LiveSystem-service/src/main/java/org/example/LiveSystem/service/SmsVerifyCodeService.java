package org.example.LiveSystem.service;

public interface SmsVerifyCodeService {
    /**
     * 给指定的手机号发送指定类型的验证码
     * @param phone
     * @param codeType
     */
    void sendVerifyCode(String phone, Integer codeType) throws Exception;

    /**
     * 根据手机号获取已经发送的验证码
     * @param phone
     * @return
     */
    String getVerifyCode(String phone);

    /**
     * 根据手机号清除已经使用过的验证码
     * @param phone
     */
    void removeVerifyCode(String phone);
}
