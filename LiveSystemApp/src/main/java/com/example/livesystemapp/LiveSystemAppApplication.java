package com.example.livesystemapp;

import com.example.livesystemapp.controller.HomePane.LoginController;
import com.example.livesystemapp.util.SplashView;
import com.example.livesystemapp.view.BlackboardMain;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LiveSystemAppApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launchApp(LiveSystemAppApplication.class, BlackboardMain.class,new SplashView(), args);
    }

    //主窗口
    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        //加载登录页面
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomePane/Login.fxml"));//BlackboardView
        Parent root = loader.load();
        LoginController controller = loader.getController();//BlackboardController
        controller.setApp(this);
        Scene scene = new Scene(root);
        //设置样式
        //scene.getStylesheets().add(getClass().getResource("/css/jfoenix-components.css").toExternalForm());
        //绑到顶层容器上
        primaryStage.setScene(scene);
        //设置标题
        primaryStage.setTitle("JAVAFX Login");
        //展示窗口
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.print("监听到Login窗口关闭，关闭系统线程");
                //关闭程序线程
                System.exit(0);
            }
        });
    }

    /**
     * 隐藏当前窗口
     */
    public void hideWindow(){
        mainStage.close();
    }

}