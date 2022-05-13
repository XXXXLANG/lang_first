package org.example.LiveSystem.service.impl;

import org.example.LiveSystem.dao.FriendsDao;
import org.example.LiveSystem.dao.UserDao;
import org.example.LiveSystem.dto.FriendsDTO;
import org.example.LiveSystem.dto.UsersInfoDTO;
import org.example.LiveSystem.entity.Friends;
import org.example.LiveSystem.entity.User;
import org.example.LiveSystem.service.FriendsService;
import org.example.LiveSystem.service.UsersService;
import org.example.LiveSystem.util.EstBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private FriendsDao friendsDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UsersService usersService;

    /**
     * 查询好友列表
     * @param id
     * @return
     */
    @Override
    public List<FriendsDTO> listFriends(Integer id) {
        List<Friends> friends = friendsDao.listFriendsBy(id);
        return EstBeanUtil.convertTo(friends, FriendsDTO.class);
    }

    /**
     * 获取好友信息
     * @param id
     * @return
     */
    @Override
    public UsersInfoDTO friendInfo(Integer id) {
        User friend = userDao.selectByPrimaryKey(id);
        //群主id需要获取其信息，群成员信息也要获取
        return usersService.userInfo(friend);
    }

    /**
     * 删除好友
     * @param uid
     * @param fid
     * @return
     */
    @Override
    public int deleteFriend(Integer uid, Integer fid) {
        return friendsDao.deleteFriend(uid, fid);
    }
}
