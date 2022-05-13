package org.example.LiveSystem.service.impl;

import org.example.LiveSystem.constants.Constants;
import org.example.LiveSystem.dto.TokenDTO;
import org.example.LiveSystem.entity.User;
import org.example.LiveSystem.service.TokenService;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {
    /**
     * 会话的过期时间,验证码持续时间
     */
    private static final long SESSION_EXPIRE_MINUTES=60;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public TokenDTO generateToken(User User) {
        String token = UUID.fastUUID().toString(true);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        return tokenDTO;
    }

    @Override
    public void saveToken(String token, User User) {
        // key=>  token:fjalkfjalfdaslfkjaf
        String tokenKey= Constants.Redis.PREFIX_TOKEN + token;
        JSONObject jsonObject = new JSONObject(User);
        redisTemplate.opsForValue()
                .set(
                        tokenKey,
                        jsonObject.toString(),
                        SESSION_EXPIRE_MINUTES,
                        TimeUnit.MINUTES
                );
    }

    @Override
    public User getUserInfoByToken(String token) {
        // fastjson alibaba
        String tokenKey= Constants.Redis.PREFIX_TOKEN + token;
        String userInfoJSONString =
                redisTemplate.opsForValue().get(tokenKey);
        if(Objects.isNull(userInfoJSONString)){
            return null;
        }
        User User = JSONUtil.toBean(userInfoJSONString, User.class);
        return User;
    }

    @Override
    public void removeToken(String token) {
        String tokenKey= Constants.Redis.PREFIX_TOKEN + token;
        redisTemplate.delete(tokenKey);
    }
}
