package org.example.LiveSystem.service;
import org.example.LiveSystem.dto.FriendsDTO;
import org.example.LiveSystem.dto.UsersInfoDTO;
import org.example.LiveSystem.entity.User;

import java.util.List;

public interface FriendsService {

    /**
     * 查询好友列表
     * @param id
     * @return
     */
    List<FriendsDTO> listFriends(Integer id);


    /**
     * 查询好友信息
     * @param id
     * @return
     */
    UsersInfoDTO friendInfo(Integer id);

    int deleteFriend(Integer uid, Integer fid);
}
