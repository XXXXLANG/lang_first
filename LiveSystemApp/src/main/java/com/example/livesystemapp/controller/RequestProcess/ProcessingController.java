package com.example.livesystemapp.controller.RequestProcess;

import com.example.livesystemapp.util.HttpRequest.PostRequest;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@FXMLController
public class ProcessingController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView faceIcon;

    @FXML
    private Label createTime;

    @FXML
    private Label intro;

    @FXML
    private Label name;

    @FXML
    private Label isAddFriend;

    @FXML
    private Button trueBt;

    @FXML
    private Button falseBt;

    private Integer frId;

    private VBox pane;

    private boolean isGroupRequest = false;

    public void setVbox(VBox pane) {
        this.pane = pane;
    }
    public void setFrId(Integer frid) {
        frId = frid;
    }
    public void setFaceIcon(ImageView faceIcon) {
        this.faceIcon = faceIcon;
    }
    public void setIsAddFriend(String addInfo) {
        isAddFriend.setText(addInfo);
        isGroupRequest = true;
    }
    public void setIntro(String intro) {
        this.intro.setText(intro);
    }
    public void setName(String name) {
        this.name.setText(name);
    }
    public void setCreateTime(String createTime) {
        if(createTime !=null && createTime.length() > 0) {
            this.createTime.setText(createTime.substring(0,10));
        }
    }

    @FXML
    void trueAction(ActionEvent event) {
        //开启一个顶级容器
        AnchorPane anchorPane = new AnchorPane();
        Stage stage = new Stage();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        //内部组件
        Label label = new Label("通过 "+name.getText()+" 的好友申请");
        Label InAlias = new Label("备注");
        TextField alias = new TextField();
        Button button = new Button("确定");
        anchorPane.getChildren().addAll(label, InAlias, alias, button);
        anchorPane.setPrefWidth(300);
        anchorPane.setPrefHeight(100);
        label.setLayoutX(30);
        label.setLayoutY(10);
        label.setPrefWidth(240);
        label.setAlignment(Pos.CENTER);
        InAlias.setLayoutX(60);
        InAlias.setLayoutY(40);
        alias.setLayoutX(100);
        alias.setLayoutY(40);
        button.setLayoutX(90);
        button.setLayoutY(70);
        button.setPrefWidth(120);
        button.setPrefHeight(20);
        button.setStyle("-fx-background-color: #009265;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Prs(1, alias.getText());
                stage.close();
            }
        });
        if(isGroupRequest) {
            label.setText("通过 "+name.getText()+" 的进群申请");
            InAlias.setVisible(false);
            InAlias.setManaged(false);
            alias.setVisible(false);
            alias.setManaged(false);
        }
        //展示窗口
        stage.setX(600);
        stage.setY(400);
        stage.setAlwaysOnTop(true);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @FXML
    void falseAction(ActionEvent event) {
        Prs(2, "");
    }

    /**
     * 是否接受（1：接受，0：未处理，2：拒绝）
     * @param state
     */
    private void Prs(Integer state, String alias) {
        ObservableList<Node> paneChildren = pane.getChildren();
        paneChildren.remove(mainPane);
        if(pane.getChildren().isEmpty()) {
            AnchorPane anchorPane = new AnchorPane();
            Label label = new Label("没有好友申请信息");
            anchorPane.getChildren().add(label);
            label.setLayoutY(220);
            label.setLayoutX(220);
            paneChildren.add(anchorPane);
        }
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("FR_ID", frId);
        paramMap.add("state", state);
        paramMap.add("Alias", alias);
        new PostRequest().postReObject(paramMap, "Friend/Processing/");
    }

}
