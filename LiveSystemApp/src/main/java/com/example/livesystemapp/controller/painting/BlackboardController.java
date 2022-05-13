package com.example.livesystemapp.controller.painting;

import com.example.livesystemapp.service.WebSocket.WSClient;
import com.example.livesystemapp.util.ChangeImgColorUtil;
import com.example.livesystemapp.util.FFmpegUtil;
import com.example.livesystemapp.util.HttpRequest.PostRequest;
import com.example.livesystemapp.util.Param.GlobalParam;
import de.felixroske.jfxsupport.FXMLController;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ws.schild.jave.process.ProcessWrapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@FXMLController
public class BlackboardController implements Initializable {

    @FXML
    private AnchorPane canvasPane;

    @FXML
    private StackPane root;

    @FXML
    private ImageView cameraImg;
    @FXML
    private ImageView mikeImg;
    @FXML
    private ImageView videoImg;

    @FXML
    private Button minBT;
    @FXML
    private Button switchBT;
    @FXML
    private Button closeBT;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private Label bord;

    private Boolean onClick = false;
    private Boolean onClick2 = true;
    private Boolean onClick3 = true;
    private Boolean onClick4 = true;
    private Boolean onClick5 = true;
    private Stage stage = new Stage();
    private Integer selCamera = null;
    private Integer selMike = null;
    private Integer selectDev = null;
    private FFmpegUtil ffmpegUtil = new FFmpegUtil();
    private Map<String,List<String>> devices = ffmpegUtil.getDevice();
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private void showPane(MouseEvent event, String device) {
        if(device.equals("Video")) {
            selectDev = selCamera;
        } else if(device.equals("Audio")) {
            selectDev = selMike;
        }
        //开启一个顶级容器
        Stage stage = new Stage();
        VBox vBox = new VBox();
        vBox.setPrefWidth(150);
        vBox.setPrefHeight(20);
        vBox.setStyle("-fx-background-color: #757575");
        Label title = new Label("选择麦克风");
        title.setPrefHeight(20);
        title.setStyle("-fx-background-color: #757575; -fx-text-fill: #f5f5f5;");
        vBox.getChildren().add(title);
        List<Label> labels = new ArrayList<>();
        Label banCamera = new Label("    "+"禁止");
        banCamera.setPrefHeight(20);
        banCamera.setStyle("-fx-background-color: #757575; -fx-text-fill: #f5f5f5;");
        if(selectDev == null) {
            banCamera.setText(" √ "+"禁止");
        }
        for(int i=0;i<devices.get(device).size();i++) {
            vBox.setPrefHeight(vBox.getHeight()+20);
            String camera = devices.get(device).get(i);
            int num = i;
            Label newLabel = new Label();
            labels.add(newLabel);
            if(selectDev != null && selectDev == Integer.valueOf(i)) {
                newLabel.setText(" √ "+camera);
            } else {
                newLabel.setText("    "+camera);
            }
            vBox.getChildren().add(newLabel);
            newLabel.setPrefHeight(20);
            newLabel.setStyle("-fx-background-color: #757575; -fx-text-fill: #f5f5f5;");
            newLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(selectDev != Integer.valueOf(num)) {
                        if(selectDev != null) {
                            String x = labels.get(selectDev).getText();
                            labels.get(selectDev).setText("    "+x.substring(3));
                        } else {
                            banCamera.setText("    "+"禁止");
                        }
                        selectDev = Integer.valueOf(num);
                        if(device.equals("Video")) {
                            selCamera = selectDev;
                        } else if(device.equals("Audio")) {
                            selMike = selectDev;
                        }
                        newLabel.setText(" √ "+camera);
                    } else if(selectDev == Integer.valueOf(num)) {
                        newLabel.setText("    "+camera);
                        banCamera.setText(" √ "+"禁止");
                        selectDev = null;
                        if(device.equals("Video")) {
                            selCamera = selectDev;
                        } else if(device.equals("Audio")) {
                            selMike = selectDev;
                        }
                    }
                }
            });
        }
        vBox.getChildren().add(banCamera);
        banCamera.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(selectDev != null) {
                    String x = labels.get(selectDev).getText();
                    labels.get(selectDev).setText("    "+x.substring(3));
                    selectDev = null;
                    if(device.equals("Video")) {
                        selCamera = selectDev;
                    } else if(device.equals("Audio")) {
                        selMike = selectDev;
                    }
                }
                banCamera.setText(" √ "+"禁止");
            }
        });
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        //获取鼠标的位置，在鼠标点击的地方出现页面
        stage.setX(event.getScreenX());
        stage.setY(event.getScreenY());
        stage.setAlwaysOnTop(true);
        //鼠标离开消失
        vBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });
        //展示窗口
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @FXML
    public void selCamera(MouseEvent event) {
        showPane(event, "Video");
    }
    @FXML
    public void selMike(MouseEvent event) {
        showPane(event, "Audio");
    }

    @FXML
    void cameraAction(MouseEvent event) {
        String url;
        if(onClick3) {
            url = "img/摄像头-关闭.png";
            Task task = new Task<Void>() {
                @Override
                public Void call() {
                    ffmpegUtil.startDevice(selCamera);
                    return null;
                }
            };
            new Thread(task).start();
        } else {
            url = "img/摄像头-打开.png";
            ffmpegUtil.stopDevice();
        }
        onClick3 = !onClick3;
        cameraImg.setImage(ChangeImgColorUtil.pixWithImage(7, new Image(url)));
    }

    @FXML
    void mikeAction(MouseEvent event) {
        String url;
        if(onClick4) {
            url = "img/麦克风-关闭.png";
        } else {
            url = "img/麦克风-打开.png";
        }
        if(GlobalParam.LiveGroup != null) {
            ffmpegUtil.stopLive();
            if(selMike != null && devices.get("Audio").get(selMike) != null) {
                ffmpegUtil.statLive(GlobalParam.UserID, devices.get("Audio").get(selMike));
            }
        }
        onClick4 = !onClick4;
        mikeImg.setImage(ChangeImgColorUtil.pixWithImage(7, new Image(url)));
    }

    /**
     * 录像进程
     *
     * @return
     */
    private Thread videoThread() {
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                ffmpegUtil.recording(null, null);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        return thread;
    }

    @FXML
    void videoAction(MouseEvent event) {
        String url;
        if(onClick5) {
            url = "img/录制停止.png";
            videoThread();
        } else {
            url = "img/录制.png";
            ffmpegUtil.stopRecord();
        }
        onClick5 = !onClick5;
        videoImg.setImage(ChangeImgColorUtil.pixWithImage(7, new Image(url)));
    }

    @FXML
    void minAction(ActionEvent event) {
        stage.setIconified(true);
    }
    @FXML
    void switchAction(ActionEvent event) {
        stage.setMaximized(onClick2);
        if(onClick2) {
            changeCanvas(1707.0,952.0);
            bord.setLayoutX(bord.getLayoutX()+200);
            menuPane.setLayoutX(menuPane.getLayoutX()+400);
        } else {
            changeCanvas(1315.0,765.0);
            bord.setLayoutX(bord.getLayoutX()-200);
            menuPane.setLayoutX(menuPane.getLayoutX()-400);
        }
        onClick2 = !onClick2;
    }
    @FXML
    void closeAction(MouseEvent event) {
        ffmpegUtil.stopRecord();
        stage.close();
    }
    private void changeCanvas(Double x, Double Y) {
        canvas.setWidth(x);
        canvas.setHeight(Y);
        canvas_effect.setWidth(x);
        canvas_effect.setHeight(Y);
        System.out.println(root.getWidth()+":"+root.getHeight());
    }

    private Canvas canvas = new Canvas(1315, 765);
    private Canvas canvas_effect = new Canvas(1315, 765);
    private int switchTake = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //图片颜色反转，高亮处理
        cameraImg.setImage(ChangeImgColorUtil.pixWithImage(7, cameraImg.getImage()));
        mikeImg.setImage(ChangeImgColorUtil.pixWithImage(7, mikeImg.getImage()));
        videoImg.setImage(ChangeImgColorUtil.pixWithImage(7, videoImg.getImage()));
