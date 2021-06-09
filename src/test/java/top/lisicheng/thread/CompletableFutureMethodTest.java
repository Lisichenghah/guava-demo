package top.lisicheng.thread;

import com.google.common.base.Strings;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class CompletableFutureMethodTest {

    protected void taskA() {
        print("taskA start");
        sleep(1, TimeUnit.SECONDS);
        // int i = 1 / 0;
        print("taskA end");
    }

    protected void taskB() {
        print("taskB start");
        sleep(2, TimeUnit.SECONDS);
        print("taskB end");
    }

    protected void taskC() {
        print("taskC start");
        sleep(1500, TimeUnit.MILLISECONDS);
        print("taskC end");
    }

    protected String taskD() {
        print("taskD start");
        sleep(1, TimeUnit.SECONDS);
        print("taskD end");
        return "1路公交车";
    }

    protected String taskE() {
        print("taskE start");
        sleep(1, TimeUnit.SECONDS);
        print("taskE end");
        return "9路公交车";
    }

    protected String taskF() {
        print("taskF start");
        sleep(1, TimeUnit.SECONDS);
        print("taskF end");
        return "饭";
    }

    protected String taskG() {
        print("taskG start");
        sleep(1, TimeUnit.SECONDS);
        print("taskG end");
        return "菜";
    }

    protected void sleep(long t, TimeUnit timeUnit) {
        try {
            Thread.sleep(timeUnit.toMillis(t));
        } catch (InterruptedException ignored) {
        }
    }

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss.SSS");

    protected void print(String msg) {
        String name = Thread.currentThread().getName();
        int threadNameMaxLength = 35;
        if (name.length() < threadNameMaxLength) {
            name = name + Strings.repeat(" ", threadNameMaxLength - name.length());
        }
        System.out.println(LocalTime.now().format(timeFormatter) + " | " + name + " | " + msg);
    }

}
