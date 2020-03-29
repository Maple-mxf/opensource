package io.jopen.db.mongo.transaction;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

/**
 * 多文档事务测试
 * https://www.zybuluo.com/mdeditor#1622459-full-reader
 *
 * @author maxuefeng
 * @since 2019/10/16
 */
public class DistributeTransactionTest {

    private String uri = "mongodb://192.168.74.143:27018,192.168.74.143:27019,192.168.74.143:27019";
    private ConnectionString connectionString = new ConnectionString(uri);

    private MongoClient client = null;

    @Before
    public void before() {
        client = MongoClients.create(connectionString);
    }

    /**
     * For a replica set, include the replica set name and a seedlist of the members in the URI string; e.g.
     * String uri = "mongodb://mongodb0.example.com:27017,mongodb1.example.com:27017/admin?replicaSet=myRepl";
     * For a sharded cluster, connect to the mongos instances; e.g.
     * String uri = "mongodb://mongos0.example.com:27017,mongos1.example.com:27017:27017/admin";
     */
    @Test
    public void testTransaction() {

        client.getDatabase("mydb1").getCollection("foo").withWriteConcern(WriteConcern.MAJORITY).insertOne(new Document("abc", 0));
        client.getDatabase("mydb2").getCollection("bar").withWriteConcern(WriteConcern.MAJORITY).insertOne(new Document("xyz", 0));

        final ClientSession clientSession = client.startSession();

        TransactionOptions txnOptions = TransactionOptions.builder()
                .readPreference(ReadPreference.primary())
                .readConcern(ReadConcern.LOCAL)
                .writeConcern(WriteConcern.MAJORITY)
                .build();

        TransactionBody txnBody = (TransactionBody<String>) () -> {

            // 创建集合
            MongoCollection<Document> coll1 = client.getDatabase("mydb1").getCollection("foo");
            MongoCollection<Document> coll2 = client.getDatabase("mydb2").getCollection("bar");

            coll1.insertOne(clientSession, new Document("abc", 1));
            // 第一条数据插入成功
            System.err.println("data  insert success");

            // 抛出运行时异常
            double a = 10 / 0;

            // 如果上一条数据插入成功 下一条数据插入失败，事务需要回滚
            coll2.insertOne(clientSession, new Document("xyz", 999));
            return "Inserted into collections in different databases";
        };
        try {
            clientSession.withTransaction(txnBody, txnOptions);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            clientSession.close();
        }
    }
}
