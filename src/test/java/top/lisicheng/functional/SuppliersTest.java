package top.lisicheng.functional;

import com.google.common.base.Functions;
import com.google.common.base.Suppliers;
import org.junit.jupiter.api.Test;
import top.lisicheng.pojo.Guava;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuppliersTest {

    @Test
    void test() throws InterruptedException {

        // 生产一个对象
        // 等同于java.util.function.Supplier () -> "123"
        assertEquals(Suppliers.ofInstance("123").get(), "123");

        // 生产一个参数为supplier的function函数对象
        assertEquals(Suppliers.supplierFunction().apply(Suppliers.ofInstance("123")), "123");

        // 生产一个单例对象
        Suppliers.memoize(Suppliers.ofInstance(new Guava())).get();

        // 生产一个会过期的单例对象
        Suppliers.memoizeWithExpiration(Suppliers.ofInstance(new Guava()), 1, TimeUnit.SECONDS);

        // 生产一个线程安全的Supplier
        synchronizedSupplier();

        // 首先获取supplier函数的值，再由function转换，转换后的值生成一个supplier
        assertEquals(Suppliers.compose(Functions.toStringFunction(), Suppliers.ofInstance(1)).get(), "1");
    }


    /**
     * 模拟多个线程抢占资源
     *
     * @throws InterruptedException 线程状态异常
     */
    private void synchronizedSupplier() throws InterruptedException {
        Supplier<Integer> integerSupplier = Suppliers.synchronizedSupplier(() -> {
            System.out.printf("%s get lock \n", Thread.currentThread().getName());
            // 模拟耗时操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                integerSupplier.get();
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
    }
}
