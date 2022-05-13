package com.example.livesystemapp.controller.HomePane;

import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.LiveSystemAppApplication;
import com.example.livesystemapp.service.Login;
import com.example.livesystemapp.service.WebSocket.WSClient;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

@FXMLController
public class LoginController implements Initializable {
    @FXML
    private AnchorPane loginPane;

    @FXML
    private CheckBox aotoLogin;

    @FXML
    private CheckBox remember;

    @FXML
    private TextField password;

    @FXML
    private Button login;

    @FXML
    private TextField userID;

    @FXML
    private ImageView userFace;

    @FXML
    private ImageView phone;

    @FXML
    private Label errorText;

    private LiveSystemAppApplication myApp;

    public void setApp(LiveSystemAppApplication myApp) {
        this.myApp = myApp;
    }

    private Login Login =new Login();

    @FXML
    void login(ActionEvent event) throws Exception {
        String uID = userID.getText();
        String pw = password.getText();
        //判断账号密码
        JSONObject body = Login.pwLogin(uID,pw);
        if(!body.getString("code").equals("0")) {
            errorText.setText(body.getString("msg"));
            return;
        }
        //正确转跳到主界面
        jumpStage();
    }

    @FXML
    void aotoLogin(ActionEvent event) {

    }

    @FXML
    void remember(ActionEvent event) {

    }

    @FXML
    private AnchorPane registerPane;

    @FXML
    private TextField loginID;

    @FXML
    private CheckBox sure;

    @FXML
    private Label back;

    @FXML
    private TextField rePsw;

    @FXML
    private TextField psw;

    @FXML
    void registerBT(ActionEvent event) {

    }

    @FXML
    void register(ActionEvent event) {
        //注册页面显示
        registerPane.setVisible(true);
        registerPane.setManaged(true);
        loginPane.setVisible(false);
        loginPane.setManaged(false);
        phonePane.setVisible(false);
        phonePane.setManaged(false);
    }

    @FXML
    private AnchorPane phonePane;

    @FXML
    private TextField sms;

    @FXML
    private Button smsRequest;

    @FXML
    private TextField phoneNum;

    @FXML
    private Label smsError;

    @FXML
    void smsRequestAction(ActionEvent event) throws InterruptedException {
        String phone = phoneNum.getText();
        if(isValidPhoneNumber(phone)) {
            smsRequest.setMouseTransparent(true);
            Login.smsRequest(phone);
            smsError.setText("");

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int oldTime = 0;
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(oldTime<61) {
                                smsRequest.setText((60-oldTime)+"秒后可以重新发");
                                oldTime++;
                            } else {
                                timer.cancel();
                                smsRequest.setMouseTransparent(false);
                                smsRequest.setText("获取验证码");
                            }
                        }
                    });
                }
            },100,1000);
        } else {
            smsError.setText("请输入正确的手机号码格式!");
        }
    }

    //电话号码格式判断
    private boolean isValidPhoneNumber(String phoneNumber) {
        if ((phoneNumber != null) && (!phoneNumber.isEmpty())) {
            return Pattern.matches("^1[3-9]\\d{9}$", phoneNumber);
        }
        return false;
    }

    @FXML
    void smslogin(ActionEvent event) {
        String phone = phoneNum.getText();
        String sms = this.sms.getText();
        if(isValidPhoneNumber(phone) && sms != null && sms.length() > 5) {
            JSONObject body = Login.smsLogin(phone,sms);
            smsError.setText(body.getString("msg"));
            //正确转跳到主界面
            if(body.getString("code").equals("0")) {
                jumpStage();
            }
        } else {
            smsError.setText("请验证手机号或验证码格式!");
        }
    }

    private void jumpStage() {
        myApp.hideWindow();
        FXMLLoader loader = new FXMLLoader(myApp.getClass().getResource("/view/HomePane/Home.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HomeController controller = loader.getController();
        controller.setApp(myApp);
        //开启一个顶级容器
        Stage stage = new Stage();
        controller.setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //设置标题
        stage.setTitle("JavaFX Welcome");
        //展示窗口
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.print("监听到Home窗口关闭，关闭系统线程");
                //调用logout接口，删除服务器Token记录
                new Login().logout();
                //关闭WebSocket链接
                new WSClient().webSocketClient.close();
                //关闭程序线程
                System.exit(0);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //点击图片空白部分有效
        phone.setPickOnBounds(true);
        //隐藏页面
        phonePane.setVisible(false);
        phonePane.setManaged(false);
        registerPane.setVisible(false);
        registerPane.setManaged(false);
        //短信登录或注册并且登录
        phone.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(phonePane.isVisible()) {
                    phone.setImage(new Image("img/phone.png"));
                } else {
                    phone.setImage(new Image("img/225默认头像.png"));
                }
                phonePane.setVisible(!phonePane.isVisible());
                phonePane.setManaged(!phonePane.isManaged());
            }
        });
        //返回登录页面
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                registerPane.setVisible(false);
                registerPane.setManaged(false);
                loginPane.setVisible(true);
                loginPane.setManaged(true);
            }
        });
    }
}
