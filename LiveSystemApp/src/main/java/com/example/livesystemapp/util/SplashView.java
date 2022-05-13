package com.example.livesystemapp.util;

import de.felixroske.jfxsupport.SplashScreen;
import javafx.scene.Group;
import javafx.scene.Parent;

public class SplashView extends SplashScreen {
    @Override
    public String getImagePath() {
        // 其实这里return什么都没有影响，关键是getParent方法
        return "";
    }

    // 返回闪屏的界面
    @Override
    public Parent getParent() {
        Parent gp = new Group();
        // 自己写闪屏然后return回去
        return gp;
    }
}
