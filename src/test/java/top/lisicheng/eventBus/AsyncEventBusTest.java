package top.lisicheng.eventBus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class AsyncEventBusTest {

    @Test
    void test() throws InterruptedException {
        AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(10));
        asyncEventBus.register(new AsyncEventBusTest());
        System.out.println("start post");
        asyncEventBus.post("message");
        System.out.println("post end");
        TimeUnit.SECONDS.sleep(2);
    }

    @Subscribe
    void subscribe(String event) throws InterruptedException {
        System.out.println("reverse event...");
        System.out.println(event);
        System.out.println("to do it ....");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("done");
        throw new RuntimeException("123");
    }

}
