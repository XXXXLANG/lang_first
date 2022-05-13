package com.example.livesystemapp.controller.InfoDispaly;

import com.example.livesystemapp.controller.ChatPane.ChatViewController;
import com.example.livesystemapp.util.HttpRequest.PostRequest;
import com.example.livesystemapp.util.Param.GlobalParam;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

public class GroupInfoController {

    @FXML
    private Label GroupName;

    @FXML
    private Image GroupIcon;

    @FXML
    private Label teacherName;

    @FXML
    private Label intro;

    @FXML
    private Image teacherIcon;

    private Integer gid;

    public void setGid(Integer gid) {
        this.gid = gid;
    }
    public void setGroupIcon(String url) {
        GroupIcon = new Image(url);
    }
    public void setTeacherIcon(String url) {
        teacherIcon = new Image(url);
    }
    public void setGroupName(String name) {
        GroupName.setText(name);
    }
    public void setTeacherName(String name) {
        teacherName.setText(name);
    }
    public void setIntro(String intro) {
        this.intro.setText(intro);
    }

    @FXML
    void sendCharAction(MouseEvent event) throws IOException {
        ChatViewController chatViewController = GlobalParam.controllerForGroup.get(gid);
        if(chatViewController == null) {
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("isGroup", true);
            paramMap.add("fid", gid);
            new PostRequest().postReObject(paramMap, "Msg/addChat");
        }
        GlobalParam.homeController.liveAction(event);
        chatViewController = GlobalParam.controllerForGroup.get(gid);
        chatViewController.ChatAction(event);
    }
}
