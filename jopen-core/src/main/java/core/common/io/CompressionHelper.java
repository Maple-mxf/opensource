package core.common.io;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片压缩
 *
 * @author maxuefeng
 */
public final class CompressionHelper {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        CompressionHelper CompressionHelper = new CompressionHelper();
        // CompressionHelper.zoomAsProportion("1.jpg");

    }

    /**
     * 指定大小进行缩放
     * <p>
     * <p>
     * <p>
     * size(width,height) 若图片横比200小，高比300小，不变
     * E:\workplace\jopen-core\tmp
     *
     * @throws IOException
     */
    public static BufferedImage zoomAtSize(String filePath,int width, int height) throws IOException {

        //  若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
       return Thumbnails.of(filePath).size(width, height).asBufferedImage();
    }

    /**
     * 按照比例进行缩放
     * <p>
     * scale(比例)
     *
     * @throws IOException  io
     */
    public static BufferedImage zoomAsProportion(String filePath, double proportion) throws IOException {
        return Thumbnails.of(filePath).scale(proportion).asBufferedImage();
    }

    /**
     * 不按照比例，指定大小进行缩放
     * <p>
     * keepAspectRatio(false) 默认是按照比例缩放的
     *
     * @throws IOException
     */
    public static void test3(String filePath) throws IOException {
        Thumbnails.of(filePath).size(120, 120).keepAspectRatio(false).toFile("E:\\workplace\\jopen-core\\tmp\\image_120x120.jpg");
    }

    /**
     * 旋转
     *
     * @throws IOException
     */
    public static void test4(String filePath) throws IOException {
        /**
         * rotate(角度),正数：顺时针 负数：逆时针
         */
        Thumbnails.of(filePath).size(1280, 1024).rotate(90).toFile("C:/image+90.jpg");
        Thumbnails.of(filePath).size(1280, 1024).rotate(-90).toFile("C:/iamge-90.jpg");
    }

    /**
     * 水印
     *
     * @throws IOException
     */
    public static void test5(String filePath) throws IOException {
        /**
         * watermark(位置，水印图，透明度)
         */
        Thumbnails.of(filePath).size(1280, 1024).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("images/watermark.png")), 0.5f)
                .outputQuality(0.8f).toFile("C:/image_watermark_bottom_right.jpg");
        Thumbnails.of(filePath).size(1280, 1024).watermark(Positions.CENTER, ImageIO.read(new File("images/watermark.png")), 0.5f)
                .outputQuality(0.8f).toFile("C:/image_watermark_center.jpg");
    }

    /**
     * 裁剪
     *
     * @throws IOException
     */
    public static void test6(String filePath) throws IOException {
        /**
         * 图片中心400*400的区域
         */
        Thumbnails.of(filePath).sourceRegion(Positions.CENTER, 400, 400).size(200, 200).keepAspectRatio(false)
                .toFile("C:/image_region_center.jpg");
        /**
         * 图片右下400*400的区域
         */
        Thumbnails.of(filePath).sourceRegion(Positions.BOTTOM_RIGHT, 400, 400).size(200, 200).keepAspectRatio(false)
                .toFile("C:/image_region_bootom_right.jpg");
        /**
         * 指定坐标
         */
        Thumbnails.of(filePath).sourceRegion(600, 500, 400, 400).size(200, 200).keepAspectRatio(false).toFile("C:/image_region_coord.jpg");
    }

    /**
     * 转化图像格式
     *
     * @throws IOException
     */
    public static void test7(String filePath) throws IOException {
        /**
         * outputFormat(图像格式)
         */
        Thumbnails.of(filePath).size(1280, 1024).outputFormat("png").toFile("C:/image_1280x1024.png");
        Thumbnails.of(filePath).size(1280, 1024).outputFormat("gif").toFile("C:/image_1280x1024.gif");
    }

    /**
     * 输出到OutputStream
     *
     * @throws IOException
     */
    public static void test8(String filePath) throws IOException {
        /**
         * toOutputStream(流对象)
         */
        OutputStream os = new FileOutputStream("C:/image_1280x1024_OutputStream.png");
        Thumbnails.of(filePath).size(1280, 1024).toOutputStream(os);
    }

    /**
     * 输出到BufferedImage
     * <p>
     * asBufferedImage() 返回BufferedImage
     *
     * @throws IOException
     */
    public static void test9(String filePath) throws IOException {

        BufferedImage thumbnail = Thumbnails.of(filePath).size(1280, 1024).asBufferedImage();
        ImageIO.write(thumbnail, "jpg", new File("C:/image_1280x1024_BufferedImage.jpg"));
    }
}
