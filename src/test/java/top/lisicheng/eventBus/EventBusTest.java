package top.lisicheng.eventBus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class EventBusTest {

    @Test
    void test1() {

        EventBus eventBus = new EventBus();
        eventBus.register(new SimpleLinster());

        System.out.println(Thread.currentThread().getName() + "开始推送消息");
        eventBus.post("123");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
