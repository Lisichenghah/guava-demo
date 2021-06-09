package top.lisicheng.utils;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import top.lisicheng.pojo.Guava;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderingTest {

    private static List<String> LIST = Arrays.asList("b", "c", "2", "d", "1");

    private static List<Guava> LIST_2 = Arrays.asList(new Guava("b"), new Guava("c"), new Guava("2"), new Guava("d"), new Guava("1"), new Guava(null));

    private static List<String> LIST_3 = Arrays.asList("b", "c", "2", "d", "1", null);

    @Test
    void test() {

        // natural() 自然排序，整数从小到大，字符字典排序
//        assertEquals(Ordering.natural().sortedCopy(LIST), Arrays.asList("1", "2", "b", "c", "d"));


        // usingToString  toString返回字符串，按照字典顺序排序
//        LIST.stream().sorted(Ordering.usingToString()).forEach(System.out::println);

        Ordering.natural().onResultOf(Guava::getName).nullsFirst().sortedCopy(LIST_2);

        System.out.println(Ordering.from(Comparator.comparing(Guava::getName)).nullsFirst().sortedCopy(LIST_2));

//        System.out.println(Ordering.natural().onResultOf(Guava::getName).nullsFirst().sortedCopy(LIST_2));

//        System.out.println(Ordering.natural().nullsFirst().sortedCopy(LIST_3));

//        LIST_2.stream().sorted(Ordering.from(Comparator.comparing(Guava::getName)).nullsFirst()).forEach(System.out::println);
    }

}
