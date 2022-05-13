package com.example.livesystemapp.controller.ChatPane;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.controller.HomePane.HomeController;
import com.example.livesystemapp.service.Messages;
import com.example.livesystemapp.util.Param.GlobalParam;
import com.example.livesystemapp.util.SceneUtil;
import com.example.livesystemapp.util.SecondaryUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class ChatViewController implements Initializable {
    @FXML
    private Label Msg;

    @FXML
    private Label FriendName;

    @FXML
    private Label Sendtime;

    @FXML
    private Image FriendFace;

    @FXML
    public AnchorPane bgColor;

    private Messages messages = new Messages();

    private JSONObject jsonObject = new JSONObject();

    public void setFriendFace(String url) {
        FriendFace = new Image(url);
    }
    public void setFriendName(String FriendName) {
        this.FriendName.setText(FriendName);
    }
    public void setMsg(String msg) {
        Msg.setText(msg);
    }
    public void setSendtime(String sendtime) {
        Sendtime.setText(sendtime.substring(0,10));
    }
    public void setJson(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        if(jsonObject.getString("chTop").equals("1")) {
            bgColor.setStyle("-fx-background-color: #DCDCDC; -fx-border-width: 0 0 1 0; -fx-border-color: black;");
        }
    }

    /**
     * 点击联系人的事件，需要在右侧弹出该联系人的信息
     * @param event
     * @throws IOException
     */
    @FXML
    public void ChatAction(MouseEvent event) throws IOException {
        Boolean isGroup;
        //获取聊天内容，判断群还是联系人
        String ID = jsonObject.getString("chGroupid");
        JSONArray jsonArray = new JSONArray();
        if(ID !=null) {
            jsonArray = messages.getGroupMsg(Integer.valueOf(ID), null);
            isGroup = true;
        } else {
            ID = jsonObject.getString("chFriendid");
            if(ID !=null) {
                jsonArray = messages.getFriendMsg(Integer.valueOf(ID), null);
                isGroup = false;
            } else {
                System.out.println("用户ID和群ID都为空！");
                return;
            }
        }
        MouseButton button = event.getButton();
        //单击操作
        if (button == MouseButton.PRIMARY) {
            HomeController homeController = GlobalParam.homeController;
            SceneUtil sceneUtil = new SceneUtil();
            sceneUtil.tlmChatContent(homeController.getThreeLevelMenu(), sceneUtil.ChatContent, jsonArray, FriendName.getText(), ID, isGroup);
            //记录点击的聊天室
            if(isGroup) {
                GlobalParam.SelectGroupID = Integer.valueOf(ID);
            } else {
                GlobalParam.SelectFriendID = Integer.valueOf(ID);
            }
            //右键点击
        } else if (button == MouseButton.SECONDARY) {
            SecondaryUtil secondaryUtil = new SecondaryUtil(event,2,FriendName.getText(),jsonObject.getInteger("chId"));
            secondaryUtil.isGroup = isGroup;
            secondaryUtil.ID = Integer.valueOf(ID);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Msg.setWrapText(false);
    }
}
