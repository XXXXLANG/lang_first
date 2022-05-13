package com.example.livesystemapp.controller.DefaultPane;

import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.controller.HomePane.HomeController;
import com.example.livesystemapp.util.Param.GlobalParam;
import com.example.livesystemapp.util.SceneUtil;
import com.example.livesystemapp.util.SecondaryUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class FriendViewController implements Initializable {

    @FXML
    //头像
    private Image FriendFace;

    @FXML
    //昵称
    private Label FriendName;

    @FXML
    //职位
    private Label Position;

    //用户信息
    private JSONObject jsonObject = new JSONObject();

    public void setFriendFace(String url) { FriendFace = new Image(url); }

    public void setFriendName(String name) { FriendName.setText(name); }

    public void setPosition(String position) {
        Position.setText(position);
    }

    public void setJson(JSONObject jsonObject) { this.jsonObject = jsonObject; }

    /**
     * 点击联系人的事件，需要在右侧弹出该联系人的信息
     * @param event
     * @throws IOException
     */
    @FXML
    void FriendAction(MouseEvent event) throws IOException {
        MouseButton button = event.getButton();
        //单击操作
        if (button == MouseButton.PRIMARY) {
            HomeController homeController = GlobalParam.homeController;
            SceneUtil sceneUtil = new SceneUtil();
            sceneUtil.tlmFriendInfo(homeController.getThreeLevelMenu(), sceneUtil.FriendInfo, jsonObject);
        //右键点击
        } else if (button == MouseButton.SECONDARY) {
            new SecondaryUtil(event,0,FriendName.getText(),jsonObject.getJSONObject("friendInfo").getInteger("uid"));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
