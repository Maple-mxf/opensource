package io.jopen.core.function;

import com.google.common.collect.ImmutableList;
import io.jopen.core.function.util.Either;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 */
public class EitherTest {

    /**
     * 测试Either处理运行时异常
     *
     * @see Either#isRight() 右侧保存正确的信息
     * @see Either#isLeft()  左侧保存异常信息
     */
    @Test
    public void simpleTestEither() {

        //
        List<String> vars = ImmutableList.of("HelloWorld", "HelloJava", "HelloPHP");

        //  这种写法是将Either.lif返回的结果再次进行了包装  会调用doSomeThings这个函数
        List<Either> collect = vars.stream().map(Either.lift(this::doSomeThings)).collect(Collectors.toList());

        //  这种写法不会对Either.lift返回的结果不会进行包装  并且不会调用doSomeThings这个函数  （这个地方只是定义了map函数中的参数定义Function）
        List<Function<String, Either>> collect1 = vars.stream().map(s -> Either.lift(this::doSomeThings)).collect(Collectors.toList());

        //  这种写法在Lamda中丢失了类型  本是String类型  但却得到了Object类型  而且Optional的get方法也会出现警告（因为没有进行isPresent判断）
        List<Object> collect2 = vars.stream().map(Either.lift(this::doSomeThings)).filter(Either::isRight).map(either -> either.right().get()).collect(Collectors.toList());

        //  这种写法还是消除不了Optional的get元素的警告  并且写法过于繁琐   同上 也丢失了参数的类型  String最后编程Object的类型
        Stream<Either> es = vars.stream().map(Either.lift(this::doSomeThings));
        List<Object> collect3 = es.filter(either -> either.right().isPresent()).map(either -> either.right().get()).collect(Collectors.toList());

        //  TODO  如何重新设计Either使得代码编写更优雅？
        vars.stream().map(Either.lift(this::doSomeThings)).map(either -> {
            if (either.right().isPresent()) {
                // 此处Get出来的元素为Object  在这个地方的String类型被篡改成了Object
                Object o = either.right().get();
            }
            return null;
        });

        //
       /* vars.stream().map(s -> {
            Function<String, Either> lift = Either.lift(this::doSomeThings);


        })*/
    }

    private String doSomeThings(String var) {

        // 假如在执行这个方法过程中会出现预料不到的某些异常

        return var.toUpperCase();
    }
}
