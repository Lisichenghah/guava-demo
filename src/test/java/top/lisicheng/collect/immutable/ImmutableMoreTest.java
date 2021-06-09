package top.lisicheng.collect.immutable;

import com.google.common.collect.*;
import com.google.common.reflect.ImmutableTypeToInstanceMap;
import org.junit.jupiter.api.Test;

/**
 * 其他不可变集合
 * 因构建方法大同小异，所以只列举guava中存在不可变集合种类，不再继续描述
 * ImmutableSet | 不可变Set
 * <p></p>
 * ImmutableSortedSet | 不可变排序set
 * <p></p>
 * ImmutableRangeSet | 不可变区间Set
 * <p></p>
 * ImmutableBiMap  | 不可变BiMap（可翻转map，key和value都必须是唯一的）
 * <p></p>
 * ImmutableSortedMap | 不可变排序map
 * <p></p>
 * ImmutableRangeMap | 不可变区间map
 * <p></p>
 */
class ImmutableMoreTest {

    @Test
    void test() {
        ImmutableSet<Integer> immutableSet = ImmutableSet.of(1);

        ImmutableBiMap<String, String> immutableBiMap = ImmutableBiMap.of("k1", "k2");

        ImmutableRangeMap.of(Range.open(1, 2), "a");

        ImmutableRangeSet.of(Range.open(1, 2));

        ImmutableSortedMap.of("a3", "a3V", "a", "aV", "a2", "a2V");

        // ImmutableTypeToInstanceMap.of().getInstance();
        // ImmutableClassToInstanceMap.of()
        ImmutableSortedSet<Integer> sortedSet = ImmutableSortedSet.of(2, 3, 1);
        System.out.println(sortedSet);
    }

    @Test
    void test2() {
        TreeRangeSet<Integer> treeRangeSet = TreeRangeSet.create();

        treeRangeSet.add(Range.closed(2, 3));
        treeRangeSet.add(Range.closed(5, 6));
        treeRangeSet.add(Range.closed(12, 13));

        // System.out.println(treeRangeSet);

        System.out.println(treeRangeSet.encloses(Range.closed(1, 4)));
        System.out.println(treeRangeSet.encloses(Range.closed(3, 4)));
        System.out.println(treeRangeSet.encloses(Range.closed(4, 5)));
    }

}
