package top.lisicheng.thread;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class MoreExecutorsTest {

    private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);


    @Test
    void createListeningExecutorServiceTest() throws InterruptedException {


        CountDownLatch countDownLatch = new CountDownLatch(2);

        // 创建可回调线程池
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(fixedThreadPool);

        // 提交任务
        ListenableFuture<String> stringListenableFuture = listeningExecutorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "执行任务");
            TimeUnit.SECONDS.sleep(2);
            countDownLatch.countDown();
            return "result1";
        });

        // 添加任务回调
        stringListenableFuture.addListener(() -> {
            System.out.println(Thread.currentThread().getName() + "任务回调");
            countDownLatch.countDown();
        }, listeningExecutorService);

        Futures.addCallback(stringListenableFuture, FutureCallbacks
                .create(value -> {

                    System.out.println(Thread.currentThread().getName() + "成功回调");
                    countDownLatch.countDown();


                }, throwable -> {

                    System.out.println(Thread.currentThread().getName() + "失败回调." + throwable.getMessage());
                    countDownLatch.countDown();


                }), listeningExecutorService);



        System.out.println(Thread.currentThread().getName() + "主线程执行");
        countDownLatch.await();
    }

}
