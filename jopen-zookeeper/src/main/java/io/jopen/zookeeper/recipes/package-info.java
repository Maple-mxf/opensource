/**
 * Curator implements all of the recipes listed on the ZooKeeper recipes doc (except two phase commit).
 * Click on the recipe name below for detailed documentation. NOTE: Most Curator recipes will autocreate
 * parent nodes of paths given to the recipe as CreateMode.CONTAINER. Also, see Tech Note 7 regarding
 * "Curator Recipes Own Their ZNode/Paths".
 * <p>
 *
 *
 * <pre>
 * <h4>Zookeeper curator framework recipes module provide</h4>
 *
 * 1 most of server election(提供了zookeeper集群中的每台服务器的选举机制，在选举期间每台机器都是属于master节点)
 * 2 distribute lock(提供了诸多类型的分布式锁，比如分布式可重入锁SharedLockReentrantLock，简单分布式锁，具有不可重入性之SharedLock，
 * 读写锁SharedLockReadWrite，读写锁相对于诸多的悲观锁，效率更高(读读不互斥，读写互斥)。)
 * 3 counter (分布式计数器，主要体现在atomic原子属性)
 * 4 node watch (节点监控，主要体现在节点的事件机制，由事件源，事件，监听器组成)
 * 5 node cache (节点数据缓存)
 * 6 distribute queue (分布式队列，主要有阻塞队列，优先级队列等，普通队列)
 * 7 barriers (分布式同步机制，类似java并发包中的栅栏)
 * </pre>
 */
package io.jopen.zookeeper.recipes;

