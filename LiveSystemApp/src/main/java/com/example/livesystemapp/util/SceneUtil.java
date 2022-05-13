package com.example.livesystemapp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.controller.ChatPane.BubbleController;
import com.example.livesystemapp.controller.ChatPane.ChatContentController;
import com.example.livesystemapp.controller.ChatPane.ChatViewController;
import com.example.livesystemapp.controller.DefaultPane.FriendViewController;
import com.example.livesystemapp.controller.DefaultPane.GroupViewController;
import com.example.livesystemapp.controller.InfoDispaly.FriendInfoController;
import com.example.livesystemapp.controller.InfoDispaly.GroupInfoController;
import com.example.livesystemapp.controller.InfoDispaly.GroupInfoTwoController;
import com.example.livesystemapp.controller.InfoDispaly.UserInfoController;
import com.example.livesystemapp.controller.RequestProcess.AddRequestController;
import com.example.livesystemapp.controller.RequestProcess.FriendRequestController;
import com.example.livesystemapp.controller.RequestProcess.ProcessingController;
import com.example.livesystemapp.util.Param.GlobalParam;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;

public class SceneUtil {

    public final static String FriendsListView = "/view/HomePane/FriendsListView.fxml";

    public final static String LiveView = "/view/HomePane/LiveView.fxml";

    //默认三级菜单样式
    public final static String defaultPane = "/view/DefaultPane/DefaultPane.fxml";

    //三级菜单，联系人信息
    public final static String FriendInfo = "/view/InfoDisplay/FriendInfo.fxml";

    //三级菜单，群消息
    public final static String GroupInfo = "/view/InfoDisplay/GroupInfo.fxml";

    //三级菜单，聊天内容
    public final static String ChatContent = "/view/ChatPane/ChatContent.fxml";

    //三级菜单，好友申请
    public final static String FriendRequest = "/view/RequestProcess/FriendRequest.fxml";

    //好友标签模板
    public final static String FriendView = "/view/DefaultPane/FriendView.fxml";
    
    //群组标签模板
    public final static String GroupView = "/view/DefaultPane/GroupView.fxml";

    //聊天信息标签模板
    public final static String ChartView = "/view/ChatPane/ChatView.fxml";

    private ObservableList<Node> paneChildren;

    /**
     * 改变右侧菜单的界面
     * @param pane
     * @param pagePath
     * @throws IOException
     */
    public void skipView(AnchorPane pane, String pagePath) throws IOException {
        //菜单内容更新
        ObservableList<Node> paneChildren = pane.getChildren();
        paneChildren.clear();
        paneChildren.add(FXMLLoader.load(getClass().getResource(pagePath)));
    }

    /**
     * 添加好友列表内容
     * @param pane
     * @param pagePath
     * @param jsonObject
     * @throws IOException
     */
    public void addFriendView(VBox pane, String pagePath, JSONObject jsonObject) throws IOException {
        paneChildren = pane.getChildren();
        //添加的对象需要是新的对象，这样才可以单独控制
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());	// 设置BuilderFactory
        loader.setLocation(getClass().getResource(pagePath));	// 设置路径基准
        //用新的组件去把页面“装起来”
        InputStream in = getClass().getResourceAsStream(pagePath);
        AnchorPane anchorPane = (AnchorPane) loader.load(in); // 对象方法的参数是InputStream，返回值是Object
        //获取对象的控制类，独立控制
        FriendViewController FriendViewController = loader.getController();
        FriendViewController.setFriendName(jsonObject.getString("faliasuser"));
        FriendViewController.setPosition(jsonObject.getJSONObject("friendInfo").getString("position"));
        FriendViewController.setJson(jsonObject);

