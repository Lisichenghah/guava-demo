package top.lisicheng.collect;

import com.google.common.collect.*;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class Collections2Test {

    @Test
    void test() {

        // Collections2.
        //  FluentIterable.

        // Map<String, Object> treeMap = Maps.newTreeMap();
        // treeMap.put("2",2);
        // treeMap.put("1", 1);
        // treeMap.put("a",2);
        // treeMap.put("c",3);
        // treeMap.put("3",2);
        // treeMap.put("b",3);
        //
        // System.out.println(treeMap);
        //
        // ImmutableMap<String, String> of = ImmutableMap.of("k", "v");

        List<Integer> list = IntStream.rangeClosed(1, 100).parallel().boxed().collect(toList());

        System.out.println(list);

        Collection<List<Integer>> lists = Collections2.permutations(list);
        System.out.println(lists);
    }

}
