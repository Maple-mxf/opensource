package core.common.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author maxuefeng
 */
public class IOHelper {

    /**
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] inputStreamToBytes(InputStream in) throws IOException {
        if (in == null) {
            return new byte[0];
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = in.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }
        output.flush();
        return output.toByteArray();
    }

    public static BufferedImage bytesToBufferImage(byte[] img_src) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(img_src);
        return ImageIO.read(bais);
    }


    public static void inputStream2File(InputStream ins, File destFile) throws IOException {
        OutputStream os = new FileOutputStream(destFile);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }
}
