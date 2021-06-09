package top.lisicheng.collect;

import com.google.common.base.Functions;
import com.google.common.base.Predicates;
import com.google.common.collect.*;
import org.junit.jupiter.api.Test;

import java.util.*;

class MapsTest {

    @Test
    void test() {

        // 通过set构造map
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("2");
        Map<String, Integer> map = Maps.asMap(set, s -> s != null ? s.length() : 0);
        System.out.println(map);

        // 计算map的差异
        final MapDifference<String, String> difference = Maps.difference(new HashMap<String, String>() {{
            put("1", "2");
            put("2", "3");
            put("4", "6");
            put("5", "5");
        }}, new HashMap<String, String>() {{
            put("1", "3");
            put("2", "5");
            put("3", "4");
            put("5", "5");
        }});

        System.out.println("common:" + difference.entriesInCommon());

        System.out.println("left 独有：" + difference.entriesOnlyOnLeft());

        System.out.println("right 独有：" + difference.entriesOnlyOnRight());

        System.out.println("key相同,value不相同：" + difference.entriesDiffering());

        System.out.println("两个map是否相同：" + difference.areEqual());

        // 创建一个hashMap
        final HashMap<String, Object> hashMap = Maps.newHashMap();
        hashMap.put("1", 1);
        hashMap.put("2", 2);
        hashMap.put("34", 34);

        // 根据map中的实体过滤map
        Map<String, Object> filterEntries = Maps.filterEntries(hashMap, predicate -> predicate.getKey().length() > 1);

        // 根据map中的key过滤map
        Map<String, Object> filterKeys = Maps.filterKeys(hashMap, predicate -> predicate.length() > 1);

        // 根据map中的value过滤map
        Map<String, Object> filterValues = Maps.filterValues(hashMap, predicate -> Integer.parseInt(predicate.toString()) > 1);

        // 通过properties构造一个不可变map
        Properties properties = new Properties();
        properties.setProperty("1", "2");
        final ImmutableMap<String, String> stringStringImmutableMap = Maps.fromProperties(properties);
        System.out.println(stringStringImmutableMap);

        // 创建一个线程安全的map
        Maps.newConcurrentMap();

        // 创建一个treeMap
        Map<String, Object> treeMap = Maps.newTreeMap();
        treeMap.put("123", 1);

        // 枚举map
        Maps.newEnumMap(Enum.class);

        // 将map的value作为key，函数参数将value转换为value
        Maps.transformValues(hashMap, Functions.toStringFunction());

        // 将map的实体作为key，函数参数将实体转换为value
        Maps.transformEntries(hashMap, (key, value) -> value);

        // list的元素作为key，函数参数将元素转换为value
        Maps.toMap(Lists.newArrayList(1, 2, 3), Functions.toStringFunction());

        // list的元素作为value，参数参数将元素转换为key
        Maps.uniqueIndex(Lists.newArrayList(1, 1, 2), Functions.toStringFunction());

    }

}
