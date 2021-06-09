package top.lisicheng.collect;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class ListsTest {

    @Test
    void test() {

        // 创建一个空list
        ArrayList<String> objects = Lists.newArrayList();

        // 创建一个有元素的list
        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3);

        // 创建指定初始化容量的list
        Lists.newArrayListWithCapacity(10);

        // 创建一个根据大小增幅的list
        Lists.newArrayListWithExpectedSize(10);

        // 链表linklist
        Lists.newLinkedList();

        // 线程安全list
        Lists.newCopyOnWriteArrayList();

        // 将字符串转为List<Char>
        for (Character character : Lists.charactersOf("123")) {
            System.out.println(character);
        }

        // 按照长度将list分割成多个list，子list长度为参数长度
        for (List<String> list : Lists.partition(Lists.newArrayList("123", "456", "789"), 2)) {
            System.out.println(list);
        }

        // 将list中的元素转换，function函数参数，等同于list.stream.map
        for (String s : Lists.transform(Lists.newArrayList(1, 2, 3, 4), Functions.toStringFunction())) {
            System.out.println(s);
        }

        // 迪尔卡积
        for (List<Integer> list : Lists.cartesianProduct(Lists.newArrayList(1, 4, 7), Lists.newArrayList(2, 5, 8), Lists.newArrayList(3, 6, 9))) {
            System.out.println(list);
        }

    }


    @Test
    void test2(){

        HashSet<Integer> integers = Sets.newHashSet(1, 2, 3);

        HashSet<Integer> arrayList = Sets.newHashSet(3, 4, 5);

        Sets.SetView<Integer> difference = Sets.difference(integers, arrayList);
        Sets.SetView<Integer> difference2 = Sets.difference(arrayList, integers);

        // 差集
        System.out.println(difference);
        System.out.println(difference2);

        Sets.SetView<Integer> union = Sets.union(integers, arrayList);

        // 并集
        System.out.println(union);

        Sets.SetView<Integer> intersection = Sets.intersection(integers, arrayList);

        // 交集
        System.out.println(intersection);

        // 迪尔卡积
        System.out.println(Sets.cartesianProduct(integers, arrayList));

    }

    @Test
    void test3(){
        List<Integer> integers = Lists.newArrayList(1, 2, 3);

        List<Integer> arrayList = Lists.newArrayList(1, 4, 5);

        // intersection
        integers.addAll(arrayList);

        System.out.println(integers);

        integers = Lists.newArrayList(1, 2, 3);

        arrayList = Lists.newArrayList(1, 4, 5);

        // difference
        integers.removeAll(arrayList);

        System.out.println(integers);

        integers = Lists.newArrayList(1, 2, 3);

        arrayList = Lists.newArrayList(1, 4, 5);

        // union
        integers.retainAll(arrayList);

        System.out.println(integers);
    }
}