//        画板控件canvas
        canvas_effect.setStyle("-fx-background-color: #353030");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc_effect = canvas_effect.getGraphicsContext2D();
        InitDraw initDraw = new InitDraw();
        initDraw.initDraw(gc, gc_effect);
//        清屏按钮
        Button button_clear = new Button("清屏");
        button_clear.setStyle("-fx-font-size: 18px; -fx-pref-height: 20px; -fx-pref-width: 80px");
        button_clear.setOnAction(event -> gc.clearRect(0, 0, canvas_effect.getWidth(), canvas_effect.getHeight()));
//        下拉框ComboBox
        new setComboBox(root, canvas_effect, gc, gc_effect);
//        橡皮擦
        Button button_eraser = new Button("橡皮擦");
        String style = "-fx-font-size: 18px; -fx-pref-height: 20px; -fx-pref-width: 80px";
        button_eraser.setStyle(style);
        button_eraser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onClick = !onClick;
                if(onClick) {
                    gc.setStroke(Color.rgb(53,48,48));
                    gc.setLineWidth(50);
                    gc_effect.setStroke(Color.rgb(53,48,48));
                    gc_effect.setLineWidth(50);
                    if (setComboBox.drawLine.isActive) {
                        setComboBox.drawLine.removeAction();
                        switchTake = 0;
                    }
                    if (setComboBox.drawCircle.isActive) {
                        setComboBox.drawCircle.removeAction();
                        switchTake = 1;
                    }
                    if (setComboBox.drawOval.isActive) {
                        setComboBox.drawOval.removeAction();
                        switchTake = 2;
                    }
                    if (setComboBox.drawRect.isActive) {
                        setComboBox.drawRect.removeAction();
                        switchTake = 3;
                    }
                    setComboBox.drawLine.takeAction();
                    button_eraser.setStyle("-fx-background-color: BLACK;"+style);
                } else {
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(2);
                    gc_effect.setStroke(Color.BLACK);
                    gc_effect.setLineWidth(2);
                    switch (switchTake) {
                        case 0:setComboBox.drawLine.takeAction();break;
                        case 1:setComboBox.drawCircle.takeAction();break;
                        case 2:setComboBox.drawOval.takeAction();break;
                        case 3:setComboBox.drawRect.takeAction();break;
                    }
                    button_eraser.setStyle("-fx-background-color: WHITE;"+style);
                }
            }
        });
//        布局
        root.setStyle("-fx-background-color: #353030");
        root.getChildren().addAll(canvas, canvas_effect, button_eraser, button_clear);
        root.setAlignment(Pos.CENTER);
        StackPane.setAlignment(canvas, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(canvas_effect, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(button_eraser, Pos.TOP_CENTER);
        StackPane.setAlignment(button_clear, Pos.TOP_RIGHT);
        //退出关闭直播
        stage.setOnCloseRequest(event -> {
            ffmpegUtil.stopLive();
            GlobalParam.threadStatic.destroy();
            if(GlobalParam.LiveGroup != null) {
                //群消息规则   "GROUP:"+GroupID+";"+"USER:"+UserID+";"+message
                WSClient.webSocketClient.send("GROUP:"+GlobalParam.LiveGroup+";"+"USER:"+GlobalParam.UserID+";"+" 【退出直播】 ");
                MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
                paramMap.add("client", null);
                paramMap.add("state", 0);
                paramMap.add("gid", GlobalParam.LiveGroup);
                new PostRequest().postReObject(paramMap, "live/updateState");
            }
        });
    }
}
