package com.example.livesystemapp.controller.RequestProcess;

import com.example.livesystemapp.util.HttpRequest.PostRequest;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class SendFQController implements Initializable {

    @FXML
    private Label question;

    @FXML
    private Label remarkL;

    @FXML
    private TextField remark;

    @FXML
    private TextField intro;

    private Integer friendID;

    private boolean isFriend;

    public void setFriendID(Integer ID) {
        friendID = ID;
    }
    public void setQuestion(String str) {
        question.setText(str);
    }
    public void isFriend(boolean bl) {
        isFriend = bl;
        if(!bl) {
            remarkL.setVisible(false);
            remarkL.setManaged(false);
            remark.setVisible(false);
            remarkL.setManaged(false);
        }
    }

    @FXML
    void send(ActionEvent event) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        if(isFriend) {
            paramMap.add("Fid", friendID);
        } else {
            paramMap.add("Gid", friendID);
        }
        paramMap.add("isAddFriend", isFriend?1:0);
        paramMap.add("Alias", remark.getText());
        paramMap.add("Intro", intro.getText());
        new PostRequest().postReObject(paramMap, "Friend/addFriend/");
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
