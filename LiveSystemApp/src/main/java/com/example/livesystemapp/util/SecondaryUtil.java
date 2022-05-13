package com.example.livesystemapp.util;

import com.example.livesystemapp.util.HttpRequest.PostRequest;
import com.example.livesystemapp.util.Param.GlobalParam;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.SneakyThrows;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class SecondaryUtil {

    MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();

    public SecondaryUtil(MouseEvent event, Integer sel, String name, Integer ID) {
        //开启一个顶级容器
        Stage stage = new Stage();
        VBox vBox = new VBox();
        Label label = new Label();
        Scene scene = new Scene(vBox);
        if(sel == 0) {
            label.setText("删除联系人");
        } else if(sel == 1) {
            label.setText("退出群聊");
            Label label1 = new Label("更改消息提醒");
            label1.setPrefHeight(25);
            label1.setPrefWidth(100);
            label1.setAlignment(Pos.CENTER);
            label1.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1 1 0 1;");
            vBox.getChildren().add(label1);
            label1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @SneakyThrows
                @Override
                public void handle(MouseEvent event) {
                    stage.close();
                    paramMap.add("gid", ID);
                    new PostRequest().postReObject(paramMap, "home/changeGroupRemind");
                    GlobalParam.homeController.FriendsAction(event);
                }
            });
        } else if(sel == 2) {
            label.setText("删除聊天记录");
            Label label1 = new Label("更改聊天置顶");
            label1.setPrefWidth(100);
            label1.setPrefHeight(25);
            label1.setAlignment(Pos.CENTER);
            label1.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1 1 0 1;");
            vBox.getChildren().add(label1);
            label1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @SneakyThrows
                @Override
                public void handle(MouseEvent event) {
                    stage.close();
                    paramMap.add("chId", ID);
                    new PostRequest().postReObject(paramMap, "Msg/changeChatTop");
                    GlobalParam.homeController.liveAction(event);
                }
            });
        }
        vBox.getChildren().add(label);
        stage.setScene(scene);
        label.setPrefWidth(100);
        label.setPrefHeight(25);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1");
        vBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
                //开启一个顶级容器
                Stage stage = new Stage();
                AnchorPane pane = new AnchorPane();
                Scene scene = new Scene(pane);
                pane.setPrefWidth(300);
                pane.setPrefHeight(100);
                Label label1 = new Label();
                if(sel == 0) {
                    label1.setText("确定要删除联系人 "+name+" 吗？");
                } else if(sel == 1) {
                    label1.setText("确定要退出 "+name+" 吗？");
                } else if(sel == 2) {
                    label1.setText("确定要删除与 "+name+" 的聊天吗？");
                }
                Button bt1 = new Button("确定");
                pane.getChildren().addAll(label1, bt1);
                label1.setAlignment(Pos.CENTER);
                label1.setLayoutX(30);
                label1.setLayoutY(20);
                label1.setPrefWidth(240);
                bt1.setPrefWidth(120);
                bt1.setPrefHeight(20);
                bt1.setLayoutX(90);
                bt1.setLayoutY(70);
                bt1.setStyle("-fx-background-color: #009265;");
                bt1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @SneakyThrows
                    @Override
                    public void handle(MouseEvent event) {
                        if(sel == 0) {
                            friend(ID);
                            GlobalParam.homeController.FriendsAction(event);
                        } else if(sel == 1) {
                            group(ID);
                            GlobalParam.homeController.FriendsAction(event);
                        } else if(sel == 2) {
                            chat(ID);
                            GlobalParam.homeController.liveAction(event);
                        }
                        stage.close();
                    }
                });
                stage.setScene(scene);
                //获取鼠标的位置，在鼠标点击的地方出现页面
                stage.setX(event.getScreenX());
                stage.setY(event.getScreenY());
                stage.setAlwaysOnTop(true);
                //展示窗口
                stage.initOwner(((Node)event.getSource()).getScene().getWindow());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.show();
            }
        });
        //获取鼠标的位置，在鼠标点击的地方出现页面
        stage.setX(event.getScreenX());
        stage.setY(event.getScreenY());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        //展示窗口
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private void friend(Integer fid) {
        delChatParam(fid, false);
        paramMap.add("fid", fid);
        new PostRequest().postReObject(paramMap, "home/deleteFriend");
    }

    private void group(Integer gid) {
        delChatParam(gid, true);
        paramMap.add("gid", gid);
        new PostRequest().postReObject(paramMap, "home/deleteGroup");
    }

    public Integer ID;
    public Boolean isGroup;
    private void chat(Integer chId) {
        delChatParam(ID, isGroup);
        paramMap.add("chId", chId);
        new PostRequest().postReObject(paramMap, "Msg/deleteChat");
    }

    private void delChatParam(Integer ID, Boolean isGroup) {
        if(isGroup) {
            GlobalParam.paneForGroup.remove(ID);
            GlobalParam.controllerForGroup.remove(ID);
        } else {
            GlobalParam.paneForFriend.remove(ID);
            GlobalParam.controllerForFriend.remove(ID);
        }
    }
}
