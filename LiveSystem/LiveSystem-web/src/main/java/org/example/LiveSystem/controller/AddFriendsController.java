package org.example.LiveSystem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.LiveSystem.annotations.TokenUser;
import org.example.LiveSystem.common.util.ResultUtil;
import org.example.LiveSystem.dao.UserDao;
import org.example.LiveSystem.dao.UserGroupsDao;
import org.example.LiveSystem.dto.FriendRequestDTO;
import org.example.LiveSystem.dto.Result;
import org.example.LiveSystem.entity.User;
import org.example.LiveSystem.param.FriendRequestParm;
import org.example.LiveSystem.param.RequestProcessionParm;
import org.example.LiveSystem.service.FriendRequestService;
import org.example.LiveSystem.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@Api(tags = "添加好友API")
@RestController
@Validated
public class AddFriendsController {

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserGroupsDao userGroupsDao;

    @Autowired
    private GroupsService groupsService;

    /**
     * 发送添加请求（生成申请表）
     * @param friendRequestParm
     */
    @ApiOperation("发送添加请求")
    @PostMapping("/api/Friend/addFriend/")
    public Result<Void> addFriend(@TokenUser User user,
                                  @Valid FriendRequestParm friendRequestParm) {
        if(friendRequestService.addFriends(friendRequestParm, user.getUId()) > 0) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail();
        }
    }

    /**
     * 申请处理结果
     * @param requestProcessionParm
     */
    @ApiOperation("获取申请处理结果")
    @PostMapping("/api/Friend/Processing/")
    public Result<Void> Processing(@Valid RequestProcessionParm requestProcessionParm) {
        friendRequestService.Processing(requestProcessionParm);
        return ResultUtil.success();
    }

    /**
     * 获取添加申请表（请求对方确认）
     * @param user
     * @return
     */
    @ApiOperation("获取添加申请表")
    @PostMapping("/api/Friend/getProcess/")
    public Result<List<FriendRequestDTO>> getProcess(@TokenUser User user) {
        List<FriendRequestDTO> friendRequestDTOs = friendRequestService.getProcess(user.getUId());
        for(FriendRequestDTO friendRequestDTO : friendRequestDTOs) {
            friendRequestDTO.setFriendName(userDao.getUserName(friendRequestDTO.getFrApplicant()));
            //群申请需要获取群名称
            Integer GroupID = friendRequestDTO.getFrGroup();
            if(GroupID != null && GroupID > 0) {
                friendRequestDTO.setGroupName(userGroupsDao.getGroupName(GroupID));
            }
        }
        return ResultUtil.success(friendRequestDTOs);
    }

    @ApiOperation("新建班级群")
    @PostMapping("/api/Group/addGroup/")
    public Result<Integer> addGroup(@TokenUser User user, @RequestParam("GroupName") String gGroupName) {
        if(user == null) {
            return ResultUtil.fail();
        }
        Integer groupID = groupsService.addGroup(gGroupName, user.getUId());
        if(groupID != null) {
            return ResultUtil.success(groupID);
        } else {
            return ResultUtil.fail();
        }
    }
}
