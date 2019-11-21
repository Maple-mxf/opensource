package io.jopen.vision;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 调整图像对比度
 *
 * @author maxuefeng
 * @since 2019/11/21
 */
public class PictureContrast {

    //图片对比度调整
    public static BufferedImage contrast(BufferedImage bufferedImage, int contrast) {
        try {
            int contrast_average = 128;
            // 创建一个不带透明度的图片
            BufferedImage back = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            int pix;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixel = bufferedImage.getRGB(j, i);
                    Color color = new Color(pixel);

                    if (color.getRed() < contrast_average) {
                        pix = color.getRed() - Math.abs(contrast);
                        if (pix < 0) pix = 0;
                    } else {
                        pix = color.getRed() + Math.abs(contrast);
                        if (pix > 255) pix = 255;
                    }
                    int red = pix;
                    if (color.getGreen() < contrast_average) {
                        pix = color.getGreen() - Math.abs(contrast);
                        if (pix < 0) pix = 0;
                    } else {
                        pix = color.getGreen() + Math.abs(contrast);
                        if (pix > 255) pix = 255;
                    }
                    int green = pix;
                    if (color.getBlue() < contrast_average) {
                        pix = color.getBlue() - Math.abs(contrast);
                        if (pix < 0) pix = 0;
                    } else {
                        pix = color.getBlue() + Math.abs(contrast);
                        if (pix > 255) pix = 255;
                    }
                    int blue = pix;

                    color = new Color(red, green, blue);
                    int x = color.getRGB();
                    back.setRGB(j, i, x);
                }
            }
            return back;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
