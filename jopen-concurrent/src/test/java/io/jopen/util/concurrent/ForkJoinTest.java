package io.jopen.util.concurrent;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
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
            if (size > threshold) {
                // 向上取整  获取到要拆分的数量
                int partitionNum = (int) Math.ceil(size / threshold);
                ArrayList<Task> taskList = new ArrayList<>();
                for (int i = 0; i < partitionNum; ) {
                    List<String> collection = this.text.subList(i, i + partitionNum);
                    taskList.add(new Task(collection));
                    i += partitionNum;
                }
                // 并发执行拆分完的任务
                Collection<Task> reduceTaskList = invokeAll(taskList);

                // 对于拆分玩的任务进行reduce合并;并且返回执行结果
                return reduceTaskList.parallelStream().map(ForkJoinTask::join).reduce((integer, integer2) -> integer + integer2)
                        .orElse(0);
            }
            return size;
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
     * @see ForkJoinPool#submit(Runnable)
     * @see ForkJoinPool#submit(Callable)
     * @see ForkJoinPool#submit(Runnable, Object) 第二个参数表示默认返回值（类似变量在不同作用域的传递）
     * @see Runtime#availableProcessors() 默认根据CPU核数进行分配线程数量
     * @see ForkJoinPool#ForkJoinPool(int, ForkJoinPool.ForkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler, int, String)
     */
    @Test
    public void testForkJoinExecutor() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(
                // 并行线程数量
                10,
                // 线程生产工厂
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                // 自定义异常处理（在线程执行任务过程中会捕获到错误信息）
                (t, e) -> System.out.printf("Thread Id %d,Name %s execute task failure,cause %s", t.getId(), t.getName(), e.getCause().getMessage()),
                true);

        // 数据生成
        Random random = new Random();
        List<String> text = Stream.generate(() -> String.valueOf(random.doubles())).limit(10000).collect(Collectors.toList());

        // 提交任务 并且返回合并的任务
        ForkJoinTask<Integer> reduceTask = forkJoinPool.submit(new Task(text));

        /*
         * @see ForkJoinTask#get()
         * 此处会阻塞主线程
         */
        System.err.println(reduceTask.get());
    }
}
