package com.example.livesystemapp.util.FireCurtain;

import java.util.Scanner;

public class FireCurtainUtil {

    public static void sendFireCurtain(String str) {
        DanmuNew danmuNew=DanmuNew.getInstance();
        new Thread(() -> {
            DanContent content=new DanContent(DanContent.ORANGE, 70, DanContent.BOLD, str);
            danmuNew.add(content);
        }).start();
    }
}
