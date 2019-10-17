/**
 * Java 委托模式
 * <p>
 * 一个自定义的Button按钮，这个按钮在RecyclerView的每一个ViewType都用到，响应的点击事件都一样。
 * <p>
 * 可以理解成，Button这个对象A，在点击时需要做一件事件（做事情），但不用关心具体什么事情，
 * 由另一个对象B负责具体的实现（具体做什么事情）。
 * <p>
 * 通知者类完全不知道自己需要通知的是谁，做到了完全解耦(1)，同时也去掉了抽象的观察者类(2)。
 * 观察者模式的缺点是抽象通知者依赖抽象观察者，.NET中用委托技术处理这个问题，事件委托扩展性好(3)
 * <p>
 * TODO  当前包下实现的委托模式有BUG
 *
 * @author maxuefeng
 */
package io.jopen.core.common.design.delegate;