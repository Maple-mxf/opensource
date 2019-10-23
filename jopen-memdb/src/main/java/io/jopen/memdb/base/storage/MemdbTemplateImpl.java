package io.jopen.memdb.base.storage;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.jopen.core.function.ReturnValue;

import java.util.concurrent.ConcurrentHashMap;

import static io.jopen.memdb.base.storage.JavaModelTable.preModifyTableActions;

/**
 * 内存数据库
 *
 * @author maxuefeng
 * @since 2019/10/22
 */
public final
class MemdbTemplateImpl implements MemdbTemplate {

    //
    private Database currentDatabase;

    public ConcurrentHashMap<String, Database> showDBs() {
        return DatabaseManagement.DBA.databases;
    }

    // 客户端登陆
    private MemdbTemplateImpl() {
    }

    // 单例
    private static MemdbTemplateImpl memTemplateInstance = null;

    static MemdbTemplateImpl getInstance() {
        synchronized (MemdbTemplateImpl.class) {
            if (memTemplateInstance == null) {
                memTemplateInstance = new MemdbTemplateImpl();
            }
            return memTemplateInstance;
        }
    }

    public static class Builder {

        public Builder() {
        }

        /**
         * 同步加锁方式  防止数据错误
         *
         * @param dbName 数据库名称
         * @return fluent风格 build
         */
        public synchronized Builder switchDB(String dbName) {
            if (Strings.isNullOrEmpty(dbName)) {
                throw new IllegalArgumentException("database name must not null");
            }

            Database db = DatabaseManagement.DBA.databases.get(dbName);

            if (db == null) {
                db = new Database(dbName);
                DatabaseManagement.DBA.databases.put(dbName, db);
            }
            MemdbTemplateImpl.getInstance().currentDatabase = db;
            return this;
        }

        public MemdbTemplateImpl build() {
            return new MemdbTemplateImpl();
        }
    }

    private OperationType operationType = OperationType.SELECT;

    public MemdbTemplateImpl select() {
        this.operationType = OperationType.SELECT;
        return this;
    }

    public MemdbTemplateImpl delete() {
        this.operationType = OperationType.DELETE;
        return this;
    }

    public MemdbTemplateImpl update() {
        this.operationType = OperationType.UPDATE;
        return this;
    }

    public MemdbTemplateImpl insert() {
        this.operationType = OperationType.INSERT;
        return this;
    }

    // 内部条件构造器
    private IntermediateExpression intermediateExpression;

    public void eq(String name, Object value) {

    }

    public <T> Boolean save(T t) throws Throwable {
        Preconditions.checkNotNull(t);

        JavaModelTable<T> targetTable = null;
        // 执行J修改表格之前的预操作
        for (PreModifyTableAction preAction : preModifyTableActions) {
            ReturnValue returnValue = preAction.prerequisites(getInstance().currentDatabase, t);

            if (returnValue.containsKey(t.getClass().getName())) {
                targetTable = (JavaModelTable<T>) returnValue.get(t.getClass().getName());
            }
        }

        // 程序错误
        if (targetTable == null) {
            throw new RuntimeException("unknow error happen");
        }

        // 保存数据
        return targetTable.add(t);
    }

    @Override
    public <T> void delete(T t) throws Throwable {

        Preconditions.checkNotNull(t);
        JavaModelTable<T> targetTable = null;
        // 执行J修改表格之前的预操作
        for (PreModifyTableAction preAction : preModifyTableActions) {
            ReturnValue returnValue = preAction.prerequisites(this.currentDatabase, t);

            if (returnValue.containsKey(t.getClass().getName())) {
                targetTable = (JavaModelTable<T>) returnValue.get(t.getClass().getName());
            }
        }
        targetTable.delete(t);
    }

    @Override
    public <T> void update(T t) {
        Preconditions.checkNotNull(t);
    }

    @Override
    public <T> void select(T t) {
        Preconditions.checkNotNull(t);
    }
}
