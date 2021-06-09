package top.lisicheng.functional;

import com.google.common.base.Functions;
import com.google.common.base.Predicates;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 该类只列举【实用】或【jdk没有直接方法替代】的方法
 */
class PredicatesTest {

    @Test
    void test() {

        // 以下为类说明方法，所以不做详细说明，可顾名思义（由方法名理解其意图）
        assertTrue(Predicates.equalTo("123").apply("123"));

        Predicates.alwaysFalse().apply(123);

        Predicates.alwaysTrue().apply(123);

        Predicates.isNull().apply(null);

        Predicates.notNull().apply(null);

        /*------------------------*/

        // 判断目标参数是否包含在集合中
        assertTrue(Predicates.in(Arrays.asList("123", "456")).apply("123"));

        // 判断目标参数是否符合正则
        assertTrue(Predicates.containsPattern("1").apply("1,2,3|"));

        // 与上一样，区别在参数正则字符串和正则对象
        assertTrue(Predicates.contains(Pattern.compile("12")).apply("12/3|"));

        // 判断目标参数 instanceof String
        assertTrue(Predicates.instanceOf(String.class).apply("123"));

        // 目标参数类型是否为Object子类
        assertTrue(Predicates.subtypeOf(Object.class).apply(String.class));

        // 逻辑反
        assertTrue(Predicates.not("123"::equals).apply("456"));

        // 逻辑与
        assertTrue(Predicates.and((String predicate) -> predicate.contains("1"), (String predicate) -> predicate.contains("3")).apply("123"));

        // 逻辑或
        assertTrue(Predicates.or("123"::equals, "456"::equals).apply("456"));

        // 1，A-B
        // 2，B逻辑处理
        assertTrue(Predicates.compose("123"::equals, Functions.toStringFunction()).apply(123));
    }

}
