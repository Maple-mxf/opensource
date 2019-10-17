package io.jopen.core.common.design.observer;

/**
 * 读者A
 *
 * @author maxuefeng
 */
public class ReadorB implements Observer {

    @Override
    public void update(Object object) {
        System.out.println("我是读者B,收到了新书:" + object.toString());
    }


}
