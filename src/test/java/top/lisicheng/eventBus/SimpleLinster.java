package top.lisicheng.eventBus;

import com.google.common.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

public class SimpleLinster {

    @Subscribe
    public void strLinster(String str) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ignored) {

        }
        System.out.println(String.format("%s收到消息：%s", Thread.currentThread().getName(), str));
    }

}