        paneChildren.add(anchorPane);
    }

    /**
     * 添加群组列表内容
     * @param pane
     * @param pagePath
     * @param jsonObject
     * @throws IOException
     */
    public void addGroupView(VBox pane, String pagePath, JSONObject jsonObject) throws IOException {
        paneChildren = pane.getChildren();
        //添加的对象需要是新的对象，这样才可以单独控制
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());	// 设置BuilderFactory
        loader.setLocation(getClass().getResource(pagePath));	// 设置路径基准
        //用新的组件去把页面“装起来”
        InputStream in = getClass().getResourceAsStream(pagePath);
        AnchorPane anchorPane = (AnchorPane) loader.load(in); // 对象方法的参数是InputStream，返回值是Object
        //获取对象的控制类，独立控制
        GroupViewController groupViewController = loader.getController();
        groupViewController.setGroupName(jsonObject.getJSONObject("groupsInfo").getString("ugName"));
        //1:开启免打扰，0：关闭免打扰
        groupViewController.setMsgTab(jsonObject.getInteger("ugtReminding") == 1);
        groupViewController.setJson(jsonObject);

        paneChildren.add(anchorPane);
    }

    /**
     * 添加联系人聊天标签
     * @param pane
     * @param pagePath
     * @param jsonObject
     * @throws IOException
     */
    public void addChartView(VBox pane, String pagePath, JSONObject jsonObject) throws IOException {
        paneChildren = pane.getChildren();
        //添加的对象需要是新的对象，这样才可以单独控制
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());   // 设置BuilderFactory
        loader.setLocation(getClass().getResource(pagePath));	// 设置路径基准
        //用新的组件去把页面“装起来”
        InputStream in = getClass().getResourceAsStream(pagePath);
        AnchorPane anchorPane = (AnchorPane) loader.load(in); // 对象方法的参数是InputStream，返回值是Object
        //获取对象的控制类，独立控制
        ChatViewController chatViewController = loader.getController();
        chatViewController.setFriendName(jsonObject.getString("friendName"));
        chatViewController.setMsg(jsonObject.getString("msg"));
        chatViewController.setSendtime(jsonObject.getString("chCreatetime"));
        chatViewController.setJson(jsonObject);
        //保存控制器
        Integer ID = jsonObject.getInteger("chFriendid");
        if(ID == null) {
            ID = jsonObject.getInteger("chGroupid");
            GlobalParam.paneForFriend.put(ID, pane);
            GlobalParam.controllerForGroup.put(ID, chatViewController);
        } else {
            GlobalParam.paneForGroup.put(ID, pane);
            GlobalParam.controllerForFriend.put(ID, chatViewController);
        }
        paneChildren.add(anchorPane);
    }

    /**
     * 添加聊天气泡
     * @param pane
     * @param jsonObject
     * @param friendID
     * @throws IOException
     */
    public void addBubble(VBox pane, JSONObject jsonObject, String friendID) throws IOException {
        paneChildren = pane.getChildren();
        //添加的对象需要是新的对象，这样才可以单独控制
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());   // 设置BuilderFactory
        String pagePath;
        Boolean isMy;
        //如果发送人是朋友,就在左边显示
        String fromID;
        if(jsonObject.getString("mfromuserid") != null && jsonObject.getString("mfromuserid").length() > 0) {
            fromID = jsonObject.getString("mfromuserid");
        } else {
            fromID = jsonObject.getString("gmFromid");
        }
        //如果是群消息，那么friendID是群ID
        if(GlobalParam.UserID.toString().equals(fromID)) {
            pagePath = "/view/ChatPane/MyBubble.fxml";
            isMy = true;
        } else {
            pagePath = "/view/ChatPane/FriendBubble.fxml";
            isMy = false;
        }
        loader.setLocation(getClass().getResource(pagePath));	// 设置路径基准
        //用新的组件去把页面“装起来”
        InputStream in = getClass().getResourceAsStream(pagePath);
        AnchorPane anchorPane = loader.load(in); // 对象方法的参数是InputStream，返回值是Object
        //获取对象的控制类，独立控制
        BubbleController bubbleController = loader.getController();

        String contentText = jsonObject.getString("mmsgcontent");
        //判断私聊还是群聊
        if(contentText==null || contentText.length()==0) {
            contentText = jsonObject.getString("gmContent");
            //发送者的昵称
            bubbleController.setFriendName(jsonObject.getString("groupNick"));
        }
        String time = jsonObject.getString("gmCreatetime");
        if(time !=null) {
            bubbleController.setSendTime(time.substring(0,10)+"  "+time.substring(11,19));
        } else {
            time = jsonObject.getString("mcreatetime");
            if(time !=null) {
                bubbleController.setSendTime(time.substring(0,10)+"  "+time.substring(11,19));
            }
        }
        bubbleController.setIsMy(isMy);
        bubbleController.setContent(contentText);

        paneChildren.add(anchorPane);
    }

    /**
     * 添加好友申请
     * @param pane
     * @param jsonObject
     * @throws IOException
     */
    public void addProcessing(VBox pane, JSONObject jsonObject) throws IOException {
        paneChildren = pane.getChildren();
        //添加的对象需要是新的对象，这样才可以单独控制
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());   // 设置BuilderFactory
        String pagePath = "/view/RequestProcess/Processing.fxml";
        loader.setLocation(getClass().getResource(pagePath));	// 设置路径基准
        //用新的组件去把页面“装起来”
        InputStream in = getClass().getResourceAsStream(pagePath);
        AnchorPane anchorPane = (AnchorPane) loader.load(in); // 对象方法的参数是InputStream，返回值是Object
        //获取对象的控制类，独立控制
        ProcessingController processingController = loader.getController();
        processingController.setName(jsonObject.getString("friendName"));
        processingController.setIntro(jsonObject.getString("frIntro"));
        processingController.setCreateTime(jsonObject.getString("frCreatetime"));
        processingController.setFrId(jsonObject.getInteger("frId"));
        processingController.setVbox(pane);
        //进群申请
        if(jsonObject.getString("frGroup") != null && jsonObject.getString("frGroup").length() > 0) {
            processingController.setIsAddFriend("申请进"+jsonObject.getString("groupName"));
        }

        paneChildren.add(anchorPane);
    }

    /**
     * 刷新三级菜单，显示联系人信息
     * @param pane
     * @param jsonObject
     * @throws IOException
     */
    public void tlmFriendInfo(AnchorPane pane, String view, JSONObject jsonObject) throws IOException {
        paneChildren = pane.getChildren();
        //添加的对象需要是新的对象，这样才可以单独控制
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());	// 设置BuilderFactory
        loader.setLocation(getClass().getResource(view));	// 设置路径基准
        //用新的组件去把页面“装起来”
        InputStream in = getClass().getResourceAsStream(view);
        AnchorPane anchorPane = (AnchorPane) loader.load(in); // 对象方法的参数是InputStream，返回值是Object
        if(view.equals(FriendInfo)) {
            FriendInfoController friendInfoController = loader.getController();
            JSONObject FriendInfo = jsonObject.getJSONObject("friendInfo");
            if(FriendInfo != null) {
                friendInfoController.setFid(FriendInfo.getInteger("uid"));
                friendInfoController.setNickName(FriendInfo.getString("unickname"));
                friendInfoController.setAlias(jsonObject.getString("faliasuser"));
                friendInfoController.setSignaTure(FriendInfo.getString("usignature"));
                friendInfoController.setEmail(FriendInfo.getString("uemail"));
                friendInfoController.setLoginID(FriendInfo.getString("uloginid"));
            }
        } else {
            //居中显示
            anchorPane.setLayoutX(108);
            anchorPane.setLayoutY(27);
            //获取对象的控制类，独立控制
            UserInfoController userInfoController = loader.getController();
//            userInfoController.setUserFace();
            userInfoController.setUserName(getStr(jsonObject.getString("unickname")));
            userInfoController.setBirthday(getStr(jsonObject.getString("ubirthday")));
            userInfoController.setTel(getStr(jsonObject.getString("utelephone")));
            userInfoController.setSex(getStr(jsonObject.getString("usex")));
            userInfoController.setLocation(getStr(jsonObject.getString("usex")),getStr(jsonObject.getString("usex")),getStr(jsonObject.getString("usex")));
            userInfoController.setSignaTure(getStr(jsonObject.getString("usignature")));
            userInfoController.setUid(jsonObject.getInteger("uid"));
            userInfoController.setEmail(getStr(jsonObject.getString("uemail")));
        }
        paneChildren.clear();
        paneChildren.add(anchorPane);
    }

    private String getStr(String str) {
        if(str == null) {
            return "";
        }
        return str;
    }

    public void tlmGroupInfo(AnchorPane pane, String view, JSONObject jsonObject) throws IOException {
        paneChildren = pane.getChildren();
        //添加的对象需要是新的对象，这样才可以单独控制
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());	// 设置BuilderFactory
        loader.setLocation(getClass().getResource(view));	// 设置路径基准
        //用新的组件去把页面“装起来”
        InputStream in = getClass().getResourceAsStream(view);
        AnchorPane anchorPane = (AnchorPane) loader.load(in); // 对象方法的参数是InputStream，返回值是Object
        if(view.equals(GroupInfo)) {
            //获取对象的控制类，独立控制
            GroupInfoController groupInfoController = loader.getController();
            JSONObject GroupInfo = jsonObject.getJSONObject("groupsInfo");
            if (GroupInfo == null) {
                GroupInfo = jsonObject;
            }
            groupInfoController.setGroupName(GroupInfo.getString("ugName")+"    （"+GroupInfo.getString("ugGroupNum")+"）");
            groupInfoController.setIntro(GroupInfo.getString("ugIntro"));
            groupInfoController.setGid(GroupInfo.getInteger("ugId"));
            //教师名称
            groupInfoController.setTeacherName(jsonObject.getString("ugtGroupnick"));
        } else {
            //居中显示
            anchorPane.setLayoutX(108);
            anchorPane.setLayoutY(27);
            //获取对象的控制类，独立控制
            GroupInfoTwoController groupInfoTwoController = loader.getController();
            groupInfoTwoController.setIntro(jsonObject.getString("ugIntro"));
            groupInfoTwoController.setGname(jsonObject.getString("ugName"));
            groupInfoTwoController.setTname(jsonObject.getString("adminName"));
            groupInfoTwoController.setGID(jsonObject.getInteger("ugId"));
        }
        paneChildren.clear();
        paneChildren.add(anchorPane);
    }

    public void tlmChatContent(AnchorPane pane, String pagePath, JSONArray jsonArray, String name, String ID, Boolean isGroup) throws IOException {
        paneChildren = pane.getChildren();
        //添加的对象需要是新的对象，这样才可以单独控制
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());	// 设置BuilderFactory
        loader.setLocation(getClass().getResource(pagePath));	// 设置路径基准
        //用新的组件去把页面“装起来”
        InputStream in = getClass().getResourceAsStream(pagePath);
        BorderPane borderPane = (BorderPane) loader.load(in); // 对象方法的参数是InputStream，返回值是Object
        //获取对象的控制类，独立控制
        ChatContentController chatContentController = loader.getController();
        chatContentController.setFriendName(name);
        chatContentController.setFriendID(ID);
        chatContentController.setIsGroup(isGroup);
        chatContentController.setJSON(jsonArray);
        //记录消息显示框，用于收到信息后控制其实时添加内容
        if(isGroup) {
            GlobalParam.paneForGroup.put(Integer.valueOf(ID), chatContentController.getChatContent());
        } else {
            GlobalParam.paneForFriend.put(Integer.valueOf(ID), chatContentController.getChatContent());
        }

        paneChildren.clear();
        paneChildren.add(borderPane);
    }

    public void tlmFriendRequest(AnchorPane pane, String pagePath) throws IOException {
        paneChildren = pane.getChildren();
        //添加的对象需要是新的对象，这样才可以单独控制
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());	// 设置BuilderFactory
        loader.setLocation(getClass().getResource(pagePath));	// 设置路径基准
        //用新的组件去把页面“装起来”
        InputStream in = getClass().getResourceAsStream(pagePath);
        BorderPane borderPane = (BorderPane) loader.load(in); // 对象方法的参数是InputStream，返回值是Object
        //获取对象的控制类，独立控制
        FriendRequestController friendRequestController = loader.getController();

        paneChildren.clear();
        paneChildren.add(borderPane);
    }

    /**
     * 查询好友或者群组的界面
     *
     * @param idNum
     * @param isAddFriend
     * @throws IOException
     */
    public void tlmAddRequest(String idNum, Boolean isAddFriend) throws IOException {
        paneChildren = GlobalParam.homeController.getThreeLevelMenu().getChildren();
        String pagePath = "/view/RequestProcess/AddRequest.fxml";
        //添加的对象需要是新的对象，这样才可以单独控制
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());	// 设置BuilderFactory
        loader.setLocation(getClass().getResource(pagePath));	// 设置路径基准
        //用新的组件去把页面“装起来”
        InputStream in = getClass().getResourceAsStream(pagePath);
        BorderPane borderPane = (BorderPane) loader.load(in); // 对象方法的参数是InputStream，返回值是Object
        //获取对象的控制类，独立控制
        AddRequestController addRequestController = loader.getController();
        addRequestController.setIsAddFriend(isAddFriend);
        addRequestController.setIdNum(idNum);

        paneChildren.clear();
        paneChildren.add(borderPane);
    }
}
