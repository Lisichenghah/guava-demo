package top.lisicheng.collect.multi;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 统计key统计次数set
 */
class MultiSetTest {

    private List<String> strings = Lists.newArrayList("1", "2", "3", "4", "4", "5", "3", "2", "1");

    @Test
    void test() {



        // LinkedHashMultiset<String> objects = LinkedHashMultiset.create();
        Multiset<String> multiset = HashMultiset.create(strings);

        // 获取元素值为1在集合中出现的次数
        Assertions.assertEquals(multiset.count("1"), 2);

        /*
            the code run println
            1 x 2
            2 x 2
            3 x 2
            4 x 2
            5
         */
        // 打印集合
        multiset.entrySet().forEach(System.out::println);

        // 添加元素5（1次）
        multiset.add("5");

        // 添加元素6，并添加3次
        multiset.add("6", 3);

        System.out.println("------------------------");

        /*
            the code run println
            1
            2
            3
            4
            5
         */
        // 去重元素并打印
        multiset.elementSet().forEach(System.out::println);

        System.out.println("------------------------");

        /*
            the code run println
            1 x 2
            2 x 2
            3 x 2
            4 x 2
            5 x 2
            6 x 3
         */
        // 打印集合
        multiset.entrySet().forEach(System.out::println);

        // 删除元素1（1次）
        multiset.remove("1");

        System.out.println("------------------------");

        /*
            the code run println
            1
            2 x 2
            3 x 2
            4 x 2
            5 x 2
            6 x 3
         */
        // 打印集合
        multiset.entrySet().forEach(System.out::println);

        // 删除元素2，3次（如超过剩余次数，不会变成-1） 2-3=0
        multiset.remove("2", 3);

        System.out.println("------------------------");

         /*
            the code run println
            1
            3 x 2
            4 x 2
            5 x 2
            6 x 3
         */
        // 打印集合
        multiset.entrySet().forEach(System.out::println);

        multiset.setCount("2", 1);
        multiset.setCount("3", 1);

        System.out.println("------------------------");

         /*
            the code run println
            1
            2
            3
            4 x 2
            5 x 2
            6 x 3
         */
        // 打印集合
        multiset.entrySet().forEach(System.out::println);

        // 返回集合元素个数
        Assertions.assertEquals(multiset.size(), 10);

    }

    @Test
    void testByTree(){
        TreeMultiset<String> treeMultiset = TreeMultiset.create(strings);
    }

    @Test
    void testByLinked(){
        LinkedHashMultiset<String> linkedHashMultiset = LinkedHashMultiset.create();
    }



}
