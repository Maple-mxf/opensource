package io.jopen.db.mongo.api;

import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import dev.morphia.Datastore;
import dev.morphia.DatastoreImpl;
import dev.morphia.Key;
import dev.morphia.Morphia;
import dev.morphia.aggregation.Group;
import dev.morphia.aggregation.Projection;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import dev.morphia.query.Query;
import dev.morphia.query.internal.MorphiaCursor;
import io.jopen.core.common.text.StringHelper;
import io.jopen.core.common.text.Worker;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/10/12
 */
public class MorphiaAPISimpleTest {

    @Entity("person")
    @Getter
    @Setter
    @ToString
    static class Person implements Serializable {
        @Id
        String id;
        @Property
        String name;
        @Property
        LocalDateTime birth;
        @Property
        Integer age;
    }

    Datastore datastore = null;
    MongoClient client = null;
    Morphia morphia = null;

    @Before
    public void before() {
        client = new MongoClient("192.168.74.136");
        morphia = new Morphia();
        datastore = new DatastoreImpl(morphia, client, "nonStand");
    }

    @Test
    public void testQuery() {
        Query<Person> query = datastore.createQuery(Person.class);
        query.filter("age >= ", 10);
        MorphiaCursor<Person> morphiaCursor = query.find();

        morphiaCursor.forEachRemaining(System.err::println);
    }

    @Test
    public void testUpdate() {
        // "632506113141149696"
        Query<Person> query = datastore.createQuery(Person.class);
        query.filter("id =", "632506113141149696");
        // datastore.update(query,);
        // datastore.update(query)
    }

    @Test
    public void testSave() {
        Person person = new Person();
        person.setId(Worker.id());
        person.setAge(19);
        person.setBirth(LocalDateTime.now());
        person.setName("jack ma");

        Key<Person> personKey = datastore.save(person);
        System.err.println(personKey);
    }

    @Test
    public void testDelete() {
        Person person = new Person();
        person.setId("632502582027784192");
        WriteResult writeResult = datastore.delete(person);
        System.err.println(writeResult);
    }

    @Test
    public void testBatchSave() {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Person person = new Person();
            person.setId(Worker.id());
            person.setAge(RandomUtils.nextInt(10, 100));
            person.setBirth(LocalDateTime.now());
            person.setName(StringHelper.randomString(10));

            people.add(person);
        }

        Iterable<Key<Person>> saveRes = datastore.save(people);
        System.err.println(saveRes);
    }

    @Test
    public void testAggregation() {
        Iterator<Person> aggregationRes = datastore.createAggregation(Person.class)
                .group(Group.grouping("name", Projection.projection("age")))
                .out(Person.class);

        aggregationRes.forEachRemaining(System.err::println);
    }
}
