package org.example.LiveSystem.service;

import org.example.LiveSystem.dto.TokenDTO;
import org.example.LiveSystem.entity.User;

public interface TokenService {
    /**
     *   根据登录的用户信息自动生成一个token:
     *   (使用UUID的方式自动生成一个)
     */
    TokenDTO generateToken(User users);

    /**
     * 保存token和相关的用户信息到redis中
     */
    void saveToken(String token,User users);

    /**
     * 根据token获取用户的信息
     */
    User getUserInfoByToken(String token);

    /**
     * 清除token相关的会话信息
     */
    void removeToken(String token);

}
