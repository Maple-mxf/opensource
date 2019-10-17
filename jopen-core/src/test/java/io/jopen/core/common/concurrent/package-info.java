/**
 * 线程池:https://www.jianshu.com/p/87bff5cc8d8c
 * 线程是稀缺资源，如果被无限制的创建，不仅会消耗系统资源，还会降低系统的稳定
 * 合理的使用线程池对线程进行统一分配、调优和监控，有以下好处：
 * 1 降低资源消耗
 * 2 提高响应速度；
 * 3 提高线程的可管理性
 * <p>
 * ThreadPoolExecutor原理
 * <p>
 * 线程创建: 工厂模式进行设计  可以通过ThreadFactory给每个线程给一个标识符
 * 线程池状态
 * 阻塞队列:workQueue{ArrayBlockingQueue LinkedBlockingQueue SynchronousQueue  priorityBlockingQueue}
 * 拒绝策略:
 *
 * @author maxuefeng
 * @see java.util.concurrent.ThreadPoolExecutor
 * @see java.util.concurrent.FutureTask
 * @see java.util.concurrent.ThreadFactory  线程工厂
 * @see java.util.concurrent.Executors.DefaultThreadFactory  Executors中默认实现的线程工厂
 * @see java.util.concurrent.RejectedExecutionHandler 丢弃任务的前提是线程池的容量有限,任务拒绝策略接口{直接丢弃（DiscardPolicy）
 * 丢弃队列中最老的任务(DiscardOldestPolicy) 抛异常(AbortPolicy) 将任务分给调用线程来执行(CallerRunsPolicy)。 }
 * 默认的任务丢弃策略是:AbortPolicy(抛出异常)
 */
package io.jopen.core.common.concurrent;