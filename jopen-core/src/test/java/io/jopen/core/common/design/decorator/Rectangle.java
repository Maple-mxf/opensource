package io.jopen.core.common.design.decorator;

/**
 * @author maxuefeng
 */
public class Rectangle implements Shape {

    @Override
    public void draw() {
        System.err.println("Shape: Rectangle");
    }
}
