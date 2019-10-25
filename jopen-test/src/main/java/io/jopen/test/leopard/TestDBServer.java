package io.jopen.test.leopard;

import io.jopen.memdb.base.storage.client.IntermediateExpression;
import io.jopen.memdb.base.storage.client.LeopardClient;

import java.util.Collection;
import java.util.UUID;

/**
 * @author maxuefeng
 * @since 2019/10/25
 */
public class TestDBServer {

    private static LeopardClient client = new LeopardClient.Builder().startDBServer().build();

    public static void main(String[] args) throws Throwable {
        client.switchDB("default");

        People people = new People();
        people.setId(UUID.randomUUID().toString());
        people.setName("Jack");
        people.setAge(20);
        //
        int saveRes = client.input(people).save().execute();

        System.err.println(saveRes);

        IntermediateExpression<People> expression = IntermediateExpression.buildFor(People.class);

        //
        People p1 = new People();
        p1.setName("ma");
        Collection<People> collection = client.input(p1).select().execute();

        System.err.println(collection);


    }
}
