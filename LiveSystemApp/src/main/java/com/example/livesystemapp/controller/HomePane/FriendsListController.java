package com.example.livesystemapp.controller.HomePane;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.controller.DefaultPane.FriendViewController;
import com.example.livesystemapp.service.ListInfo;
import com.example.livesystemapp.util.Param.GlobalParam;
import com.example.livesystemapp.util.SceneUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class FriendsListController implements Initializable {

    @FXML
    private TextField search;

    @FXML
    private VBox FriendsList;

    @FXML
    private VBox classroom;

    @FXML
    private VBox addRequest;

    @FXML
    private AnchorPane addFriend;

    @FXML
    private AnchorPane addClass;

    private SceneUtil sceneUtil = new SceneUtil();

    private ListInfo listInfo = new ListInfo();

    private com.example.livesystemapp.controller.DefaultPane.FriendViewController FriendViewController = new FriendViewController();

    @FXML
    void addAction(ActionEvent event) throws IOException {
        new SceneUtil().tlmAddRequest(search.getText(), true);
        search.setText("");
    }

    /**
     * 获取申请列表
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void friedRequestAction(ActionEvent event) throws IOException {
        HomeController homeController = GlobalParam.homeController;
        SceneUtil sceneUtil = new SceneUtil();
        sceneUtil.tlmFriendRequest(homeController.getThreeLevelMenu(), sceneUtil.FriendRequest);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //隐藏页面
        hidePane(false);
        //点击事件
        search.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hidePane(true);
            }
        });
        addRequest.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hidePane(false);
            }
        });
        addFriend.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @SneakyThrows
            @Override
            public void handle(MouseEvent event) {
                new SceneUtil().tlmAddRequest(search.getText(), true);
                search.setText("");
            }
        });
        addClass.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @SneakyThrows
            @Override
            public void handle(MouseEvent event) {
                new SceneUtil().tlmAddRequest(search.getText(), false);
                search.setText("");
            }
        });
        try {
            //获取朋友列表，加载到页面中
            JSONArray jsonObjects = listInfo.getFriendsList();
            if(jsonObjects!=null && jsonObjects.size()>0) {
                for(int i=0;i<jsonObjects.size();i++) {
                    JSONObject jsonObject = jsonObjects.getJSONObject(i);
                    sceneUtil.addFriendView(FriendsList, sceneUtil.FriendView, jsonObject);
                }
            }
            //获取聊天室列表，加载到页面中
            jsonObjects = listInfo.getGroupsList();
            if(jsonObjects!=null && jsonObjects.size()>0) {
                for(int i=0;i<jsonObjects.size();i++) {
                    JSONObject jsonObject = jsonObjects.getJSONObject(i);
                    sceneUtil.addGroupView(classroom, sceneUtil.GroupView, jsonObject);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void hidePane(Boolean bl) {
        addRequest.setVisible(bl);
        addRequest.setManaged(bl);
    }
}
