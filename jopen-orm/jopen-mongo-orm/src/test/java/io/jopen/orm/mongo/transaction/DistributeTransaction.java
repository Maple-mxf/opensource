package io.jopen.orm.mongo.transaction;

import com.mongodb.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.DatastoreImpl;
import dev.morphia.Morphia;
import org.junit.Before;

/**
 * 主要结合事务进行整合ORM框架
 *
 * @author maxuefeng
 * @see dev.morphia.Morphia
 * @since 2019/10/17
 */
public class DistributeTransaction {
    private Datastore datastore = null;
    private MongoClient client = null;
    private Morphia morphia = null;

    @Before
    public void before() {
        client = new MongoClient("192.168.74.136");
        morphia = new Morphia();
        datastore = new DatastoreImpl(morphia, client, "nonStand");
    }

    public void startTransaction() {

    }
}
