package com.example.livesystemapp.util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ChangeImgColorUtil {

    /**
     * 根据传入类型变化图片像素
     */
    public static Image pixWithImage(int type, Image image){
        PixelReader pixelReader = image.getPixelReader();
        if(image.getWidth()>0 && image.getHeight() >0){
            WritableImage wImage;
            wImage = new WritableImage(
                    (int)image.getWidth(),
                    (int)image.getHeight());
            PixelWriter pixelWriter = wImage.getPixelWriter();

            for(int y = 0; y < image.getHeight(); y++){
                for(int x = 0; x < image.getWidth(); x++){
                    Color color = pixelReader.getColor(x, y);
                    switch (type) {
                        case 1:
                            // 颜色变轻
                            color = color.brighter();
                            break;
                        case 2:
                            // 颜色变深
                            color = color.darker();
                            break;
                        case 3:
                            // 灰度化
                            color = color.grayscale();
                            break;
                        case 4:
                            // 颜色反转
                            color = color.invert();
                            break;
                        case 5:
                            // 颜色饱和
                            color = color.saturate();
                            break;
                        case 6:
                            // 颜色不饱和
                            color = color.desaturate();
                            break;
                        case 7:
                            // 颜色灰度化后反转（字黑体，背景鲜亮，可用于强字弱景）
                            color = color.grayscale();
                            color = color.invert();
                            break;
                        default:
                            break;
                    }

                    pixelWriter.setColor(x, y, color);
                }
            }
            return wImage;
        }
        return null;
    }

}
