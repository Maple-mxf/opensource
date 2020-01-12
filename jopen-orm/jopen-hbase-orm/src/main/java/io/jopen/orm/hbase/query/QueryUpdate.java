package io.jopen.orm.hbase.query;


import io.jopen.orm.hbase.query.criterion.Criterion;

import java.util.List;

public interface QueryUpdate<T> {

    List<String> getSelectedFields();

    Criterion getCriteria();

    QueryOperationType getQueryOperationType();

    T getEntity();

}
