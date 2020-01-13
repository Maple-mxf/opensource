package io.jopen.core.common.proxology;

import io.jopen.core.common.proxology.reflection.TypeInfo;
import org.junit.Test;

import java.util.List;

/**
 * 测试{@link io.jopen.core.common.proxology.reflection.TypeInfo}
 *
 * @author maxuefeng
 * @since 2020-01-13
 */
public class TypeInfoTest {

    @Test
    public void testBaseApi(){
        TypeInfo typeInfo = TypeInfo.forType(List.class);
        System.err.println(typeInfo.getArrayComponentType());
    }


}
