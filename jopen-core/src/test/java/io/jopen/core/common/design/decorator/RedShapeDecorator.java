package io.jopen.core.common.design.decorator;

/**
 * 装饰器重写父类的方法  进行装饰
 *
 * @author maxuefeng
 */
public class RedShapeDecorator extends AbstractShapeDecorator {


    public RedShapeDecorator(Shape decoratorShape) {
        super(decoratorShape);
    }

    @Override
    public void draw() {
        decoratorShape.draw();
        setDecoratorShape();
    }

    public void setDecoratorShape() {
        System.out.println("Border Color: Red");
    }
}
