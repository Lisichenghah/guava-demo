package top.lisicheng.thread;

import com.google.common.util.concurrent.ExecutionList;
import com.google.common.util.concurrent.Runnables;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 任务顺序执行器
 * <p></p>
 * 设置任务和对应任务执行线程
 * 按照添加顺序执行任务
 * 添加时模型类似于栈，先进后出（先进在最后）
 * 执行会逆序，从而达到先进先出
 */
class ExecutionListTest {

    @Test
    void test() {



        ExecutorService executorService = Executors.newFixedThreadPool(10);

        ExecutionList executionList = new ExecutionList();


        for (int i = 0; i < 5; i++) {

            int finalI = i;
            // 顺序添加
            executionList.add(() -> {
                // TimeUnit.MILLISECONDS.sleep();
                System.out.println(Thread.currentThread().getName() + "：" + finalI);
            }, executorService);

        }

        // 执行时
        executionList.execute();

        // 在未执行的时候，会添加到执行栈去，如果执行的时有新增任务，则直接执行（这个时候的执行顺序取决于线程池）


        //
        // for (int i = 5; i < 10; i++) {
        //     int finalI = i;
        //     executorService.execute(() -> {
        //         executionList.add(() -> {
        //             System.out.println(Thread.currentThread().getName() + "：" + finalI);
        //         }, executorService);
        //     });
        // }


    }

    @Test
    @SuppressWarnings("all")
    void test2(){

        Integer integer = 127;
        Integer integer1 =  127;
        Integer integer2 = 128;
        Integer integer3 = new Integer(128);
        int int4 = 128;

        System.out.println(integer == integer1);
        System.out.println(integer2 == integer3);
        System.out.println(integer2 == int4);
        System.out.println(integer3 == int4);

        // Stack<Character> objects = new Stack<>();
        // LinkedList<Object> objects1 = new LinkedList<>();
        //
        // String s = "123";
        //
        // for (char c : s.toCharArray()) {
        //     objects.push(c);
        //     objects1.add(c);
        // }
        // for (char c : s.toCharArray()) {
        //     System.out.println(objects.pop());
        //     System.out.println(objects1.pollLast());
        // }
        //
        // System.out.println(objects.isEmpty());
        //
        // // Arrays.sort(s,Comparator.reverseOrder());

    }
}
