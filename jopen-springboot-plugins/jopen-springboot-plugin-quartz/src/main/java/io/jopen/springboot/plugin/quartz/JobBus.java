package io.jopen.springboot.plugin.quartz;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * 此接口实现类需要返回一个Job的Class类的集合  用于自动化的装配
 *
 * @author maxuefeng
 * @since 2020/1/31
 */
@FunctionalInterface
@Deprecated
public interface JobBus {

    @NonNull
    List<Class<? extends JobBeanAgent>> applyJobBean();


}
