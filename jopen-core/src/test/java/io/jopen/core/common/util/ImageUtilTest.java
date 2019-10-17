package io.jopen.core.common.util;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.jopen.core.common.io.ImageHelper;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author maxuefeng [github idVal:ubuntu-maxfeng 163email:m17793873123@163.com]
 */
public class ImageUtilTest {

    @Test
    public void testImageConvert() {

        byte[] bytes = ImageHelper.image2Byte("/usr/local/java-workplace/jopen-common/src/main/resources/tmp.jpeg");

        ImageHelper.byte2Image(bytes, "/usr/local/java-workplace/jopen-common/src/main/resources/tmp22.jpeg");

        BiMap<String, Integer> userId = HashBiMap.create();

        String userForId = userId.inverse().get(1);

        Function<String, String> function = new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String input) {
                return null;
            }
        };

        /*Stream.of("Hello").map(new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String input) {
                return input == null ? input : input.toUpperCase();
            }
        }).reduce((BiFunction<String, Object>) (o, o2) -> null);*/
    }

    @Test
    public void testGrayImage() throws IOException {

        String filePath = "C:\\Users\\EDZ\\Desktop\\1.png";

        File file = new File(filePath);

        BufferedImage bufferedImage = ImageHelper.grayImage(ImageIO.read(file));

        ImageIO.write(bufferedImage, "png", file);
    }

    @Test
    public void testReadOCRResult() throws IOException {
        String filePath = "C:\\Users\\EDZ\\Desktop\\模型训练\\1.txt";
        File file = new File(filePath);

        List<String> list = Files.readAllLines(file.toPath());

        List<String> stringList = list.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());

        System.err.println(JSON.toJSONString(stringList));
    }
}
