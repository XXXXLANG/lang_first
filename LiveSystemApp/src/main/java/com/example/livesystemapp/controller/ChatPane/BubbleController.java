package com.example.livesystemapp.controller.ChatPane;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class BubbleController implements Initializable {

    @FXML
    private Image imageView;

    @FXML
    private Label FriendName;

    @FXML
    private TextArea content;

    @FXML
    private Label sendTime;

    private Boolean isMe;

    public void setImageView() {
        imageView = new Image("");
    }
    public void setIsMy(Boolean b) {
        isMe = b;
    }
    public void setFriendName(String name) {
        FriendName.setText(name);
    }
    public void setSendTime(String time) {
        sendTime.setText(time);
    }
    public void setContent(String msg) {
        content.setText(msg);
        Double widthLen = new Text(msg).getBoundsInLocal().getWidth();
        if(widthLen>390) {
            content.setPrefWidth(400);
            if(isMe) {
                content.setLayoutX(110);
            }
        } else {
            content.setPrefWidth(widthLen+20);
            if(isMe) {
                Double lyX = 485-widthLen;
                if(lyX>465) {
                    content.setLayoutX(465);
                } else {
                    content.setLayoutX(lyX);
                }
            }
        }
        Double ph = 25.0;
        while (widthLen>400) {
            ph += 25;
            widthLen -= 400;
        }
        content.setPrefHeight(ph);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        content.setStyle("-fx-background-color: #00CD66;");
        content.setWrapText(true);
        content.setEditable(false);
    }
}
