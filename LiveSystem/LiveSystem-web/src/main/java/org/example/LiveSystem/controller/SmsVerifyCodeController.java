package org.example.LiveSystem.controller;

import org.example.LiveSystem.common.util.ResultUtil;
import org.example.LiveSystem.dto.Result;
import org.example.LiveSystem.service.SmsVerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SmsVerifyCodeController {

    @Autowired
    private SmsVerifyCodeService smsVerifyCodeService;

    @PostMapping("/api/sms/verifyCode")
    public Result<Void> sendVerifyCode(@RequestParam String phone,
                                       @RequestParam Integer codeType) throws Exception {
        // TODO 验证 phone 格式是否正确
        // TODO codeType的值是否符合要求

        smsVerifyCodeService.sendVerifyCode(phone,codeType);

        return ResultUtil.success();
    }
}
