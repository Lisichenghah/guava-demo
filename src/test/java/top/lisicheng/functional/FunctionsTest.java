package top.lisicheng.functional;

import com.google.common.base.Functions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsTest {

    @Test
    void test() {

        Map<String, Object> map = new HashMap<String, Object>() {{
            put("k1", "v1");
            put("k2", null);
        }};

        // 转换String，调用目标toString()方法
        assertEquals(Functions.toStringFunction().apply(123), "123");

        // 返回本体
        // 等于java.util.function.Function  t -> t
        assertEquals(Functions.identity().apply("identity"), "identity");

        // 返回输入值，与原值没有关系
        // 等于java.util.function.Function t -> "123"
        assertEquals(Functions.constant("constant").apply(2), "constant");

        // 用原值当做key，获取map中的value
        // 如没有获取到结果，throws IllegalArgumentException
        // 等于java.util.function.Function t -> map.get(t)
        assertEquals(Functions.forMap(map).apply("k1"), "v1");
        assertNull(Functions.forMap(map).apply("k2"));
        assertThrows(IllegalArgumentException.class, () -> Functions.forMap(map).apply("k3"));

        // 与上面示例类似，不同的是如果没有获取到结果，或返回默认值
        // 等于java.util.function.Function t -> map.get(t) == null ? "默认值" : map.get(t)
        assertEquals(Functions.forMap(map, "默认值").apply("k3"), "默认值");

        /* ---------------以下为高级用法----------------- */

        // 整合两个function操作，A->B->C
        assertEquals(Functions.compose(Functions.identity(), Functions.toStringFunction()).apply(1_000), "1000");

        // 通过传入的predicate函数，与原值计算返回boolean作为function的返回值（predicate返回的是boolean，该方法返回的是Boolean，且【一定不会】存在拆包NPE）
        // 该代码报黄，是因为提示拆包NPE
        assertTrue(Functions.forPredicate("123"::equals).apply("123"));

        // 返回Supplier函数的返回值，与原值无关，类似于Functions.constant()。不同是一个是普通参数，一个是函数参数
        assertEquals(Functions.forSupplier(() -> "454").apply("123"), "454");
    }


}
