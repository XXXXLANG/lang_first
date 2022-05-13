package com.example.livesystemapp.util;

import com.example.livesystemapp.util.Param.GlobalParam;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationsUtil {

    public static void getMsg(String msg) {
        Stage stage = new Stage();
        VBox border = new VBox();
        border.setStyle("-fx-background-color: rgba(255,255,255,0);");
        Scene scene=new Scene(border, 400, 100);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(null);
        stage.setX(GlobalParam.homeController.stage.getX()+500);
        stage.setY(GlobalParam.homeController.stage.getY()-50);

        //内部组件
        Label label = new Label(msg);
        label.setPrefWidth(400);
        label.setPrefHeight(50);
        label.setScaleY(border.getScaleY());
        label.setScaleX(border.getScaleX());
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-background-color: #c6d4e1; -fx-text-fill: black;");
        border.getChildren().add(label);

        //定义矩形的平移效果
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), label);
        translateTransition.setFromY(0);
        translateTransition.setToY(50);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);

        //定义矩形的淡入淡出效果
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(7000), label);
        fadeTransition.setFromValue(1.0f);
        fadeTransition.setToValue(0f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);

        //定义矩形的淡入淡出效果
        FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(2000), label);
        fadeTransition2.setFromValue(0f);
        fadeTransition2.setToValue(1f);
        fadeTransition2.setCycleCount(1);
        fadeTransition2.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition, fadeTransition2);
        parallelTransition.setCycleCount(1);
        parallelTransition.play();

        stage.show();
        //持续显示7秒
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            int oldTime = 0;
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(oldTime<7) {
                        oldTime++;
                    } else {
                        time.cancel();
                        stage.close();
                    }
                });
            }
        },100,1000);
    }

    public static void getInteraction(String UserName, String interactionInfo) {
        Stage stage = new Stage();
        VBox border = new VBox();
        border.setStyle("-fx-background-color: rgba(255,255,255,1);");
        Scene scene=new Scene(border, 300, 300);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(null);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMaxX()-300);
        stage.setY(bounds.getMaxY()-300);

        //内部组件
        Label label = new Label(UserName);
        label.setPrefWidth(300);
        label.setPrefHeight(100);
        label.setAlignment(Pos.CENTER);
        Label label1 = new Label(interactionInfo);
        label1.setPrefWidth(300);
        label1.setPrefHeight(200);
        label1.setAlignment(Pos.CENTER);
//        label.setStyle("-fx-background-color: #c6d4e1; -fx-text-fill: black;");
        border.getChildren().addAll(label, label1);

        //定义矩形的平移效果
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), border);
        translateTransition.setFromY(300);
        translateTransition.setToY(0);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);

        //定义矩形的淡入淡出效果
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(10000), border);
        fadeTransition.setFromValue(1.0f);
        fadeTransition.setToValue(0f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);

        //定义矩形的淡入淡出效果
        FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(1000), border);
        fadeTransition2.setFromValue(0f);
        fadeTransition2.setToValue(1f);
        fadeTransition2.setCycleCount(1);
        fadeTransition2.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition, fadeTransition2);
        parallelTransition.setCycleCount(1);
        parallelTransition.play();

        stage.show();
        //持续显示10秒
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            int oldTime = 0;
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(oldTime<10) {
                        oldTime++;
                    } else {
                        time.cancel();
                        stage.close();
                    }
                });
            }
        },100,1000);
    }
}
