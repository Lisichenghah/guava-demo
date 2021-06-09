package top.lisicheng.utils;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 用stopwatch最大作用用于记录代码执行时间
 */
class StopwatchTest {

    @Test
    void test() throws InterruptedException {
        Stopwatch started = Stopwatch.createStarted();
        TimeUnit.MILLISECONDS.sleep(2);
        System.out.printf("run %s", started.stop());
    }

}
