package org.example.LiveSystem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.example.LiveSystem.annotations.TokenUser;
import org.example.LiveSystem.common.util.ResultUtil;
import org.example.LiveSystem.dao.*;
import org.example.LiveSystem.dto.ChatHistoryDTO;
import org.example.LiveSystem.dto.Result;
import org.example.LiveSystem.dto.UserGroupsmsgcontentDTO;
import org.example.LiveSystem.dto.UserMsgusertouserDTO;
import org.example.LiveSystem.entity.ChatHistory;
import org.example.LiveSystem.entity.User;
import org.example.LiveSystem.entity.UserGroups;
import org.example.LiveSystem.entity.UserGroupstouser;
import org.example.LiveSystem.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "获取信息API")
@RequestMapping("/api/Msg")
@RestController
@Validated
public class MsgController {

    @Autowired
    private MsgService msgService;
    @Autowired
    private ChatHistoryDao chatHistoryDao;
    @Autowired
    private FriendsDao friendsDao;
    @Autowired
    private UserMsgusertouserDao userMsgusertouserDao;
    @Autowired
    private UserGroupsDao userGroupsDao;
    @Autowired
    private UserGroupsmsgcontentDao userGroupsmsgcontentDao;
    @Autowired
    private UserGroupstouserDao userGroupstouserDao;
    @Autowired
    private UserDao userDao;

    @ApiOperation(value = "获取两人的私聊信息", notes = "通过两人的ID获取聊天信息")
    @GetMapping("/getFriendMsg")
    public Result<List<UserMsgusertouserDTO>> getFriendMsg(@TokenUser User user,
                                                           @NotNull(message = "好友ID不能为空") Integer FriendID,
                                                           Integer lastMID) {
        List<UserMsgusertouserDTO> FriendMsgDTOs = msgService.getFriendMsg(user.getUId(), FriendID, lastMID);
        return ResultUtil.success(FriendMsgDTOs);
    }

    @ApiOperation(value = "获取群聊信息", notes = "通过群ID获取群内聊天信息")
    @GetMapping("/getGroupMsg")
    public Result<List<UserGroupsmsgcontentDTO>> getGroupMsg(@NotNull(message = "群ID不能为空") Integer GroupID,
                                                             Integer lastGMID) {
        List<UserGroupsmsgcontentDTO> GroupMsgs = msgService.getGroupMsg(GroupID, lastGMID);
        for(UserGroupsmsgcontentDTO GroupMsg : GroupMsgs) {
            String GroupNick = userGroupstouserDao.getUserName(GroupMsg.getGmFromid(), GroupMsg.getGmId());
            if(GroupNick != null && GroupNick.length() > 0) {
                GroupMsg.setGroupNick(GroupNick);
            } else {
                GroupMsg.setGroupNick(userDao.getUserName(GroupMsg.getGmFromid()));
            }
        }
        return ResultUtil.success(GroupMsgs);
    }

    @ApiOperation(value = "获取聊天记录", notes = "通过用户ID获取个人的聊天记录列表")
    @GetMapping("/getChatList")
    public Result<List<ChatHistoryDTO>> getChatList(@TokenUser User user) {
        List<ChatHistoryDTO> chatHistoryDTOS = msgService.getChatList(user.getUId());
        for(ChatHistoryDTO chatHistoryDTO : chatHistoryDTOS) {
            Integer UserID = chatHistoryDTO.getChUserid();
            Integer FriendID = chatHistoryDTO.getChFriendid();
            if(FriendID != null) {
                String FriendName = friendsDao.getFriendName(FriendID, UserID);
                if(FriendName != null && FriendName.length() > 0) {
                    chatHistoryDTO.setFriendName(FriendName);
                } else {
                    FriendName = userDao.getUserName(FriendID);
                    chatHistoryDTO.setFriendName(FriendName);
                }
                chatHistoryDTO.setMsg(userMsgusertouserDao.getLastMsg(FriendID, UserID));
            } else {
                FriendID = chatHistoryDTO.getChGroupid();
                chatHistoryDTO.setFriendName(userGroupsDao.getGroupName(FriendID));
                chatHistoryDTO.setMsg(userGroupsmsgcontentDao.getLastMsg(FriendID));
            }
        }
        return ResultUtil.success(chatHistoryDTOS);
    }

    @ApiOperation(value = "删除聊天", notes = "通过CH_ID删除聊天，保留聊天信息")
    @PostMapping("/deleteChat")
    public Result<Void> deleteChat(@RequestParam(value = "chId") Integer chId) {
        if(msgService.deleteChat(chId) > 0) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail();
        }
    }

    @ApiOperation(value = "新增聊天", notes = "新建一个新的聊天")
    @PostMapping("/addChat")
    public Result<Void> addChat(@TokenUser User user,
                                @RequestParam(value = "isGroup") Boolean isGroup,
                                @RequestParam(value = "fid") Integer fid) {

        if(msgService.addChat(user.getUId(), fid, isGroup) > 0) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail();
        }
    }

    @ApiOperation(value = "更改聊天置顶", notes = "如果聊天已经置顶就改为不置顶")
    @PostMapping("/changeChatTop")
    public Result<Void> changeChatTop(@RequestParam(value = "chId") Integer chId) {
        ChatHistory chatHistory = chatHistoryDao.selectByPrimaryKey(chId);
        if(chatHistory == null) {
            return ResultUtil.fail();
        }
        if(chatHistory.getChTop() == 1) {
            chatHistory.setChTop(0);
        } else  {
            chatHistory.setChTop(1);
        }
        if(chatHistoryDao.updateByPrimaryKeySelective(chatHistory) > 0) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail();
        }
    }
}
