package com.example.livesystemapp.util.FireCurtain;

import java.io.Serializable;

public class DanContent implements Serializable {  //弹幕管理类
    public static final int TextLarge=70;
    public static final int TextMedium=45;
    public static final int TextSmart=30;

    public static final int RED=0;
    public static final int BLUE=1;
    public static final int BLACK=2;
    public static final int CYAN=3;
    public static final int REDARK_GRAY=4;
    public static final int GREEN=5;
    public static final int ORANGE=6;
    public static final int WHITE=7;
    public static final int PINK=8;

    public static final int BOLD=1;
    public static final int ITALLIC=2;

    public int x=0;
    public int y=0;

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int  color;
    private int size;
    private int style;
    private String text;
    private String senderId;
    private String receiverId;

    public String getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
    public String getSenderId() {
        return senderId;
    }
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    public DanContent(int color, int size, int style, String text) {
        this.color = color;
        this.size = size;
        this.style = style;
        this.text = text;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getStyle() {
        return style;
    }
    public void setStyle(int style) {
        this.style = style;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

}