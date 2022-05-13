package com.example.livesystemapp.controller.ChatPane;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.service.WebSocket.WSClient;
import com.example.livesystemapp.util.FFmpegUtil;
import com.example.livesystemapp.util.HttpRequest.PostRequest;
import com.example.livesystemapp.util.Param.GlobalParam;
import com.example.livesystemapp.util.SceneUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

@FXMLController
public class ChatContentController implements Initializable {
    @FXML
    private Label FriendName;

    @FXML
    private TextArea ChatInfo;

    @FXML
    private VBox chatContent;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label live;

    /**
     * 该好友的ID
     */
    private String friendID = new String();

    /**
     * 记录是群组还是好友
     */
    private Boolean isGroup;

    JSONArray jsonArray;

    public void setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
        if(!isGroup) {
            live.setVisible(false);
            live.setManaged(false);
        }
    }
    public VBox getChatContent() {
        return chatContent;
    }
    public void setFriendID(String id) { friendID = id; }
    public void setFriendName(String friendName) {
        FriendName.setText(friendName);
    }
    public void setJSON(JSONArray jsonArray) throws IOException {
        this.jsonArray = jsonArray;
        SceneUtil sceneUtil = new SceneUtil();
        //循环处理每一条聊天信息，展示
        if(jsonArray!=null && jsonArray.size()>0) {
            for(int i=0;i<jsonArray.size();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //显示信息
                sceneUtil.addBubble(chatContent, jsonObject, friendID);
            }
            scrollPane.setVvalue(1);
        }
    }

    @FXML
    void sendInfoAction(MouseEvent event) {
        String msg;
        String msgInfo = ChatInfo.getText();
        if(msgInfo!=null && msgInfo.length()>0) {
            //判断群聊私聊
            byte[] str;
            try {
                str = msgInfo.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                ChatInfo.setPromptText("输入的内容无法转换为UTF-8编码！");
                ChatInfo.setText("");
                return;
            }
            if(str.length > 250) {
                ChatInfo.setPromptText("输入的内容超出最大长度！");
                ChatInfo.setText("");
                return;
            } else {
                ChatInfo.setPromptText("");
            }
            if(isGroup) {
                msg = "GROUP:"+friendID+";"+"USER:"+GlobalParam.UserID+";"+msgInfo;
            } else {
                msg = "FRIEND:"+friendID+";"+"USER:"+GlobalParam.UserID+";"+msgInfo;
            }
            WSClient.webSocketClient.send(msg);
            ChatInfo.setText("");
        }
    }

    @FXML
    void FriendInfoAction(ActionEvent event) {
        //显示好友信息

    }

    private Thread thread = null;

    @FXML
    void live(MouseEvent event) {
        String content = ChatInfo.getText();
        //获取班级直播状态
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("gid", friendID);
        JSONObject jsonObject = new PostRequest().postReObject(paramMap, "live/getOnlineState");
        if(jsonObject != null && jsonObject.getInteger("ostate") == 0) {
            //开启一个顶级容器
            Stage stage = new Stage();
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefWidth(300);
            anchorPane.setPrefHeight(120);
            Label label = new Label("是否要开启直播？");
            Button button1 =new Button("开启");
            Button button2 =new Button("取消");
            button1.setOnMouseClicked(event12 -> {
                stage.close();
                Task task = new Task<Void>() {
                    @Override
                    public Void call() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println("sleep异常！！！！！！！");
                        }
                        new FFmpegUtil().statLive(GlobalParam.UserID, null);
                        return null;
                    }
                };
                thread = new Thread(task);
                paramMap.add("client", GlobalParam.UserID);
                paramMap.add("state", 1);
                paramMap.add("gid", friendID);
                JSONObject jsonObject1 = new PostRequest().postReObject(paramMap, "live/updateState");
                if(jsonObject1 != null) {
                    thread.start();
                    GlobalParam.threadStatic = thread;
                    GlobalParam.homeController.BlackboardAction(event12);
                    ChatInfo.setText(" 【正在直播】 ");
                    sendInfoAction(event12);
                    ChatInfo.setText(content);
                    GlobalParam.LiveGroup = friendID;
                }
            });
            button2.setOnMouseClicked(event1 -> stage.close());
            anchorPane.getChildren().addAll(label, button1, button2);
            label.setLayoutX(50);
            label.setLayoutY(20);
            label.setPrefWidth(200);
            label.setAlignment(Pos.CENTER);
            button1.setLayoutX(30);
            button1.setLayoutY(60);
            button1.setPrefWidth(60);
            button2.setLayoutX(210);
            button2.setLayoutY(60);
            button2.setPrefWidth(60);
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            //获取鼠标的位置，在鼠标点击的地方出现页面
            stage.setX(event.getScreenX()-450);
            stage.setY(event.getScreenY()-300);
            stage.setAlwaysOnTop(true);
            //展示窗口
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } else if(jsonObject != null && jsonObject.getInteger("ostate") == 1) {
            //开启一个顶级容器
            Stage stage = new Stage();
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefWidth(300);
            anchorPane.setPrefHeight(120);
            Label label = new Label("是否要观看直播？");
            Button button1 =new Button("进入");
            Button button2 =new Button("取消");
            button1.setOnMouseClicked(event12 -> {
                stage.close();
                new FFmpegUtil().liveView(jsonObject.getString("oclient"));
                ChatInfo.setText(" 【进入直播间】 ");
                sendInfoAction(event);
                ChatInfo.setText(content);
                GlobalParam.LiveGroup = friendID;
                //打开用户观看直播控制页面（提问、举手、录制）
                Stage stage1 = new Stage();
                stage1.setTitle("直播间");
                AnchorPane anchorPane1 = new AnchorPane();
                anchorPane1.setPrefWidth(300);
                anchorPane1.setPrefHeight(80);
                Button button11 = new Button("举手");
                button11.setLayoutX(30);
                button11.setLayoutY(30);
                button11.setPrefWidth(60);
                Button button21 = new Button("提问");
                button21.setLayoutX(120);
                button21.setLayoutY(30);
                button21.setPrefWidth(60);
                Button button3 = new Button("录制");
                button3.setLayoutX(210);
                button3.setLayoutY(30);
                button3.setPrefWidth(60);
                //互动消息规则   "Interaction:"+GroupID+";"+"USER:"+UserID+";"+message
                button11.setOnMouseClicked(event13 -> {
                    WSClient.webSocketClient.send("Interaction:"+jsonObject.getString("oclient")+";"+"USER:"+GlobalParam.UserID+";"+"举手");
                    timerBT(button11);
                });
                button21.setOnMouseClicked(event14 -> {
                    WSClient.webSocketClient.send("Interaction:"+jsonObject.getString("oclient")+";"+"USER:"+GlobalParam.UserID+";"+"提问");
                    timerBT(button21);
                });
                button3.setOnMouseClicked(event15 -> {
                    Task task = new Task<Void>() {
                        @Override
                        public Void call() {
                            if(button3.getText().equals("录制")) {
                                button3.setText("停止");
                                button3.setStyle("-fx-background-color: red;");
                                new FFmpegUtil().RecordVideo(null, jsonObject.getString("oclient"));
                            } else {
                                button3.setText("录制");
                                button3.setStyle("");
                                new FFmpegUtil().stopRecordVideo();
                            }
                            return null;
                        }
                    };
                    thread = new Thread(task);
                    thread.start();
                });
                //退出直播
                stage1.setOnCloseRequest(event16 -> {
                    ChatInfo.setText(" 【退出直播间】 ");
                    sendInfoAction(event);
                    ChatInfo.setText(content);
                    new FFmpegUtil().stopLiveView();
                });
                anchorPane1.getChildren().addAll(button11, button21, button3);
                Scene scene = new Scene(anchorPane1);
                stage1.setScene(scene);
                //获取鼠标的位置，在鼠标点击的地方出现页面
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();
                stage1.setX(bounds.getMaxX()-300);
                stage1.setY(bounds.getMaxY()/5);
                stage1.setAlwaysOnTop(true);
                //展示窗口
                stage1.show();
            });
            button2.setOnMouseClicked(event1 -> stage.close());
            anchorPane.getChildren().addAll(label, button1, button2);
            label.setLayoutX(50);
            label.setLayoutY(20);
            label.setPrefWidth(200);
            label.setAlignment(Pos.CENTER);
            button1.setLayoutX(30);
            button1.setLayoutY(60);
            button1.setPrefWidth(60);
            button2.setLayoutX(210);
            button2.setLayoutY(60);
            button2.setPrefWidth(60);
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            //获取鼠标的位置，在鼠标点击的地方出现页面
            stage.setX(event.getScreenX()-450);
            stage.setY(event.getScreenY()-300);
            stage.setAlwaysOnTop(true);
            //展示窗口
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }
    }

    //十秒不能点击
    private void timerBT(Button button) {
        button.setVisible(false);
        button.setManaged(false);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int oldTime = 0;
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(oldTime<10) {
                            oldTime++;
                        } else {
                            button.setVisible(true);
                            button.setManaged(true);
                            timer.cancel();
                        }
                    }
                });
            }
        },100,1000);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //横向滚动条取消
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //文本自动换行
        ChatInfo.setWrapText(true);
    }


}
