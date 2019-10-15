package core.common;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @author maxuefeng
 */
public class Util {

    /**
     * @param object
     * @param strictly 是否严格校验
     * @return
     */
    public static boolean isEmpty(Object object, boolean strictly) {

        if (object == null) {
            return true;
        }

        if (object instanceof String) {
            return StringUtils.isBlank(object.toString());
        }

        if (strictly) {

            // 集合类型
            if (object instanceof Collection) {
                Collection c = (Collection) object;
                return c.size() != 0;
            }

            // 数组对象
            if (object instanceof Object[]) {
                Object[] o = (Object[]) object;
                return o.length != 0;
            }

            // 自定义对象[自定义对象需要判断指定的字段不可为空]

        }
        return true;
    }


}
