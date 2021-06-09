package top.lisicheng.collect.immutable;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collector;


/**
 * 不可变map（只读map）
 * </p>
 * 不允许新增，修改，删除操作。调用会抛出异常
 */
class ImmutableMapTest {

    @Test
    void testOf() {

        // 空
        ImmutableMap<String, String> emptyMap = ImmutableMap.of();

        Assertions.assertEquals(emptyMap.size(), 0);

        // 执行写操作会抛出UnsupportedOperationException
        try {
            emptyMap.put("1", "2");
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof UnsupportedOperationException);
        }

        // 重复添加相同key，会抛出异常IllegalArgumentException
        try {
            ImmutableMap<String, String> of2 = ImmutableMap.of("k1", "v1", "k1", "v2");
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof IllegalArgumentException);
        }


        // 单次最大五个元素
        ImmutableMap<String, String> of = ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4", "k5", "v5");

        Assertions.assertEquals(of.size(), 5);

        System.out.println(of);
    }

    @Test
    void testCopyOf() {

        ImmutableMap<String, String> ofMap = ImmutableMap.of("A", "1", "b", "2");

        Assertions.assertEquals(ofMap.size(), 2);

        ImmutableMap<String, String> copyOfMap = ImmutableMap.copyOf(ofMap);

        Assertions.assertEquals(copyOfMap.size(), 2);

        // 抑制 is marked unstable api（beta版本）
        // @SuppressWarnings("UnstableApiUsage")
        ImmutableMap<String, String> copyOfByEntryMap = ImmutableMap.copyOf(ofMap.entrySet());

        Assertions.assertEquals(copyOfByEntryMap.size(), 2);

    }

    @Test
    void testBuilder() {

        ImmutableMap<String, String> ofMap = ImmutableMap.of("A", "1", "b", "2");

        ImmutableMap<Object, Object> buildMap = ImmutableMap.builder()
                .put("k1", "v1")
                .put("k2", "v2")
                .putAll(ofMap)
                .orderEntriesByValue(Ordering.usingToString())
                .build();

        Assertions.assertEquals(buildMap.size(), 4);

        System.out.println(buildMap.keySet());

        ImmutableMap<String, String> ofMap2 = ImmutableMap.of("A", "3", "b", "4");

        try {
            ImmutableMap<Object, Object> putAllBeginMap = ImmutableMap.builder()
                    .putAll(ofMap)
                    // 重复添加相同key，会抛出异常
                    .putAll(ofMap2.entrySet())
                    .build();
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    /**
     * 还没弄懂
     */
    @Test
    void testToImmutableMap() {
        ImmutableMap.toImmutableMap(Functions.toStringFunction(), Functions.toStringFunction());

        ImmutableMap.toImmutableMap(Functions.toStringFunction(), Functions.toStringFunction(), String::concat);
    }


}
