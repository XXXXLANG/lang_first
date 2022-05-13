package com.example.livesystemapp.controller.InfoDispaly;

import com.example.livesystemapp.controller.ChatPane.ChatViewController;
import com.example.livesystemapp.util.HttpRequest.PostRequest;
import com.example.livesystemapp.util.Param.GlobalParam;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.Data;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.awt.*;
import java.io.IOException;

@FXMLController
public class FriendInfoController {

    /**
     * 账号
     */
    @FXML
    private Label loginID;

    /**
     * 联系人自定义的网名
     */
    @FXML
    private Label nickName;

    /**
     * 个性签名
     */
    @FXML
    private Label signaTure;

    /**
     * 备注的昵称
     */
    @FXML
    private Label alias;

    /**
     * 地区
     */
    @FXML
    private Label email;

    /**
     * 头像
     */
    @FXML
    private ImageView headPortrait;

    private Integer fid;

    public void setFid(Integer fid) {
        this.fid = fid;
    }
    public void setHeadPortrait(ImageView headPortrait) { this.headPortrait = headPortrait; }
    public void setLoginID(String loginID) {
        this.loginID.setText(loginID);
    }
    public void setNickName(String nickName) {
        this.nickName.setText(nickName);
    }
    public void setSignaTure(String signaTure) {
        this.signaTure.setText(signaTure);
    }
    public void setEmail(String region) {
        this.email.setText(region);
    }
    public void setAlias(String alias) {
        this.alias.setText(alias);
    }

    @FXML
    void sendCharAction(MouseEvent event) throws IOException {
        ChatViewController chatViewController = GlobalParam.controllerForFriend.get(fid);
        if(chatViewController == null) {
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("isGroup", false);
            paramMap.add("fid", fid);
            new PostRequest().postReObject(paramMap, "Msg/addChat");
        }
        GlobalParam.homeController.liveAction(event);
        chatViewController = GlobalParam.controllerForFriend.get(fid);
        chatViewController.ChatAction(event);
    }
}
