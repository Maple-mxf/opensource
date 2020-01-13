package io.jopen.core.other;

import com.google.common.io.Files;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author maxuefeng
 * @since 2020-01-13
 */
public class CountCodeLine {

   static int num = 0;

    private void count(String basePath) throws IOException {
        File baseFileDir = new File(basePath);
        if (baseFileDir.isDirectory()) {
            File[] files = baseFileDir.listFiles();
            for (File file : files) {
                if (file.getName().endsWith(".java")) {
                    num += Files.readLines(file, StandardCharsets.UTF_8).size();
                } else {
                    count(file.getAbsolutePath());
                }
            }
        } else {
            if (baseFileDir.getName().endsWith(".java")){
                num += Files.readLines(baseFileDir, StandardCharsets.UTF_8).size();
            }
        }
    }

    // 109619 11W+代码  38640 + 80576
    public static void main(String[] args) throws IOException {
        new CountCodeLine().count("E:\\java-workplace\\opensource");
        System.err.println(num);
    }
}
