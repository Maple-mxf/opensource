package io.jopen.memdb.base.storage;

import com.google.common.util.concurrent.AbstractService;
import io.jopen.core.common.io.FileHelper;
import io.jopen.memdb.base.serialize.KryoHelper;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 * @see com.google.common.util.concurrent.Service
 * @see AbstractService
 * {@link DatabaseManagement#DBA 初始化DBA信息 }
 * @since 2019/10/23
 */
final class MemdbServer extends AbstractService {

    private MemdbServer() {
    }

    // 初始化
    static final MemdbServer MEMDB_SERVER = new MemdbServer();

    /**
     * 同步启动方式
     */
    @Override
    protected void doStart() {
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

                    Database db = new Database(dbName);
                    File[] tableFiles = dbFile.listFiles();

                    if (tableFiles != null) {
                        Stream.of(tableFiles).filter(File::isFile).forEach(tf -> {
                            String className = new String(Base64.getDecoder().decode(tf.getName()));
                            try {
                                Class targetClass = Class.forName(className);
                                // 反序列化
                                JavaModelTable table = (JavaModelTable) KryoHelper.deserialization(FileHelper.readAllLines(tf.getAbsolutePath()), targetClass);
                                // 加入对应数据库
                                db.tables.put(table.getTableName(), table);
                                // 异常忽略
                            } catch (ClassNotFoundException | ClassCastException ignored) {
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    DatabaseManagement.DBA.databases.put(dbName, db);
                });
            }
        }
    }

    @Override
    protected void doStop() {
        // 将临时文件进行落盘
    }
}
