package io.jopen.core.common.design.decorator;

/**
 * @author maxuefeng
 */
public class AbstractShapeDecorator implements Shape {

    protected Shape decoratorShape;

    public AbstractShapeDecorator(Shape decoratorShape) {
        this.decoratorShape = decoratorShape;
    }

    @Override
    public void draw() {
        decoratorShape.draw();
    }
}
