package io.jopen.core.common.design.decorator;

import org.junit.Test;

/**
 * @author maxuefeng
 */
public class DecoratorTest {

    @Test
    public void simpleTest() {

        Circle circle = new Circle();

        RedShapeDecorator redCircle = new RedShapeDecorator(new Circle());

        RedShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());

        circle.draw();

        redCircle.draw();

        redRectangle.draw();
    }
}
