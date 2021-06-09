package top.lisicheng.thread;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.*;

class FuturesTest {

    private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    /**
     *
     */
    @Test
    void allAsListTest() {

        Stopwatch started = Stopwatch.createStarted();

        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(fixedThreadPool);

        User user = new User("tom", 16, true);

        ListenableFuture<?> t = listeningExecutorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "任务1开始执行");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!user.getName().startsWith("t")) {
                System.out.println(Thread.currentThread().getName() + "任务1执行失败");
                throw new RuntimeException("user.name not start with t");
            }
            System.out.println(Thread.currentThread().getName() + "任务1执行完成");
        });

        ListenableFuture<?> t1 = listeningExecutorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "任务2开始执行");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (user.getAge() < 17) {
                System.out.println(Thread.currentThread().getName() + "任务2执行失败");
                throw new RuntimeException("user.age is < 17");
            }
            System.out.println(Thread.currentThread().getName() + "任务2执行完成");
        });

        ListenableFuture<?> t2 = listeningExecutorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "任务3开始执行");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (user.getSex()) {
                System.out.println(Thread.currentThread().getName() + "任务3执行失败");
                throw new RuntimeException("user.sex is true");
            }
            System.out.println(Thread.currentThread().getName() + "任务3执行完成");
        });

        try {
            ListenableFuture<List<Object>> listListenableFuture = Futures.allAsList(t, t1, t2);
            listListenableFuture.get();
            // 主逻辑
            // nothing
        } catch (Exception e) {
            // throw new IllegalArgumentException("有一个失败了",e);
            System.out.println("有一个失败了" + e.getMessage());
        }

        System.out.println(started.stop());


    }

    @Test
    void successFullListTest() throws ExecutionException, InterruptedException {

        Stopwatch started = Stopwatch.createStarted();

        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(fixedThreadPool);

        User user = new User("tom", 16, true);

        ListenableFuture<?> t = listeningExecutorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "任务1开始执行");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!user.getName().startsWith("t")) {
                System.out.println(Thread.currentThread().getName() + "任务1执行失败");
                throw new RuntimeException("user.name not start with t");
            }
            System.out.println(Thread.currentThread().getName() + "任务1执行完成");
        });

        ListenableFuture<?> t1 = listeningExecutorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "任务2开始执行");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (user.getAge() < 17) {
                System.out.println(Thread.currentThread().getName() + "任务2执行失败");
                throw new RuntimeException("user.age is < 17");
            }
            System.out.println(Thread.currentThread().getName() + "任务2执行完成");
        });

        ListenableFuture<?> t2 = listeningExecutorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "任务3开始执行");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (user.getSex()) {
                System.out.println(Thread.currentThread().getName() + "任务3执行失败");
                throw new RuntimeException("user.sex is true");
            }
            System.out.println(Thread.currentThread().getName() + "任务3执行完成");
        });

        ListenableFuture<List<Object>> listListenableFuture = Futures.successfulAsList(t, t1, t2);

        System.out.println(listListenableFuture.get());
        System.out.println(started.stop());
    }

    @Test
    void submitTest() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(2);

        ListenableFuture<Integer> submit = Futures.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "执行任务");
            countDownLatch.countDown();
            return 1;
        }, fixedThreadPool);

        submit.addListener(() -> {
            System.out.println(Thread.currentThread().getName() + "任务执行完成");
            countDownLatch.countDown();
        }, fixedThreadPool);

        countDownLatch.await();

    }

    @Test
    void submitAsyncTest() {
        Futures.submitAsync(() -> Futures.immediateFuture(1), fixedThreadPool);
    }

    @Test()
    void immediateFailedFutureTest() throws ExecutionException, InterruptedException {
        ListenableFuture<Integer> listenableFuture = Futures.immediateFuture(v());
        // ListenableFuture<Object> quick_error = Futures.immediateFailedFuture(new Throwable("quick error"));
        // Assertions.assertThrows(ExecutionException.class, listenableFuture::get);
        // System.out.println(listenableFuture.get());
    }

    @Test
    void transformTest() throws ExecutionException, InterruptedException {

        ListenableFuture<Integer> listenableFuture = Futures.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "任务执行");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }, fixedThreadPool);

        ListenableFuture<Integer> transform = Futures.transform(listenableFuture, result -> {
            System.out.println(Thread.currentThread().getName() + "拿到结果了,将结果*10");
            return result * 10;
        }, fixedThreadPool);

        Assertions.assertEquals(transform.get(), 10);
    }

    @Test
    void JdkFutureAdapters$listenInPoolThreadTest() throws Exception {

        Future<Integer> future = fixedThreadPool.submit(() -> {
            return 1;
        });

        // 将future转换为ListenableFuture
        ListenableFuture<Integer> integerListenableFuture = JdkFutureAdapters.listenInPoolThread(future);

    }

    @Test
    void test() throws ExecutionException, InterruptedException {

        ListenableFuture<Integer> listenableFuture = Futures.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "执行任务");
            TimeUnit.SECONDS.sleep(2);
            int i = 1 / 0;
            // throw new RuntimeException("");
            return 1;
        }, fixedThreadPool);

        ListenableFuture<Serializable> catching = Futures.catching(listenableFuture, RuntimeException.class, result -> {
            System.out.println(Thread.currentThread().getName() + "拿到结果" + result + "了*20");
            return result;
        }, fixedThreadPool);

        // 不明白
        // System.out.println(Futures.getChecked(listenableFuture, RuntimeException.class));

    }

    private static class User {

        private String name;

        private Integer age;

        private Boolean sex;

        public User() {
        }

        public User(String name, Integer age, Boolean sex) {
            this.name = name;
            this.age = age;
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public User setName(String name) {
            this.name = name;
            return this;
        }

        public Integer getAge() {
            return age;
        }

        public User setAge(Integer age) {
            this.age = age;
            return this;
        }

        public Boolean getSex() {
            return sex;
        }

        public User setSex(Boolean sex) {
            this.sex = sex;
            return this;
        }
    }

    private int v() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("获取值");
        return 1;
    }

}
