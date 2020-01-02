package io.jopen.core.common.generics;

import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maxuefeng
 * @since 2019/11/30
 */
public class JavaGenerics {

    @Test
    public void generics() {
        List list = new LinkedList();
        list.add(new Integer(1));
        Integer var = (Integer) list.iterator().next();
        System.err.println(var);
    }

    public void generics1(List<?> list) {
        Collection<?> collection = Collections.emptyList();
        Collection<Object> collection1 = Collections.emptyList();
    }

    @SafeVarargs
    public final <T> T generics2(T... ts) {
        return ts[0];
    }

    public <T> List<T> genericMethod(List<T> list) {
        return list.stream().collect(Collectors.toList());
    }

    class Mapper<T> {

        private TypeToken<Mapper<T>> mapperTypeToken;

        public Mapper(TypeToken<Mapper<T>> mapperTypeToken) {
            this.mapperTypeToken = mapperTypeToken;
        }

        public Object map() {
            return mapperTypeToken.resolveType(this.getClass().getTypeParameters()[0]);
        }
    }

    @Test
    public void testTypeToken() {
        Mapper<String> mapper = new Mapper<>(new TypeToken<Mapper<String>>() {
        });

        System.err.println(mapper.map());
    }


}
