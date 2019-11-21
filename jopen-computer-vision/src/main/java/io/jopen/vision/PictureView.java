package io.jopen.vision;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author maxuefeng
 * @since 2019/11/21
 */
public class PictureView {

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("E:\\java-workplace\\opensource\\jopen-computer-vision\\img\\demo1.png"))
                .size(1300,2000)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("E:\\java-workplace\\opensource\\jopen-computer-vision\\img\\snackDB.jpg"))
                , 0.5F).toFile(new File("E:\\java-workplace\\opensource\\jopen-computer-vision\\img\\demo3.png"));
    }
}
