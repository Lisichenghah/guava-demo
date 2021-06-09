package top.lisicheng.collect.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collector;

/**
 * 不可变list（只读list）
 */
class ImmutableListTest {

    @Test
    void testOf() {

        // 创建一个空集合
        ImmutableList<Object> emptyList = ImmutableList.of();

        Assertions.assertTrue(emptyList.isEmpty());

        // 创建拥有三个元素的集合
        ImmutableList<Integer> immutableList = ImmutableList.of(1, 2, 3);

        Assertions.assertEquals(immutableList.size(), 3);

        // 创建拥有9个元素的集合
        ImmutableList<Integer> immutableList1 = ImmutableList.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Assertions.assertEquals(immutableList1.size(), 9);

    }

    @Test
    void testCopyOf() {

        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3);

        // 通过迭代器构建
        ImmutableList.copyOf(list.iterator());

        // 通过集合构建
        ImmutableList.copyOf(list);

        // 通过数组构建
        ImmutableList<String> strings = ImmutableList.copyOf(new String[]{"A", "B", "C"});


    }

    @Test
    void testBuilder() {

        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3);

        ImmutableList<Integer> build = ImmutableList.<Integer>builder()
                // 添加单个元素
                .add(1)
                // 添加多个元素
                .add(1, 2, 3)
                // 添加迭代器中的元素
                .addAll(list.iterator())
                .build();

        System.out.println(build);

    }

    /**
     * 不懂这个函数
     */
    @Test
    void test() {
        Collector<Object, ?, ImmutableList<Object>> objectImmutableListCollector = ImmutableList.toImmutableList();
        objectImmutableListCollector.accumulator();
    }
}
