package com.example.livesystemapp.controller.RequestProcess;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.util.HttpRequest.PostRequest;
import com.example.livesystemapp.util.SceneUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import org.springframework.util.LinkedMultiValueMap;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class FriendRequestController implements Initializable {

    @FXML
    private VBox friendRequest;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //获取好友申请信息,如果有就清空组件，重新载入
        String url = "Friend/getProcess/";
        JSONArray jsonArray = new PostRequest().postReArray(new LinkedMultiValueMap<>(), url);
        SceneUtil sceneUtil = new SceneUtil();
        if(jsonArray.size() > 0) {
            for(int i=0;i<jsonArray.size();i++) {
                if(i==0) {
                    friendRequest.getChildren().clear();
                }
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                sceneUtil.addProcessing(friendRequest, jsonObject);
            }
        } else {
            AnchorPane anchorPane = new AnchorPane();
            Label label = new Label("没有好友申请信息");
            anchorPane.getChildren().add(label);
            label.setLayoutY(220);
            label.setLayoutX(220);
            friendRequest.getChildren().add(anchorPane);
        }
    }
}
