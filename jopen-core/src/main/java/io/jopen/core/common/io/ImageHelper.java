package io.jopen.core.common.io;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author maxuefeng [m17793873123@163.com]
 */
public class ImageHelper {

    /**
     * 常见的图片格式
     */
    private static final Set<String> IMAGE_TYPE = new HashSet<>();

    static {

        IMAGE_TYPE.add("png");
        IMAGE_TYPE.add("PNG");

        IMAGE_TYPE.add("jpg");
        IMAGE_TYPE.add("JPG");

        IMAGE_TYPE.add("jpeg");
        IMAGE_TYPE.add("JPEG");

        IMAGE_TYPE.add("SVG");
        IMAGE_TYPE.add("svg");

        IMAGE_TYPE.add("SWF");
        IMAGE_TYPE.add("swf");

        IMAGE_TYPE.add("ico");
        IMAGE_TYPE.add("ICO");

        IMAGE_TYPE.add("gif");
        IMAGE_TYPE.add("GIF");
    }


    /**
     * 裁剪图片方法
     *
     * @param bufferedImage 图像源
     * @param startX        裁剪开始x坐标  0-1065
     * @param startY        裁剪开始y坐标  56-1918
     * @param endX          裁剪结束x坐标
     * @param endY          裁剪结束y坐标
     * @return BufferedImage
     */
    public static BufferedImage cropImage(BufferedImage bufferedImage, int startX, int startY, int endX, int endY) {

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        if (startX == -1) {
            startX = 0;
        }

        if (startY == -1) {
            startY = 0;
        }

        if (endX == -1) {
            endX = width - 1;
        }

        if (endY == -1) {
            endY = height - 1;
        }

        BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);

        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                int rgb = bufferedImage.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }

        return result;
    }

    /**
     * 将图片进行灰度化 为了方便tesseract识别
     *
     * @param inPath     输入文件
     * @param outPath    输出文件
     * @param formatName 图片文件格式  截图必须为png 其他一般为jpg或者jpeg
     * @throws IOException 读取文件异常
     */
    /**
     * 将图片进行灰度化 为了方便tesseract识别
     *
     * @param image 输入图片
     * @return 灰度图片
     */
    public static BufferedImage grayImage(BufferedImage image) {

        int width = image.getWidth();

        int height = image.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY); //重点，技巧在这个参数BufferedImage.TYPE_BYTE_GRAY

        for (int i = 0; i < width; i++) {

            for (int j = 0; j < height; j++) {

                int rgb = image.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }

        return grayImage;
    }

    /**
     * 两个像素点是否颜色相近
     *
     * @param pixel1
     * @param pixel2
     * @return
     * @throws IOException
     */
    public static boolean isSimilar(int pixel1, int pixel2) {

        int[] rgb1 = new int[3];
        rgb1[0] = (pixel1 & 0xff0000) >> 16;
        rgb1[1] = (pixel1 & 0xff00) >> 8;
        rgb1[2] = (pixel1 & 0xff);

        int[] rgb2 = new int[3];
        rgb2[0] = (pixel2 & 0xff0000) >> 16;
        rgb2[1] = (pixel2 & 0xff00) >> 8;
        rgb2[2] = (pixel2 & 0xff);

        if (Math.abs(rgb1[0] - rgb2[0]) > 10 && Math.abs(rgb1[1] - rgb2[1]) > 10 && Math.abs(rgb1[2] - rgb2[2]) > 10) {
            return true;
        }

        return false;
    }

    /**
     * 比较两个图片是否相同
     *
     * @param bi1
     * @param bi2
     * @return
     * @throws IOException
     */
    public static boolean isSame(BufferedImage bi1, BufferedImage bi2) {

        // 两个图片长宽不相等 返回 false
        if (bi1.getHeight() != bi2.getHeight() || bi1.getWidth() != bi2.getWidth()) {
            return false;
        }

        int unlikeCount = 0;

        for (int x = 0; x < bi1.getWidth(); x++) {

            for (int y = 0; y < bi1.getHeight(); y++) {

                int pixel1 = bi1.getRGB(x, y);
                int pixel2 = bi2.getRGB(x, y);

                if (isSimilar(pixel1, pixel2)) {
                    unlikeCount++;
                }
            }
        }

        if ((double) unlikeCount / (bi1.getWidth() * bi1.getHeight()) > 0.01) return false;

        return true;
    }

    /**
     * 图片到byte数组
     *
     * @param path file relative or absolute path; path must contain file suffix;
     * @return byte array
     */
    public static byte[] image2Byte(String path) {

        byte[] data = null;

        FileImageInputStream input;

        try {

            input = new FileImageInputStream(new File(path));

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buf = new byte[1024];

            int numBytesRead;

            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();

            output.close();

            input.close();

        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    /**
     * byte数组到图片
     *
     * @param data string to byte
     * @param path file will be apply path; path must contain file suffix(file type)
     */
    public static void byte2Image(byte[] data, String path) {
        if (data.length < 3 || path.equals("")) return;
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));

            imageOutput.write(data, 0, data.length);

            imageOutput.close();

            System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    public static BufferedImage bytes2Image(byte[] data) throws IOException {
        InputStream in = new ByteArrayInputStream(data);
        return ImageIO.read(in);
    }

    /**
     * byte数组到16进制字符串
     *
     * @param data byte data
     * @return string
     */
    public static String byte2String(byte[] data) {

        if (data == null || data.length <= 1) return "0x";

        if (data.length > 200000) return "0x";

        StringBuilder sb = new StringBuilder();

        int[] buf = new int[data.length];

        //byte数组转化成十进制
        for (int k = 0; k < data.length; k++) {
            buf[k] = data[k] < 0 ? (data[k] + 256) : (data[k]);
        }
        //十进制转化成十六进制
        for (int i : buf) {

            if (i < 16) sb.append("0").append(Integer.toHexString(i));
            else sb.append(Integer.toHexString(i));
        }
        return "0x" + sb.toString().toUpperCase();
    }


    /**
     * 获取网络图片
     *
     * @param imgUrl
     * @return
     * @throws IOException
     */
    public static BufferedImage getNetImage2BufferedImage(String imgUrl) throws IOException {

        // 判断是否为图片
        String[] splits = imgUrl.split("\\.");

        URL url = new URL(imgUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        conn.setConnectTimeout(5 * 1000);

        // 通过输入流获取图片数据
        InputStream is = conn.getInputStream();

        BufferedImage bufferedImage = ImageIO.read(is);

        is.close();

        return bufferedImage;
    }

    /**
     * 将一张本地图片转化成Base64字符串 *
     *
     * @param imgPath 本地图片地址
     *                *
     * @return 图片转化base64后再UrlEncode结果
     */

    public static String image2StringEncodeBased64(String imgPath) throws IOException {
        InputStream in = new FileInputStream(imgPath);
        return image2StringEncodeBased64(in);
    }


    public static String image2StringEncodeBased64(InputStream in) throws IOException {

        byte[] data = new byte[in.available()];
        in.read(data);

        in.close();

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();


        // 返回Base64编码过再URLEncode的字节数组字符串
        return encoder.encode(data);
    }

    public static BufferedImage string2Image(String imgData) throws IOException {

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imgBytes = decoder.decodeBuffer(imgData);
        return ImageHelper.bytes2Image(imgBytes);
    }

}
