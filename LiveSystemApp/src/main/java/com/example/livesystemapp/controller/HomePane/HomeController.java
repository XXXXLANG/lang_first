package com.example.livesystemapp.controller.HomePane;

import com.example.livesystemapp.LiveSystemAppApplication;
import com.example.livesystemapp.controller.InfoDispaly.UserInfoController;
import com.example.livesystemapp.controller.painting.BlackboardController;
import com.example.livesystemapp.service.UserInfo;
import com.example.livesystemapp.service.WebSocket.WSClient;
import com.example.livesystemapp.util.FFmpegUtil;
import com.example.livesystemapp.util.Param.GlobalParam;
import com.example.livesystemapp.util.SceneUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class HomeController implements Initializable {

    @FXML
    private ImageView userFace;
    @FXML
    public Button live;
    @FXML
    private Button Friends;
    @FXML
    private Button Blackboard;
    @FXML
    private Button setting;

    @FXML
    private AnchorPane submenu;
    @FXML
    private AnchorPane threeLevelMenu;

    private SceneUtil sceneUtil = new SceneUtil();
    public Stage stage;

    public void setApp(LiveSystemAppApplication myApp) {
        GlobalParam.myApp = myApp;
    }
    public void setStage(Stage _stage){
        this.stage = _stage;
    }

    @FXML
    public void liveAction(MouseEvent event) throws IOException {
        sceneUtil.skipView(submenu, sceneUtil.LiveView);
        setThreeLevelMenu(sceneUtil.defaultPane);
    }
    @FXML
    public void FriendsAction(MouseEvent event) throws IOException {
        sceneUtil.skipView(submenu, sceneUtil.FriendsListView);
        setThreeLevelMenu(sceneUtil.defaultPane);
    }

    @FXML
    public void BlackboardAction(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(GlobalParam.myApp.getClass().getResource("/view/BlackboardView.fxml"));
        try {
            Parent root = loader.load();
            //????????????????????????
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            ((BlackboardController)loader.getController()).setStage(stage);
            //????????????
            stage.setTitle("JavaFX ??????");
            stage.initStyle(StageStyle.UNDECORATED);
            //????????????
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void settingAction(ActionEvent event) {

    }


    /**
     * ??????????????????
     * @param viewPane
     */
    public void setThreeLevelMenu(String viewPane) throws IOException {
        initSelID();
        sceneUtil.skipView(threeLevelMenu, viewPane);
    }

    public AnchorPane getThreeLevelMenu() {
        initSelID();
        return threeLevelMenu;
    }

    /**
     * ??????????????????????????????????????????ID???????????????????????????????????????????????????
     */
    private void initSelID() {
        GlobalParam.SelectFriendID = null;
        GlobalParam.SelectGroupID = null;
    }

    /**
     * ???????????????
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalParam.homeController = this;
        //??????????????????
        userFace.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(GlobalParam.myApp.getClass().getResource("/view/InfoDisplay/UserInfo.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //????????????????????????
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                //??????????????????????????????
                UserInfoController userInfoController = new UserInfo().insetUserInfo(loader.getController());
                userInfoController.setStage(stage);
                //????????????????????????????????????????????????????????????
                stage.setX(event.getScreenX());
                stage.setY(event.getScreenY());
                //???????????????,???????????????
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setAlwaysOnTop(true);
                //????????????
                stage.show();
            }
        });
        //????????????????????????WebSocket?????????
        WSClient.startWS();
    }
}
