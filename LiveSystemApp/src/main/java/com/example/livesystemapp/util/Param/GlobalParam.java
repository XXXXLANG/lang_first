package com.example.livesystemapp.util.Param;

import com.example.livesystemapp.LiveSystemAppApplication;
import com.example.livesystemapp.controller.ChatPane.ChatViewController;
import com.example.livesystemapp.controller.HomePane.HomeController;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GlobalParam {

    public static Integer UserID;
    public static HomeController homeController;
    public static String LiveGroup = null; //正在观看直播的群，收到的消息发送弹幕，退出观看直播需要改回null
    public static Integer SelectFriendID = null; //选择的好友聊天室
    public static Integer SelectGroupID = null; //选择的群组聊天室

    public static Map<Integer, VBox> paneForFriend = new HashMap<>();
    public static Map<Integer, VBox> paneForGroup = new HashMap<>();
    public static Map<Integer, ChatViewController> controllerForFriend = new HashMap<>();
    public static Map<Integer, ChatViewController> controllerForGroup = new HashMap<>();

    public static LiveSystemAppApplication myApp = new LiveSystemAppApplication();

    public static String liveLocation = "rtmp://192.168.75.129/live/";
    public static Thread threadStatic = null;

    //计算年龄
    public int getAgeByBirth(String birthday) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(format.parse(birthday));

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return 0;
        }
    }
}
