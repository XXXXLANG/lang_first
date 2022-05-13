package org.example.LiveSystem.service;

import io.swagger.models.auth.In;
import org.example.LiveSystem.dto.GroupsDTO;
import org.example.LiveSystem.dto.GroupsToUserDTO;
import org.example.LiveSystem.dto.UserGroupstouserDTO;
import org.example.LiveSystem.dto.UsersInfoDTO;
import org.example.LiveSystem.entity.UserGroups;

import java.util.List;

public interface GroupsService {

    /**
     * 查询群组列表
     * @param id
     * @return
     */
    List<GroupsToUserDTO> listGroups(Integer id);

    /**
     * 获取群组信息列表
     * @param id
     * @return
     */
    GroupsDTO GroupsInfo(Integer id);

    /**
     * 获取群成员
     * @param gid
     * @return
     */
    List<UserGroupstouserDTO> listGroupMember(Integer gid);

    /**
     * 通过班级账号获取群消息
     * @param GroupNum
     * @return
     */
    GroupsDTO findByNum(String GroupNum);

    /**
     * 新建班级群
     * @param groupName
     * @param uid
     */
    Integer addGroup(String groupName, Integer uid);

    int deleteGroup(Integer uId, Integer gid);
}
