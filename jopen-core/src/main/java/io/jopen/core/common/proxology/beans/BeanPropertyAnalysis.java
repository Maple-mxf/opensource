package io.jopen.core.common.proxology.beans;

import io.jopen.core.common.proxology.Nonchalantly;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * @author maxuefeng
 */
public final class BeanPropertyAnalysis {

    /**
     * @param iFace
     * @return
     */
    public static BeanPropertyAnalysis forClass(Class<?> iFace) {
        BeanInfo beanInfo = Nonchalantly.invoke(() -> Introspector.getBeanInfo(iFace));
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        return new BeanPropertyAnalysis(descriptors);
    }

    private final PropertyDescriptor[] descriptors;

    public BeanPropertyAnalysis(PropertyDescriptor[] descriptors) {
        this.descriptors = descriptors;
    }

    public String[] getPropertyNames() {
        return Stream.of(descriptors)
                .map(PropertyDescriptor::getName)
                .toArray(String[]::new);
    }

    public Map<Method, Integer> getGetterIndices() {
        return indicesForMethods(PropertyDescriptor::getReadMethod);
    }

    public Map<Method, Integer> getSetterIndices() {
        return indicesForMethods(PropertyDescriptor::getWriteMethod);
    }

    private Map<Method, Integer> indicesForMethods(Function<PropertyDescriptor, Method> methodSelector) {
        return IntStream.range(0, descriptors.length)
                .collect(HashMap::new,
                        (m, i) -> m.put(methodSelector.apply(descriptors[i]), i),
                        Map::putAll);
    }
}
