package io.jopen.memdb.base.storage;

import io.jopen.core.common.io.FileHelper;
import io.jopen.memdb.base.serialize.KryoHelper;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 * @since 2019/10/23
 * {@link DatabaseManagement#DBA 初始化DBA信息 }
 */
final class MemdbServer {

    private MemdbServer() {
    }

    // 初始化
    public static final MemdbServer MEMDB_SERVER = new MemdbServer();

    static {
        File file = new File("./memdb");
        if (file.exists()) {

            File[] files = file.listFiles();

            if (files != null) {

                Stream.of(files).filter(File::isDirectory).forEach(f -> {

                    // 获取数据库名称
                    String dbName = f.getName();

                    //
                    String dbPath = "./memdb/" + dbName;

                    File dbFile = new File(dbPath);

                    File[] tableFiles = dbFile.listFiles();

                    if (tableFiles != null) {
                        Stream.of(tableFiles).filter(File::isFile).forEach(tf -> {

                            // 反序列化
                            try {
                                KryoHelper.deserialization(FileHelper.readAllLines(tf.getAbsolutePath()), )
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
            }
        }


    }
}
