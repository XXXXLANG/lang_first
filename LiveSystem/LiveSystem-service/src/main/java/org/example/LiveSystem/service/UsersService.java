package org.example.LiveSystem.service;

import org.example.LiveSystem.dto.TokenDTO;
import org.example.LiveSystem.dto.UsersInfoDTO;
import org.example.LiveSystem.entity.User;
import org.example.LiveSystem.param.PwLoginParam;
import org.example.LiveSystem.param.SmsLoginParam;

/**
 * 用户的业务接口
 */
public interface UsersService {

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    User findById(int id);

    /**
     * 短信验证码登录
     *
     * @param loginParam
     * @return
     */
    TokenDTO smsLogin(SmsLoginParam loginParam);

    /**
     * 账号密码验证码登录
     *
     * @param loginParam
     * @return
     */
    TokenDTO pwLogin(PwLoginParam loginParam);

    /**
     * 注册用户
     *
     * @param users
     * @return
     */
    User registe(User users);

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    UsersInfoDTO getUserInfoByToken(String token);

    /**
     * 根据手机号查用户
     *
     * @param tel
     * @return
     */
    UsersInfoDTO getUserInfoByTel(String tel);

    /**
     * 退出登录
     *
     * @param token
     */
    void logout(String token);

    /**
     * 把数据的外键，连接其他表换成实际的数据
     *
     * @param user
     * @return
     */
    UsersInfoDTO userInfo(User user);
}
