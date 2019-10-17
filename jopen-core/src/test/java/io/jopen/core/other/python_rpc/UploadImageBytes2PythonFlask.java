package io.jopen.core.other.python_rpc;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import io.jopen.core.common.io.UploadHelper;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


/**
 * @author maxuefeng
 */
public class UploadImageBytes2PythonFlask {

    @Test
    public void testSimpleUpload() throws IOException {

        BufferedImage image = ImageIO.read(new File("1.jpg"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ImageIO.write( image,"jpg",out);

        JSONObject res = UploadHelper.upload("http://127.0.0.1:9090/upload", out.toByteArray(), ImmutableMap.of());

        System.err.println(res);
    }
}
