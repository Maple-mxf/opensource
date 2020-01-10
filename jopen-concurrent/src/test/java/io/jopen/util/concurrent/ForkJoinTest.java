package io.jopen.util.concurrent;

import com.google.common.collect.Collections2;
import com.google.common.io.ByteSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 参考分而治之算法
 * {@link java.util.concurrent.BlockingQueue}
 * {@link java.util.concurrent.TransferQueue}
 * {@link java.util.concurrent.ForkJoinTask}
 * {@link java.util.concurrent.RecursiveAction} 递归的触发动作  具体请参考分而治之算法
 * {@link RecursiveTask}
 *
 * @author maxuefeng
 * @since 2020/1/5
 */
public class ForkJoinTest {

    class Task extends RecursiveTask<Integer> {

        private List<String> text;

        // 阈值  超过阈值则需要拆分任务
        private final double threshold = 30D;

        Task(@NonNull List<String> text) {
            this.text = text;
        }

        @Override
        @NonNull
        protected Integer compute() {
            int size = this.text.size();

            // 如果size大于阈值  则将List切分
            if (size > threshold) {
                ArrayList<Task> taskList = new ArrayList<>();
                // 向上取整  获取到要拆分的数量
                int partitionNum = (int) Math.ceil(size / threshold);
                for (int i = 0, j = 0; i < partitionNum; i++) {
                    List<String> collection;

                    // 切分List
                    if (i == partitionNum - 1) {
                        collection = new ArrayList<>();
                        for (int k = j; k < size; k++) {
                            collection.add(text.get(k));
                        }
                    } else {
                        collection = text.subList(j, (j = (int) (j + threshold)));
                    }

                    taskList.add(new Task(collection));
                }

                // 并发执行拆分完的任务
                Collection<Task> reduceTaskList = invokeAll(taskList);

                // 对于拆分玩的任务进行reduce合并;并且返回执行结果
                return reduceTaskList.stream().map(ForkJoinTask::join).reduce((integer, integer2) -> integer + integer2)
                        .orElse(0);

            } else {
                try {
                    saveAsFile();
                    return this.text.size();
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }

        /**
         * 将数据写入文件
         *
         * @see ByteSink
         * @see com.google.common.io.ByteSource
         */
        void saveAsFile() throws IOException {
            // 将当前数据保存到文件
            Files.asCharSink(new File(UUID.randomUUID().toString() + ".txt"), StandardCharsets.UTF_8, FileWriteMode.APPEND)
                    .writeLines(this.text.stream());
        }
    }


    /**
     * @see java.util.concurrent.ForkJoinPool#submit(ForkJoinTask)
     * @see ForkJoinTask
     * @see BlockingDeque
     * @see ForkJoinPool#workQueues
     * @see ForkJoinPool#submit(Runnable)
     * @see ForkJoinPool#submit(Callable)
     * @see ForkJoinPool#submit(Runnable, Object) 第二个参数表示默认返回值（类似变量在不同作用域的传递）
     * @see Runtime#availableProcessors() 默认根据CPU核数进行分配线程数量
     * @see ForkJoinPool#ForkJoinPool(int, ForkJoinPool.ForkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler, int, String)
     * @see ForkJoinPool#getCommonPoolParallelism()
     * @see ForkJoinPool#config config参数和并行度存在一定关联{@link ForkJoinPool#ForkJoinPool(int, ForkJoinPool.ForkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler, int, String)}
     * config = (parallelism & SMASK) | mode;如果config为true，则很明显最低位为1 反之为0，
     * onfig是并行度与SMASK取与运算再与mode取或,这里并行度最大是15位整数(MAX_CAP=0x7FFF),而SMASK作用于整数后16位,mode在FIFO为1<<16,LIFO是0.很好计算.
     */
    @Test
    public void testForkJoinExecutor() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(
                // 并行度参数
                10,
                // 线程生产工厂
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                // 自定义异常处理（在线程执行任务过程中会捕获到错误信息）
                (t, e) -> System.out.printf("Thread Id %d,Name %s execute task failure,cause %s", t.getId(), t.getName(), e.getCause().getMessage()),
                // asyncMode都是false,会选用LIFO队列,是true是会选用FIFO队列
                true);

        // 生成100个字符串
        Random random = new Random();
        List<String> text = Stream.generate(() -> String.valueOf(random.nextInt())).limit(100).collect(Collectors.toList());

        // 提交任务 并且返回合并的任务
        ForkJoinTask<Integer> reduceTask = forkJoinPool.submit(new Task(text));

        /*
         * @see ForkJoinTask#get()
         * 异步执行代码
         */
        CompletableFuture.supplyAsync(() -> {
            try {
                return reduceTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                System.err.println("任务执行失败");
                return 0;
            }
        }, Executors.newSingleThreadExecutor())
                // 打印结果
                .thenAccept(System.err::println);

        // 故意阻塞主线程 等待异步执行结果
        Thread.sleep(1000000);
    }
}
