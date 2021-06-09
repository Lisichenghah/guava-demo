import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;


class CompletableFutureTest {

    private final Executor executor = new ThreadPoolExecutor(
            // 核心线程数
            10,
            // 最大线程数
            20,
            // 最大空闲时间
            300,
            // 时间单位
            TimeUnit.MILLISECONDS,
            // 任务队列
            new LinkedBlockingDeque<>(1024));

    // @BeforeEach
    void before() {
        log("main start");
    }

    // @AfterEach
    void end() {
        log("main end");
    }

    @Test
    void runAsyncTest() {

        CompletableFuture.runAsync(() -> {
            log("run Async");
        });

        CompletableFuture.runAsync(() -> {
            log("run Async with executor");
        }, executor);

    }

    @Test
    void supplierAsyncTest() throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> createObj = CompletableFuture.supplyAsync(() -> {
            log("create obj");
            return 1;
        });

        CompletableFuture<Integer> create_obj_with_executor = CompletableFuture.supplyAsync(() -> {
            log("create obj with executor");
            return 1;
        }, executor);

        log("create obj result : " + createObj.get());
        log("create obj with executor : " + create_obj_with_executor.get());

    }

    @Test
    void completedFutureTest() {

        class Test {

            private Integer test() {
                log("completedFuture is run");
                return 1;
            }

        }

        // 无异步
        CompletableFuture.completedFuture(new Test().test());

    }

    @Test
    void testThenApply() {

        CompletableFuture.supplyAsync(() -> {
            log("create obj");
            return 1;
        }).thenApply(value -> {
            log("then apply with main");
            return 1 + 1;
        });

        CompletableFuture.supplyAsync(() -> {
            log("create obj");
            return 1;
        }).thenApplyAsync(value -> {
            log("then apply with async");
            return 1 + 1;
        });

        CompletableFuture.supplyAsync(() -> {
            log("create obj with executor");
            return 1;
        }, executor).thenApplyAsync(value -> {
            log("then apply with executor");
            return 1 + 1;
        }, executor);

    }

    @Test
    void testThenRun() {

        CompletableFuture.runAsync(() -> {
            log("run async");
        }).thenRun(() -> {
            log("then run with main");
        });

        CompletableFuture.runAsync(() -> {
            log("run async");
        }).thenRunAsync(() -> {
            log("then run async");
        });

        CompletableFuture.runAsync(() -> {
            log("run async");
        }, executor).thenRunAsync(() -> {
            log("then run with executor");
        }, executor);
    }

    @Test
    void test() {

        CompletableFuture.supplyAsync(() -> {
            log("supply async");
            return 1;
        }).thenAccept(value -> {
            log("then accept value is :" + value);
        });

    }


    private void log(String str) {
        System.out.println(Thread.currentThread().getName() + ":【" + str + "】");
    }
}
