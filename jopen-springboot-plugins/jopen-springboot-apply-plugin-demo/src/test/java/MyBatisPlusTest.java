import io.jopen.apply.plugin.demo.mongo.User;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author maxuefeng
 * @since 2020/1/9
 */
public class MyBatisPlusTest {

    public void test() {

    }


    public static void main(String[] args) {
        //

        Consumer<Object> consumer1 = new Consumer<Object>() {
            @Override
            public void accept(Object object) {
                System.err.println(object);
            }
        };
        Consumer<Object> consumer2 = new Consumer<Object>() {
            @Override
            public void accept(Object object) {
                System.err.println(object);
            }
        };
        System.err.println(consumer1.getClass().equals(consumer2.getClass()));

        Function<User,String> function = User::getAddress;
        Function<User,String> function1 = User::getAddress;
        System.err.println(function.getClass().equals(function1.getClass()));

        User user = new User();
        Supplier<String> supplier = user::getAddress;
        Supplier<String> supplier1 = user::getAddress;
        System.err.println(supplier.getClass().equals(supplier1.getClass()));
    }
}
