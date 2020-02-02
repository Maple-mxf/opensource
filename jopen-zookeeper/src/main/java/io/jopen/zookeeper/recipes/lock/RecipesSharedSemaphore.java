package io.jopen.zookeeper.recipes.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     A counting semaphore that works across JVMs. All processes in all JVMs that use the same lock path
 *     will achieve an inter-process limited set of leases. Further, this semaphore is mostly "fair" -
 *     each user will get a lease in the order requested (from ZK's point of view).
 *
 *     There are two modes for determining the max leases for the semaphore. In the first mode the max
 *     leases is a convention maintained by the users of a given path. In the second mode a SharedCountReader
 *     is used as the method for semaphores of a given path to determine the max leases.
 *
 *     If a SharedCountReader is not used, no internal checks are done to prevent Process A acting
 *     as if there are 10 leases and Process B acting as if there are 20. Therefore, make sure that all instances
 *     in all processes use the same numberOfLeases value.
 *
 *     The various acquire methods return Lease objects that represent acquired leases. Clients must take care to
 *     close lease objects (ideally in a finally block) else the lease will be lost. However, if the client session
 *     drops (crash, etc.), any leases held by the client are automatically closed and made available to other clients.
 *
 *     {@link InterProcessSemaphoreV2}
 *     {@link Lease}
 *     {@link SharedCountReader}
 * </pre>
 *
 *
 * <pre>
 *     跨JVM工作的计数信号量。所有jvm中使用相同锁路径的所有进程都将实现进程间有限的租用集。
 *     此外，这个信号量基本上是“公平的”——每个用户将按照请求的顺序（从ZK的角度）获得一个租约。
 *
 *     有两种模式可用于确定信号量的最大租约。在第一种模式中，max lease是由给定路径的用户维护的约定。
 *     在第二种模式中，使用SharedCountReader作为给定路径的信号量的方法来确定最大租约。
 *
 *     如果未使用sharedCounterReader，则不会进行内部检查，以防止进程a的行为与有10个租约的行为相同，
 *     而进程B的行为与有20个租约的行为相同。因此，请确保所有进程中的所有实例都使用相同的numberOfLeases值。
 *
 *     各种获取方法返回表示获取的租约的租约对象。客户端必须注意关闭租约对象（最好是在finally块中），
 *     否则租约将丢失。但是，如果客户端会话中断（崩溃等），则客户端持有的任何租约将自动关闭，并可供其他客户端使用。
 * </pre>
 *
 * @author maxuefeng
 * @since 2020/2/2
 */
public final class RecipesSharedSemaphore {

    private final String connectionString = "192.168.74.136:2181";

    private final CuratorFramework curatorClient;

    // int baseSleepTimeMs, int maxRetries
    private final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 20);

    private final InterProcessSemaphoreV2 interProcessSemaphoreV2;

    // 最大租约
    private final static int DEFAULT_MAX_LEASES = 10;


    private final String lockPath = "/localRootPath";

    public RecipesSharedSemaphore() {
        this(DEFAULT_MAX_LEASES);
    }

    /**
     * 第一种模式 ：通过设定maxLeases来控制最大租约
     *
     * @param maxLeases
     */
    public RecipesSharedSemaphore(int maxLeases) {
        curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        curatorClient.start();
        interProcessSemaphoreV2 = new InterProcessSemaphoreV2(curatorClient, lockPath, maxLeases);
    }

    /**
     * @see SharedCount#SharedCount(CuratorFramework, String, int)
     * @see SharedCountReader
     */
    public RecipesSharedSemaphore(SharedCount sharedCount) {
        curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        curatorClient.start();
        interProcessSemaphoreV2 = new InterProcessSemaphoreV2(curatorClient, lockPath, sharedCount);
    }

    public void lock(@NonNull Runnable runnable, int qty) throws Exception {
        //  number of leases to acquire 要获得的租赁数量,如果获取不到指定的租约 则一直会阻塞线程
        Collection<Lease> leaseCollection = this.interProcessSemaphoreV2.acquire(qty);

        // access Lease
        for (Lease lease : leaseCollection) {
            String nodeName = lease.getNodeName();
            byte[] data = lease.getData();
        }

        // close Lease
        // for (Lease lease : leaseCollection) {
        //    lease.close();
        // }
        runnable.run();

        // 关闭所有的资源
        this.interProcessSemaphoreV2.returnAll(leaseCollection);
    }


    public void lock(@NonNull Runnable runnable, long awaitTimeMs) throws Exception {
        //  number of leases to acquire 要获得的租赁数量,如果获取不到指定的租约 则一直会阻塞线程
        Lease lease = this.interProcessSemaphoreV2.acquire(awaitTimeMs, TimeUnit.MILLISECONDS);
        runnable.run();
        this.interProcessSemaphoreV2.returnLease(lease);
    }
}
