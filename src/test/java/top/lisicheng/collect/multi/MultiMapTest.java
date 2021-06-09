package top.lisicheng.collect.multi;

import com.google.common.collect.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Comparator;
import java.util.NavigableMap;

/**
 * 数据结构类似于 Map<String,List<String>>
 * <p></p>
 * 暂称他为多元素map吧
 */
class MultiMapTest {

    // 不可变MultiMap
    Multimap<String,Integer> data(){
        ImmutableMultimap<String, Integer> immutableMultimap = ImmutableMultimap
                .<String, Integer>builder()
                .put("2", 11)
                .put("2", 9)
                .put("C", 5)
                .put("1", 10)
                .put("1", 11)
                .put("1", 10)
                .put("B", 2)
                .put("B", 1)
                .put("A", 1)
                .put("A", 3)
                .put("A", 2)
                .build();
        System.out.println("immutableMultimap:"+immutableMultimap);
        return immutableMultimap;
    }


    /**
     * 排序（有序）
     * <p></p>
     */
    @Test
    void testByTree() {

        TreeMultimap<String, Integer> treeMultiMap = TreeMultimap.create(data());

        System.out.println(treeMultiMap);

        System.out.println("---------------");

        System.out.println(treeMultiMap.keySet());

        System.out.println("---------------");

        System.out.println(treeMultiMap.get("1"));

        // 通过key，加value判断是否存在
        // 存在
        Assertions.assertTrue(treeMultiMap.containsEntry("1", 11));
        // 不存在
        Assertions.assertFalse(treeMultiMap.containsEntry("1", 12));
    }

    /**
     * Hash散列
     */
    @Test
    void testByHash() {

        HashMultimap<String, Object> hashMultimap = HashMultimap.create(data());

        System.out.println(hashMultimap);

        hashMultimap.put("2", "a");
        hashMultimap.put("2", "C");
        hashMultimap.put("1", "DIDI");
        hashMultimap.put("1", "ppp");

        System.out.println(hashMultimap);

    }

    /**
     * 按照插入顺序的
     */
    @Test
    void testByLinkedHash() {

        LinkedHashMultimap<String, Object> linkedHashMultimap = LinkedHashMultimap.create(data());

        System.out.println(linkedHashMultimap);

    }

    @Test
    void testByLinkList(){

        LinkedListMultimap<String, Object> linkedListMultimap = LinkedListMultimap.create(data());


        System.out.println(linkedListMultimap);

    }

    @Test
    void test2() {

        ImmutableListMultimap<String, Integer> immutableListMultimap = ImmutableListMultimap
                .<String, Integer>builder()
                .put("A", 1)
                .put("A", 3)
                .put("A", 2)
                .put("A", 1)
                .put("B", 2)
                .put("B", 1)
                .put("C", 5)
                .put("1", 10)
                .put("1", 11)
                .put("1", 10)
                .put("2", 11)
                .put("2", 9)
                .build();

        ImmutableSetMultimap<String, Integer> immutableSetMultimap = ImmutableSetMultimap.<String, Integer>builder()
                .put("A", 1)
                .put("A", 3)
                .put("A", 2)
                .put("A", 1)
                .put("B", 2)
                .put("B", 1)
                .put("C", 5)
                .put("1", 10)
                .put("1", 11)
                .put("1", 10)
                .put("2", 11)
                .put("2", 9)
                .build();

        ImmutableMultimap<String, Integer> immutableMultimap = ImmutableMultimap.<String, Integer>builder()
                .put("A", 1)
                .put("A", 3)
                .put("A", 2)
                .put("A", 1)
                .put("B", 2)
                .put("B", 1)
                .put("C", 5)
                .put("1", 10)
                .put("1", 11)
                .put("1", 10)
                .put("2", 11)
                .put("2", 9)
                .build();

        System.out.println(immutableListMultimap);
        System.out.println(immutableSetMultimap);
        System.out.println(immutableMultimap);

        // ImmutableSortedMultiset.
    }
}
