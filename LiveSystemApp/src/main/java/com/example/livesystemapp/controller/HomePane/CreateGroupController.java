package com.example.livesystemapp.controller.HomePane;

import com.example.livesystemapp.util.HttpRequest.MultipartFileResource;
import com.example.livesystemapp.util.HttpRequest.PostRequest;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class CreateGroupController implements Initializable {

    @FXML
    private TextField GroupName;

    @FXML
    private ImageView groupImg;

    @FXML
    private AnchorPane mainPane;

    private File file;

    @FXML
    void upload(ActionEvent event) {
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
                groupImg.setImage(new Image(file.toURI().toURL().toString()));
            } catch (MalformedURLException e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    void Create(ActionEvent event) {
        String groupName = GroupName.getText();
        if(groupName==null || groupName.isEmpty()) {
            GroupName.setPromptText("请输入群名称！");
            GroupName.setStyle("-fx-prompt-text-fill: red;");
            return ;
        } else {
            PostRequest postRequest = new PostRequest();
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("GroupName", groupName);
            Integer groupID = postRequest.postReObject(paramMap, "Group/addGroup/").getInteger("data");
            if( groupID != null) {
                try {
                    //上传图片
                    if(file!=null && file.isFile()) {
                        InputStream inputStream = new FileInputStream(file);
                        paramMap.clear();
                        paramMap.add("file", new MultipartFileResource(inputStream, groupID.toString()));
                        postRequest.sendFile(paramMap, "files/saveFile/");
                    }
                } catch (FileNotFoundException e) {
                    System.out.println(e);
                }
            }
        }
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
