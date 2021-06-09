package top.lisicheng.thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * completableFuture常用实例方法test
 */
public class CompletableFutureInstanceMethodTest extends CompletableFutureMethodTest {

    @Test
    void thenApplyTest() {

        String join = CompletableFuture
                .supplyAsync(this::taskD)
                .thenApply(v -> v + "到站了")
                .join();
        Assertions.assertEquals("1路公交车到站了", join);

    }

    @Test
    void thenAcceptTest() {

        CompletableFuture
                .supplyAsync(this::taskD)
                .thenAccept(v -> print(v + "到站了"))
                .join();

    }

    @Test
    void thenRunTest() {

        CompletableFuture
                .runAsync(this::taskD)
                .thenRun(() -> print("到站了，上车了"))
                .join();

    }

    @Test
    void thenCombineTest() {

        String join = CompletableFuture
                .supplyAsync(this::taskF)
                .thenCombine(
                        CompletableFuture.supplyAsync(this::taskG),
                        (d, e) -> d + e + "上桌，开吃"
                ).join();
        Assertions.assertEquals("饭菜上桌，开吃", join);

    }

    @Test
    void thenComposeTest() {

        String join = CompletableFuture
                .supplyAsync(this::taskF)
                .thenCompose(v -> CompletableFuture.supplyAsync(() -> v + this.taskG()))
                .join();
        Assertions.assertEquals("饭菜", join);

    }

    @Test
    void thenAcceptBoth() {

        CompletableFuture
                .supplyAsync(this::taskG)
                .thenAcceptBoth(
                        CompletableFuture.supplyAsync(this::taskF),
                        (f, g) -> print(f + g + "上桌，开吃")
                )
                .join();

    }

    @Test
    void runAfterBoth() {

        CompletableFuture
                .runAsync(this::taskA)
                .runAfterBoth(
                        CompletableFuture.runAsync(this::taskB),
                        this::taskC
                ).join();

    }

    @Test
    void applyToEitherTest() {

        String join = CompletableFuture
                .supplyAsync(this::taskD)
                .applyToEither(
                        CompletableFuture.supplyAsync(this::taskE),
                        Function.identity()
                )
                .join();

        print(join + "到站了");

    }

    @Test
    void acceptEither() {

        CompletableFuture
                .supplyAsync(this::taskD)
                .acceptEither(
                        CompletableFuture.supplyAsync(this::taskE),
                        v -> print(v + "到站了")
                )
                .join();

    }

    @Test
    void runAfterEither() {

        CompletableFuture
                .supplyAsync(this::taskD)
                .runAfterEither(
                        CompletableFuture.supplyAsync(this::taskE),
                        () -> print("车到站了，溜了")
                )
                .join();

    }

    @Test
    void exceptionallyTest() {

        String join = CompletableFuture
                .supplyAsync(this::taskD)
                // .thenApply(v -> 1 / 0 + "")
                .exceptionally(e -> taskE())
                .join();

        Assertions.assertEquals("1路公交车", join);
        // Assertions.assertEquals("9路公交车", join);

    }

    @Test
    void whenComplete() {

        CompletableFuture
                .supplyAsync(this::taskD)
                .thenApply(v -> 1 / 0 + "")
                .whenComplete(
                        (v, e) -> {

                            if (v == null) {
                                print("车不来了，走路回家");
                            }

                            if (e == null) {
                                print(v + "来了，上车回家");
                            }
                        }
                )
                .thenRun(() -> {
                    print("then Run");
                })
                .join();

    }

    @Test
    void handleTest() {

        String join = CompletableFuture
                .supplyAsync(this::taskD)
                .thenApply(v -> 1 / 0 + "")
                .handle(
                        (v, e) -> {

                            if (v == null) {
                                return this.taskE();
                            }

                            return v;
                        }
                )
                .join();

        // Assertions.assertEquals("1路公交车", join);
        Assertions.assertEquals("9路公交车", join);
    }
}
