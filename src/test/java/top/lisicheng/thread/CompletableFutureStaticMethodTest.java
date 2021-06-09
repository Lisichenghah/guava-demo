package top.lisicheng.thread;

import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * completableFuture静态方法test
 */
public class CompletableFutureStaticMethodTest extends CompletableFutureMethodTest{

    @Test
    void runAsync() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(this::taskA);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        future.join();
    }

    @Test
    void supplyAsyncTest() {
        CompletableFuture<Integer> supplyAsyncCompletableFuture = CompletableFuture.supplyAsync(() -> {
            taskA();
            return 1;
        });
        supplyAsyncCompletableFuture.join();
    }

    @Test
    void allOfTest() {

        Stopwatch started = Stopwatch.createStarted();
        print("allOfTest start");

        CompletableFuture<Void> allOfCompletableFuture = CompletableFuture.allOf(
                CompletableFuture.runAsync(this::taskA),
                CompletableFuture.runAsync(this::taskB),
                CompletableFuture.runAsync(this::taskC)
        );

        try {
            allOfCompletableFuture.join();
        } catch (Exception e) {
            print("error allOf " + e.getMessage());
        }

        print("allOfTest end" + started.stop());
    }

    @Test
    void anyOfTest() {

        Stopwatch started = Stopwatch.createStarted();
        print("anyOfTest start");

        CompletableFuture<Object> anyOfCompletableFuture = CompletableFuture.anyOf(
                CompletableFuture.runAsync(this::taskA),
                CompletableFuture.runAsync(this::taskB),
                CompletableFuture.runAsync(this::taskC)
        );

        try {
            anyOfCompletableFuture.join();
        } catch (Exception e) {
            print("error allOf " + e.getMessage());
        }

        print("anyOfTest end" + started.stop());
    }

    @Test
    void completedFutureTest() {

        CompletableFuture<Integer> completableFuture = CompletableFuture.completedFuture(1);
        completableFuture.join();
    }



}
