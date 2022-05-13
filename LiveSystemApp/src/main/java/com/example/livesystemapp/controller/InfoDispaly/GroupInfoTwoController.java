package com.example.livesystemapp.controller.InfoDispaly;

import com.example.livesystemapp.controller.ChatPane.ChatViewController;
import com.example.livesystemapp.controller.RequestProcess.SendFQController;
import com.example.livesystemapp.util.Param.GlobalParam;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

@FXMLController
public class GroupInfoTwoController {

    @FXML
    private Label intro;

    @FXML
    private Label Gname;

    @FXML
    private ImageView Timg;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label Tname;

    @FXML
    private ImageView groupImg;

    private Integer GID;

    public void setGID(Integer ugid) {
        GID = ugid;
    }
    public void setIntro(String intro) {
        this.intro.setText(intro);
    }
    public void setGname(String name) {
        Gname.setText(name);
    }
    public void setTname(String name) {
        Tname.setText(name);
    }
    public void setTimg(String url) {
        Timg.setImage(new Image(url));
    }
    public void setGroupImg(String url) {
        groupImg.setImage(new Image(url));
    }

    @FXML
    void accessGroup(MouseEvent event) throws IOException {
        ChatViewController chatViewController = GlobalParam.controllerForGroup.get(GID);
        if(chatViewController != null) {
            chatViewController.ChatAction(event);
            return;
        }
        FXMLLoader loader = new FXMLLoader(GlobalParam.myApp.getClass().getResource("/view/RequestProcess/SendFQ.fxml"));
        try {
            Parent root = loader.load();
            //开启一个顶级容器
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //获取鼠标的位置，在鼠标点击的地方出现页面
            stage.setX(event.getScreenX()-301);
            stage.setY(event.getScreenY()-250);
            stage.setAlwaysOnTop(true);
            SendFQController sendFQController = loader.getController();
            sendFQController.setQuestion("你要加入群聊 "+Gname.getText()+" 吗？");
            sendFQController.isFriend(false);
            sendFQController.setFriendID(GID);
            //展示窗口
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
