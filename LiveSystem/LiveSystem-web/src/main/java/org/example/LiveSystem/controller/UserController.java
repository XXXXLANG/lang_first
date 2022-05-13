package org.example.LiveSystem.controller;

import io.swagger.annotations.Api;
import org.example.LiveSystem.common.BaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.example.LiveSystem.common.util.ResultUtil;
import org.example.LiveSystem.dao.UserGroupsDao;
import org.example.LiveSystem.dto.GroupsDTO;
import org.example.LiveSystem.dto.Result;
import org.example.LiveSystem.dto.TokenDTO;
import org.example.LiveSystem.dto.UsersInfoDTO;
import org.example.LiveSystem.param.PwLoginParam;
import org.example.LiveSystem.param.SmsLoginParam;
import org.example.LiveSystem.service.GroupsService;
import org.example.LiveSystem.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Api(tags = "用户API")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private GroupsService groupsService;

    @ApiOperation(value = "短信验证码登录", notes = "获取验证码之后，通过手机号和验证码进行登录")
    @PostMapping("/api/user/login/sms")
    public Result<TokenDTO> smsLogin(@Valid SmsLoginParam loginParam) {

        TokenDTO tokenDTO = usersService.smsLogin(loginParam);

        return ResultUtil.success(tokenDTO);
    }

    @ApiOperation(value = "账号密码验证码登录", notes = "通过账号和密码进行登录")
    @PostMapping("/api/user/login/password")
    public Result<TokenDTO> pwLogin(@Valid PwLoginParam loginParam) {

        TokenDTO tokenDTO = usersService.pwLogin(loginParam);

        return ResultUtil.success(tokenDTO);
    }

    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "token", value = "会话token", paramType = "header", required = true)}
    )
    @GetMapping("/api/user/info")
    public Result<UsersInfoDTO> getUserInfo() {
        String token = request.getHeader("Token");
        UsersInfoDTO usersInfoDTO = usersService.getUserInfoByToken(token);
        usersInfoDTO.setToken(token);
        return ResultUtil.success(usersInfoDTO);
    }

    @ApiOperation(value = "查询用户信息")
    @PostMapping("/api/user/getFriendInfo")
    public Result<UsersInfoDTO> getFriendInfo(@RequestParam(value = "tel") @Pattern(regexp = "1\\d{10}",message = "手机号格式不正确") String tel) {
        UsersInfoDTO usersInfoDTO = usersService.getUserInfoByTel(tel);
        return ResultUtil.success(usersInfoDTO);
    }

    @ApiOperation(value = "查询群信息")
    @PostMapping("/api/user/getGroupInfo")
    public Result<GroupsDTO> getGroupInfo(@RequestParam(value = "GroupID") @Pattern(regexp = "d{10}",message = "班级号格式不正确") String GroupID) {
        GroupsDTO groupsDTO = groupsService.findByNum(GroupID);
        if(groupsDTO != null) {
            groupsDTO.setAdminName(usersService.findById(groupsDTO.getUgAdminid()).getUNickname());
        }
        return ResultUtil.success(groupsDTO);
    }

    @ApiOperation(value = "用户注销")
    @PostMapping("/api/user/logout")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Token", value = "会话token", paramType = "header", required = true)}
    )
    public Result<Void> logout() {
        String token = request.getHeader("Token");
        usersService.logout(token);
        return ResultUtil.success();
    }
}
