package io.jopen.core.common.concurrent;

import org.junit.Test;

import java.util.concurrent.Phaser;

/**
 * [https://juejin.im/post/5a093ff551882531bb6c4ee3]
 *
 * @author maxuefeng
 * @see Phaser
 */
public class PhaserTest {

    /**
     * 测试理论结果：多个线程必须等到其它线程的同一阶段的任务全部完成才能进行到下一个阶段
     * <p>
     * arriveAndAwaitAdvance： 当前线程当前阶段执行完毕，等待其它线程完成当前阶段。
     * 如果当前线程是该阶段最后一个未到达的，则该方法直接返回下一个阶段的序号（阶段序号从0开始），
     * 同时其它线程的该方法也返回下一个阶段的序号。
     * <p>
     * 测试Phaser计数
     *
     * @see Phaser#arriveAndAwaitAdvance()
     */
    @Test
    public void testSimplePhaserCount() throws InterruptedException {

        int parties = 3, phases = 4;

        Phaser phaser = new Phaser(parties) {

            @Override
            protected boolean onAdvance(int phase, int registeredParties) {

                System.err.println("[phase: " + phase + " ]");
                return registeredParties == 0;
            }
        };

        for (int i = 0; i < parties; i++) {

            int threadId = i;

            new Thread(() -> {

                for (int phase = 0; phase < phases; phase++) {
                    System.out.println(String.format("Thread %s, phase %s", threadId, phase));
                }

                System.out.println(phaser.getPhase());
                phaser.awaitAdvance(phaser.getPhase());


            }).start();
        }

        Thread.sleep(200000000);
    }
}