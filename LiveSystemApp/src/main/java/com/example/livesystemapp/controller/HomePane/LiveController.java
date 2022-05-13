package com.example.livesystemapp.controller.HomePane;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.service.Messages;
import com.example.livesystemapp.util.NotificationsUtil;
import com.example.livesystemapp.util.Param.GlobalParam;
import com.example.livesystemapp.util.SceneUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class LiveController implements Initializable {

    @FXML
    private TextField search;

    @FXML
    private VBox chatList;

    @FXML
    private VBox addRequest;

    @FXML
    private AnchorPane addFriend;

    @FXML
    private AnchorPane addClass;

    @FXML
    private ScrollPane scrollpane;

    private SceneUtil sceneUtil = new SceneUtil();

    private Messages messages = new Messages();

    @FXML
    void addAction(ActionEvent event) throws IOException {
        new SceneUtil().tlmAddRequest(search.getText(), true);
        search.setText("");
    }

    @FXML
    void createGroup(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(GlobalParam.myApp.getClass().getResource("/view/HomePane/CreateGroup.fxml"));
        try {
            Parent root = loader.load();
            //开启一个顶级容器
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //获取鼠标的位置，在鼠标点击的地方出现页面
            stage.setX(event.getScreenX());
            stage.setY(event.getScreenY());
            stage.setAlwaysOnTop(true);
            //展示窗口
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
            //获取聊天记录
            JSONArray jsonArray = messages.getChartList();
            if(jsonArray!=null && jsonArray.size()>0) {
                for(int i=0;i<jsonArray.size();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    sceneUtil.addChartView(chatList, sceneUtil.ChartView, jsonObject);
                    if(GlobalParam.UserID == null) {
                        GlobalParam.UserID = jsonObject.getInteger("chUserid");
                    }
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
