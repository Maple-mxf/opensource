package io.jopen.db.mongo.api;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/8/25
 */
public class SimpleTest {

    MongoClient client = null;
    MongoDatabase database = null;
    MongoCollection<Document> collection = null;

    /**
     * {@link ConnectionString}
     * mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database.collection][?options]]
     */
    @Before
    public void before() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://192.168.74.135:27017/nonStand"))
                .build();
        client = MongoClients.create(settings);
        database = client.getDatabase("nonStand");
        collection = database.getCollection("canvas-collection");
    }

    @Test
    public void testSave() {
        //
        Document canvas = new Document("item", "canvas").append("qty", 100).append("tags", "cotton");
        collection.insertOne(canvas);
    }

    @Test
    public void testDelete() {
        DeleteResult deleteResult = collection.deleteOne(Filters.eq("item", "canvas"));
        System.err.println(deleteResult);
    }

    @Test
    public void testQuery() {
        FindIterable<Document> documents = collection.find(Filters.eq("item", "canvas"));
        for (Document document : documents) System.err.println(document);
    }

    @Test
    public void testUpdate(){
        // 构建修改的doc
        Document updateDoc = new Document();
        updateDoc.append("$set", new Document().append("item", "java canvas"));
        // 过去查询的doc
        Document searchQueryDoc = new Document().append("item", "canvas");
        UpdateResult updateResult = collection.updateOne(searchQueryDoc, updateDoc);
        System.err.println(updateResult);
    }
}
