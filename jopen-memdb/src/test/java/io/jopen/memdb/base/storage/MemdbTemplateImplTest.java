package io.jopen.memdb.base.storage;

import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/10/23
 */
public class MemdbTemplateImplTest {

    @Test
    public void testGetInstance() {

        // 创建单例
        MemdbTemplateImpl memTemplateInstance = new MemdbTemplateImpl.Builder().switchDB("default").build();

        memTemplateInstance.save();

        memTemplateInstance.delete();

        memTemplateInstance.update();

        memTemplateInstance.select();
    }
}
