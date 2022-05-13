package com.example.livesystemapp.controller.RequestProcess;

import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.service.UserInfo;
import com.example.livesystemapp.util.SceneUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class AddRequestController implements Initializable {

    @FXML
    private AnchorPane userInfo;

    @FXML
    private Label header;

    @FXML
    private TextField idNum;

    private Boolean isAddFriend;

    public void setIsAddFriend(Boolean isAddFriend) {
        this.isAddFriend = isAddFriend;
        if(!isAddFriend) {
            header.setText("加入班级");
            idNum.setPromptText("请输入班级号");
        }
    }

    public void setIdNum(String idNum) {
        this.idNum.setText(idNum);
        //不为空直接查询
        if(!idNum.isEmpty()) {
            findUser(new ActionEvent());
        }
    }

    @FXML
    void findUser(ActionEvent event) {
        try {
            userInfo.getChildren().clear();
            String ID = idNum.getText().trim();
            if(isAddFriend) {
                if(!ID.matches("1\\d{10}")) {
                    new Exception("非法手机号码！");
                }
                //获取对方信息
                JSONObject jsonObject = new UserInfo().getFriendInfo(ID);
                if(jsonObject == null) {
                    noUser("用户");
                } else {
                    //添加用户信息页面
                    new SceneUtil().tlmFriendInfo(userInfo, "/view/InfoDisplay/UserInfo.fxml",jsonObject);
                }
            } else {
                if(!ID.matches("d{10}")) {
                    new Exception("非法班级号！");
                }
                //获取群信息
                JSONObject jsonObject = new UserInfo().getGroupInfo(ID);
                if(jsonObject == null) {
                    noUser("班级");
                } else {
                    //添群信息页面
                    new SceneUtil().tlmGroupInfo(userInfo, "/view/InfoDisplay/GroupInfoTwo.fxml", jsonObject);
                }
            }
        } catch (Exception e) {
            System.out.println("查找用户班级时出现异常:"+e);
            idNum.setText("");
            idNum.setPromptText("请输入正确的号码!");
            idNum.setStyle("-fx-prompt-text-fill: red;");
        }
    }

    private void noUser(String str) {
        //页面显示  没有找到符合条件的用户
        Label label = new Label("没有找到符合条件的"+str);
        label.setLayoutX(240);
        label.setLayoutY(240);
        userInfo.getChildren().add(label);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}