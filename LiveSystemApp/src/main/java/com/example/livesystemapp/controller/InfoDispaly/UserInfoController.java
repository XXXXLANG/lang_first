package com.example.livesystemapp.controller.InfoDispaly;

import com.example.livesystemapp.controller.RequestProcess.SendFQController;
import com.example.livesystemapp.util.Param.GlobalParam;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@FXMLController
public class UserInfoController implements Initializable {

    @FXML
    private DatePicker birthday;

    @FXML
    private Label pswLab;
    @FXML
    private TextField password;
    private String pwd;

    @FXML
    private Label sex;

    @FXML
    private TextField signaTure;

    @FXML
    private TextField tel;

    @FXML
    private Label location;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField userName;

    @FXML
    private Label age;

    @FXML
    private ImageView userFace;

    @FXML
    private Label edit;

    @FXML
    private Label send;

    private Boolean onEdit = true;

    private Integer uid = null;

    private File file;

    /**
     * 外部新建的本页面stage，用于控制页面关闭
     */
    private Stage stage = null;

    public void setStage(Stage stage) { this.stage = stage; }
    public void setBirthday(String birthday) {
        try {
            String data = birthday.substring(0,10);
            this.birthday.setValue(LocalDate.of(Integer.parseInt(data.substring(0,4)),Integer.parseInt(data.substring(5,7)),Integer.parseInt(data.substring(8,10))));
            age.setText(""+new GlobalParam().getAgeByBirth(data));
        } catch (Exception e) {
            System.out.println("计算年龄错误:"+e);
        }
    }
    public void setTel(String tel) {
        this.tel.setText(tel);
    }
    public void setSignaTure(String signaTure) {
        this.signaTure.setText(signaTure);
    }
    public void setUserName(String userName) {
        this.userName.setText(userName);
    }
    public void setUserFace(String userFace) {
        if(userFace != null) {
            this.userFace.setImage(new Image(userFace));
        }
    }
    public void setSex(String sex) {
        if(sex !=null && sex.equals("1")) {
            sex = "男";
        } else {
            sex = "女";
        }
        this.sex.setText(sex);
    }
    //国家+省份+城市
    public void setLocation(String n, String p, String c) {
        if(c != null) {
            this.location.setText(n+p+c);
        }
    }
    //加密*显示
    public void setPassword(String password) {
        pwd = password;
    }

    public void setEmail(String email) {
        pswLab.setText("邮箱");
        password.setText(email);
    }

    public void setUid(Integer uid) {
        this.uid = uid;
        //判断是否属于自己的信息
        isMe(uid!=null && GlobalParam.UserID == uid);
    }

    private void noClick(Boolean bl) {
        birthday.setDisable(bl);
        password.setDisable(bl);
        tel.setDisable(bl);
        signaTure.setDisable(bl);
        userName.setDisable(bl);
    }

    private void isMe(Boolean bl) {
        edit.setVisible(bl);
        edit.setManaged(bl);
        send.setVisible(!bl);
        send.setManaged(!bl);
        if(bl) {
            userFace.setOnMouseClicked(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.setTitle("View Pictures");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))
                );
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                        new FileChooser.ExtensionFilter("GIF", "*.gif"),
                        new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                        new FileChooser.ExtensionFilter("PNG", "*.png")
                );
                file = fileChooser.showOpenDialog(new Stage());
                if(file != null) {
                    try {
                        userFace.setImage(new Image(file.toURI().toURL().toString()));
                    } catch (MalformedURLException e) {
                        System.out.println(e);
                    }
                }
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noClick(true);
        isMe(true);
        //自动隐藏
        mainPane.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(stage != null) {
                    stage.close();
                }
            }
        });
        //编辑事件,自己才能显示
        edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onEdit = !onEdit;
                noClick(onEdit);
                if(onEdit) {
                    edit.setText("辑编");
                    //保存发送事件

                } else {
                    edit.setText("保存");
                    if(pswLab.getText().equals("密码")) {
                        password.setText(pwd);
                    }
                }
            }
        });
        //发送好友申请事件,对方显示
        send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(GlobalParam.myApp.getClass().getResource("/view/RequestProcess/SendFQ.fxml"));
                try {
                    Parent root = loader.load();
                    //开启一个顶级容器
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    //获取鼠标的位置，在鼠标点击的地方出现页面
                    stage.setX(event.getScreenX()-300);
                    stage.setY(event.getScreenY());
                    stage.setAlwaysOnTop(true);
                    SendFQController sendFQController = loader.getController();
                    sendFQController.setQuestion("你要添加"+userName.getText()+"为好友吗？");
                    sendFQController.isFriend(true);
                    sendFQController.setFriendID(uid);
                    //展示窗口
                    stage.initOwner(((Node)event.getSource()).getScene().getWindow());
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
