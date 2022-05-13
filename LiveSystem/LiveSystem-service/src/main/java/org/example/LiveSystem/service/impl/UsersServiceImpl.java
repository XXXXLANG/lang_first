package org.example.LiveSystem.service.impl;

import org.example.LiveSystem.common.exception.BusinessException;
import org.example.LiveSystem.constants.Constants;
import org.example.LiveSystem.constants.ResultEnum;
import org.example.LiveSystem.dao.*;
import org.example.LiveSystem.dto.TokenDTO;
import org.example.LiveSystem.dto.UsersInfoDTO;
import org.example.LiveSystem.entity.User;
import org.example.LiveSystem.param.PwLoginParam;
import org.example.LiveSystem.param.SmsLoginParam;
import org.example.LiveSystem.service.SmsVerifyCodeService;
import org.example.LiveSystem.service.TokenService;
import org.example.LiveSystem.service.UsersService;
import cn.hutool.core.util.StrUtil;
import org.example.LiveSystem.util.EstBeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class UsersServiceImpl implements UsersService {

    @Resource
    private UserDao UserDao;
    @Autowired
    private SmsVerifyCodeService verifyCodeService;
    @Autowired
    private TokenService tokenService;

    @Resource
    private SchoolDao schoolDao;
    @Resource
    private PositionDao positionDao;
    @Resource
    private NationDao nationDao;
    @Resource
    private ProvinceDao provinceDao;
    @Resource
    private CityDao cityDao;

    @Override
    public User findById(int id) {
        return UserDao.selectByPrimaryKey(id);
    }

    //微信登录openid
//    @Override
//    public User findByOpenId(String openid) {
//        return UserDao.findByOpenId(openid);
//    }

    @Override
    public User registe(User User) {
        UserDao.insertSelective(User);
        return User;
    }

    @Override
    public TokenDTO smsLogin(SmsLoginParam loginParam) {
        // 根据手机号获取缓存的验证码
        String phone = loginParam.getPhone();
        String verifyCode = verifyCodeService.getVerifyCode(phone);
        // 对比验证码是否正确
        // 如果不正确，抛出业务异常： 验证码不正确
        if (!StrUtil.equals(verifyCode,loginParam.getSms())) {
            throw new BusinessException(ResultEnum.FAIL_VERIFY);
        }
        // 如果验证码正确，就清除验证码
        verifyCodeService.removeVerifyCode(phone);
        // 根据手机号查询用户信息
        User User = UserDao.findByTel(phone);
        // 如果用户信息不存在，就进行自动注册
        if(ObjectUtils.isEmpty(User)){
            User = registerByPhone(phone);
        }
        // 自动登录
        TokenDTO tokenDTO = tokenService.generateToken(User);
        tokenService.saveToken(tokenDTO.getToken(),User);
        // 返回结果
        return tokenDTO;
    }

    @Override
    public TokenDTO pwLogin(PwLoginParam loginParam) {
        String LoginID = loginParam.getLoginID();
        String password = loginParam.getPassword();
        //根据账号，密码查询用户
        //如果没有该用户，抛出业务异常： 账号或密码不正确
        User user = UserDao.findByUidPwd(LoginID, password);
        if(ObjectUtils.isEmpty(user)){
            throw new BusinessException(ResultEnum.FAIL_UidPwd);
        }
        // 自动登录
        TokenDTO tokenDTO = tokenService.generateToken(user);
        tokenService.saveToken(tokenDTO.getToken(),user);
        // 返回结果
        return tokenDTO;
    }

    @Override
    public UsersInfoDTO getUserInfoByToken(String token) {
        User User = tokenService.getUserInfoByToken(token);

        UsersInfoDTO infoDTO = new UsersInfoDTO();
        if (User != null) {
            BeanUtils.copyProperties(User,infoDTO);
        }

        return infoDTO;
    }

    @Override
    public UsersInfoDTO getUserInfoByTel(String tel) {
        User user = UserDao.findByTel(tel);

        UsersInfoDTO infoDTO = new UsersInfoDTO();
        BeanUtils.copyProperties(user,infoDTO);

        return infoDTO;
    }

    @Override
    public void logout(String token) {
        tokenService.removeToken(token);
    }

    @Override
    public UsersInfoDTO userInfo(User friend) {
        UsersInfoDTO usersInfoDTO = EstBeanUtil.convertTo(friend, UsersInfoDTO.class);
        Integer id = usersInfoDTO.getUSchooltag();
        if(id != null) {
            usersInfoDTO.setSchool(schoolDao.selectByPrimaryKey(id).getSName());
        }
        id = usersInfoDTO.getUPosition();
        if(id != null) {
            usersInfoDTO.setPosition(positionDao.selectByPrimaryKey(id).getSpName());
        }
        id = usersInfoDTO.getUNationid();
        if(id != null) {
            usersInfoDTO.setNation(nationDao.selectByPrimaryKey(id).getNName());
        }
        id = usersInfoDTO.getUProvinceid();
        if(id != null) {
            usersInfoDTO.setProvince(provinceDao.selectByPrimaryKey(id).getPName());
        }
        id = usersInfoDTO.getUCityid();
        if(id != null) {
            usersInfoDTO.setCity(cityDao.selectByPrimaryKey(id).getcName());
        }
        return usersInfoDTO;
    }

    private User registerByPhone(String phone) {
        User User = new User();
        User.setULoginid(phone);
        User.setUNickname(phone);
        User.setUPassword(phone+String.valueOf(new Date().getTime()).substring(0,10));
        User.setUTelephone(phone);

        UserDao.insert(User);
        return User;
    }
}
