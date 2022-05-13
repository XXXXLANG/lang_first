package org.example.LiveSystem.service.impl;

import org.example.LiveSystem.dao.ChatHistoryDao;
import org.example.LiveSystem.dao.UserGroupsDao;
import org.example.LiveSystem.dao.UserGroupstouserDao;
import org.example.LiveSystem.dto.GroupsDTO;
import org.example.LiveSystem.dto.GroupsToUserDTO;
import org.example.LiveSystem.dto.UserGroupstouserDTO;
import org.example.LiveSystem.dto.UsersInfoDTO;
import org.example.LiveSystem.entity.User;
import org.example.LiveSystem.entity.UserGroups;
import org.example.LiveSystem.entity.UserGroupstouser;
import org.example.LiveSystem.service.GroupsService;
import org.example.LiveSystem.util.EstBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class GroupsServiceImpl implements GroupsService {
    @Autowired
    private UserGroupstouserDao userGroupstouserDao;
    @Autowired
    private UserGroupsDao userGroupsDao;

    /**
     * 查询群组列表
     * @param id
     * @return
     */
    @Override
    public List<GroupsToUserDTO> listGroups(Integer id) {
        List<UserGroupstouser> groupsToUsers =
                userGroupstouserDao.listGroupsBy(id);
        return EstBeanUtil.convertTo(groupsToUsers, GroupsToUserDTO.class);
    }

    /**
     * 获取群组信息列表
     * @param id
     * @return
     */
    @Override
    public GroupsDTO GroupsInfo(Integer id) {
        UserGroups userGroup = userGroupsDao.selectByPrimaryKey(id);
        return EstBeanUtil.convertTo(userGroup, GroupsDTO.class);
    }

    /**
     * 获取群成员
     * @param gid
     * @return
     */
    @Override
    public List<UserGroupstouserDTO> listGroupMember(Integer gid) {
        List<UserGroupstouser> userGroupstousers = userGroupstouserDao.listUsersByGid(gid);
        return EstBeanUtil.convertTo(userGroupstousers, UserGroupstouserDTO.class);
    }

    @Override
    public GroupsDTO findByNum(String GroupNum) {
        UserGroups userGroups = userGroupsDao.findByNum(GroupNum);
        return EstBeanUtil.convertTo(userGroups, GroupsDTO.class);
    }

    @Override
    public Integer addGroup(String groupName, Integer uid) {
        UserGroups userGroups = new UserGroups();
        userGroups.setUgName(groupName);
        userGroups.setUgAdminid(uid);
        userGroups.setUgCreatetime(new Date());
        userGroups.setUgVerification(1);
        //自动生成9位班级账号
        String randNum = null;
        while (randNum == null) {
            randNum = String.format("%010d", Math.abs(new Random().nextInt()));;
            if(userGroupsDao.findByNum(randNum) != null) {
                randNum = null;
            }
        }
        userGroups.setUgGroupNum(randNum);
        userGroupsDao.insert(userGroups);
        Integer ugId = userGroups.getUgId();
        //添加群成员信息，群主也是群成员
        UserGroupstouser userGroupstouser = new UserGroupstouser();
        userGroupstouser.setUgtUserid(uid);
        userGroupstouser.setUgtGroupid(ugId);
        userGroupstouser.setUgtReminding(0);
        userGroupstouserDao.insert(userGroupstouser);
        return ugId;
    }

    @Override
    public int deleteGroup(Integer uId, Integer gid) {
        UserGroups userGroups = userGroupsDao.selectByPrimaryKey(gid);
        //群主退出群聊，群主自动易位，不直接解散群聊
        if(userGroups.getUgAdminid() == uId) {
            Integer nextAdmin = userGroupstouserDao.selectNextAdmin(gid, uId);
            if(nextAdmin == null) {
                userGroupsDao.deleteByPrimaryKey(gid);
            } else {
                userGroupsDao.updateAdmin(gid, nextAdmin);
            }
        }
        return userGroupstouserDao.deleteGroup(uId, gid);
    }
}
