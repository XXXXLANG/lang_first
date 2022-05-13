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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class GroupViewController implements Initializable {

    @FXML
    //头像
    private Image GroupIcon;

    @FXML
    //昵称
    private Label GroupName;

    @FXML
    //职位
    private ImageView msgTab;

    //群信息
    private JSONObject GroupsInfo = new JSONObject();

    public void setGroupIcon(String url) { GroupIcon = new Image(url); }

    public void setGroupName(String name) { GroupName.setText(name); }

    public void setMsgTab(Boolean x) {
        String url = "img/";
        if(x) {
            msgTab.setImage(new Image(url+"消息不提醒.png"));
        } else {
            msgTab.setImage(new Image(url+"消息提醒.png"));
        }
    }

    public void setJson(JSONObject jsonObject) { GroupsInfo = jsonObject; }

    /**
     * 点击群标签的事件，需要在右侧弹出该群的信息
     * @param event
     * @throws IOException
     */
    @FXML
    void GroupAction(MouseEvent event) throws IOException {
        MouseButton button = event.getButton();
        //单击操作
        if (button == MouseButton.PRIMARY) {
            HomeController homeController = GlobalParam.homeController;
            SceneUtil sceneUtil = new SceneUtil();
            sceneUtil.tlmGroupInfo(homeController.getThreeLevelMenu(), SceneUtil.GroupInfo, GroupsInfo);
            //右键点击
        } else if (button == MouseButton.SECONDARY) {
            new SecondaryUtil(event,1,GroupName.getText(), GroupsInfo.getJSONObject("groupsInfo").getInteger("ugId"));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
