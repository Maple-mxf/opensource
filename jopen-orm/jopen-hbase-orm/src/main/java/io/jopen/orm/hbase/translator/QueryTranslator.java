package io.jopen.orm.hbase.translator;

import io.jopen.orm.hbase.query.QuerySelect;
import io.jopen.orm.hbase.query.QueryUpdate;

public interface QueryTranslator<Q, O, P> {

    public <T, R> Q translate(QuerySelect<T, R> query);

    public <T, R> O translateOrder(QuerySelect<T, R> query);

    public <T, R> P translateProjection(QuerySelect<T, R> query);

    public <T> Q translate(QueryUpdate<T> updateQuery);

}
