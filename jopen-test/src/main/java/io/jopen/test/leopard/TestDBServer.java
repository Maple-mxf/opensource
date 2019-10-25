package io.jopen.test.leopard;

import io.jopen.memdb.base.storage.client.LeopardClient;

import java.util.UUID;

/**
 * @author maxuefeng
 * @since 2019/10/25
 */
public class TestDBServer {

    private static LeopardClient client = new LeopardClient.Builder().startDBServer().build();

    public static void main(String[] args) {
        client.switchDB("default");

        People people = new People();
        people.setId(UUID.randomUUID().toString());
        people.setName("Jack");
        people.setAge(20);
        //
        int saveRes = client.input(people).save().execute();

        System.err.println(saveRes);
    }
}
