package org.example.LiveSystem.controller;

import org.example.LiveSystem.annotations.TokenUser;
import org.example.LiveSystem.common.BaseController;
import org.example.LiveSystem.common.util.ResultUtil;
import org.example.LiveSystem.dao.UserDao;
import org.example.LiveSystem.dao.UserGroupstouserDao;
import org.example.LiveSystem.dto.*;
import org.example.LiveSystem.entity.User;
import org.example.LiveSystem.entity.UserGroupstouser;
import org.example.LiveSystem.service.FriendsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.LiveSystem.service.GroupsService;
import org.example.LiveSystem.service.MsgService;
import org.example.LiveSystem.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "主页API")
@RequestMapping("/api/home")
@RestController
public class HomeController extends BaseController{

    @Autowired
    private FriendsService friendsService;
    @Autowired
    private GroupsService groupsService;
    @Autowired
    private MsgService msgService;
    @Autowired
    private UserGroupstouserDao userGroupstouserDao;

    @ApiOperation(value = "获取好友列表", notes = "获取所有好友")
    @GetMapping("/friendsList")
    public Result<List<FriendsDTO>> getFriendsList(@TokenUser User user){
        List<FriendsDTO> friendsDTOS = friendsService.listFriends(user.getUId());
        for(FriendsDTO friendsDTO : friendsDTOS) {
            friendsDTO.setFriendInfo(friendsService.friendInfo(friendsDTO.getFFriendid()));
        }
        return ResultUtil.success(friendsDTOS);
    }

    @ApiOperation(value = "获取群列表", notes = "获取所有加入的群聊")
    @GetMapping("/groupsList")
    public Result<List<GroupsToUserDTO>> getGroupsList(@TokenUser User user){
        List<GroupsToUserDTO> groupsDTOS = groupsService.listGroups(user.getUId());
        for(GroupsToUserDTO groupsToUserDTO : groupsDTOS) {
            groupsToUserDTO.setGroupsInfo(groupsService.GroupsInfo(groupsToUserDTO.getUgtGroupid()));
        }
        return ResultUtil.success(groupsDTOS);
    }

    @ApiOperation(value = "删除好友")
    @PostMapping("/deleteFriend")
    public Result<Void> deleteFriend(@TokenUser User user,
                                     @RequestParam(value = "fid") Integer fid) {
        if(friendsService.deleteFriend(user.getUId(), fid) > 0) {
            msgService.delChatByUFID(user.getUId(), fid);
            return ResultUtil.success();
        } else {
            return ResultUtil.fail();
        }
    }

    @ApiOperation(value = "删除群聊")
    @PostMapping("/deleteGroup")
    public Result<Void> deleteGroup(@TokenUser User user,
                                    @RequestParam(value = "gid") Integer gid) {
        if(groupsService.deleteGroup(user.getUId(), gid) > 0) {
            msgService.delChatByUGID(user.getUId(), gid);
            return ResultUtil.success();
        } else {
            return ResultUtil.fail();
        }
    }

    @ApiOperation(value = "更改群消息提醒")
    @PostMapping("/changeGroupRemind")
    public Result<Void> changeGroupRemind(@TokenUser User user,
                                          @RequestParam(value = "gid") Integer gid) {
        UserGroupstouser userGroupstouser = userGroupstouserDao.findByUidGid(user.getUId(), gid);
        if(userGroupstouser == null) {
            return ResultUtil.fail();
        }
        if(userGroupstouser.getUgtReminding() == 1) {
            userGroupstouser.setUgtReminding(0);
        } else {
            userGroupstouser.setUgtReminding(1);
        }
        if(userGroupstouserDao.updateByPrimaryKeySelective(userGroupstouser) > 0) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail();
        }
    }
}
