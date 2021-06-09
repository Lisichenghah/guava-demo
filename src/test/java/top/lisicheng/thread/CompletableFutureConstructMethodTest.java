package top.lisicheng.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

/**
 * completableFuture构造方法test
 */
class CompletableFutureConstructMethodTest extends CompletableFutureMethodTest{

    @Test
    void test(){

        CompletableFuture<Object> completableFuture = new CompletableFuture<>();
        completableFuture.thenRun(this::taskA).join();

    }

}
