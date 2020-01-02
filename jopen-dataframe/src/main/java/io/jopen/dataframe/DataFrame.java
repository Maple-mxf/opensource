package io.jopen.dataframe;

/**
 * @author maxuefeng
 * @since 2019/12/22
 */
public interface DataFrame {

    DataFrame get(String dsl);

    DataFrame remove(String dsl);

    DataFrame map(String dsl);

    DataFrame filter(String dsl);

    DataFrame count(String dsl);
}
