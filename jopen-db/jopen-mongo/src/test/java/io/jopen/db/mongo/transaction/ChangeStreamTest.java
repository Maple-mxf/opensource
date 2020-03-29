package io.jopen.db.mongo.transaction;

import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * @author maxuefeng
 * @since 2020/3/29
 */
public class ChangeStreamTest {

    private String uri = "mongodb://192.168.74.143:27018,192.168.74.143:27019,192.168.74.143:27019";
    private ConnectionString connectionString = new ConnectionString(uri);

    private MongoClient client = null;

    private final String db = "change_stream";
    private final String collection = "watch_collection";

    @Before
    public void before() {
        client = MongoClients.create(connectionString);
    }


    @Test
    public void createDatabaseAndCollection() {
        MongoDatabase database = client.getDatabase(db);
        database.createCollection(collection);
    }

    @Test
    public void testWatchCollection() {
        ChangeStreamIterable<Document> changeStreamDocuments
                = client.getDatabase(db).getCollection(collection)
                .watch();

        // 打开游标
        MongoChangeStreamCursor<ChangeStreamDocument<Document>> cursor = changeStreamDocuments.cursor();
        while (cursor.hasNext())
            System.err.println(cursor.next().getFullDocument());

        cursor.close();
    }

    @Test
    public void addDataToCollection() {
        Document doc = new Document();
        doc.put("name", "opensource");
        doc.put("age", 20);

        client.getDatabase(db).getCollection(collection)
                .insertOne(doc);
    }

    @Test
    public void testPipelineWatch() {
        List<Bson> pipeline = Collections.singletonList(Aggregates.match(Filters.or(
                Document.parse("{'fullDocument.name': 'opensource'}"),
                Filters.eq("fullDocument.age", 20))));
        MongoChangeStreamCursor<ChangeStreamDocument<Document>> cursor =
                client.getDatabase(db).getCollection(collection).watch(pipeline).cursor();
        while (cursor.hasNext())
            System.err.println(cursor.next().getFullDocument());

        cursor.close();
    }

}
