package top.lisicheng.utils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 字符配置器{@link CharMatcherTest}
 * <p></p>
 */
class SplitterTest {

    // 抑制 is marked unstable 警告
    // @SuppressWarnings("UnstableApiUsage")
    @Test
    void test() {

        // 普通
        assertEquals(Splitter.on("|").splitToList("1|2|3").size(), 3);

        // 去除内容为空格的元素
        assertEquals(Splitter.on("\\").trimResults().splitToList(" 3\\ \\4\\222\\bbbbb  ").size(), 5);

        // 去除内容为空（Empty）的元素
        assertEquals(Splitter.on(",").omitEmptyStrings().splitToList("2,,3 ,4,,5").size(), 4);

        // 去除内容为数字（CharMatcher.digit()）的元素，配置规则为 CharMatcher（字符匹配器），详情请看类说明
        assertEquals(Splitter.on("[").trimResults(CharMatcher.digit()).splitToList("q[w[1").size(), 3);

        // 只需要2两位
        assertEquals(Splitter.on("]").limit(2).splitToList("z]x]c").size(), 2);

        // 按照长度截取，长度不足会自动丢弃
        assertEquals(Splitter.fixedLength(3).splitToList("2,2,3,4").get(0), "2,2");
        assertEquals(Splitter.fixedLength(3).splitToList("2,2,3,4").get(1), ",3,");
        assertEquals(Splitter.fixedLength(3).splitToList("2,2,3,4").get(2), "4");

        // 刚够
        assertEquals(Splitter.fixedLength(4).splitToList("12345678").get(0), "1234");
        assertEquals(Splitter.fixedLength(4).splitToList("12345678").get(1), "5678");

        // 大于字符长度
        assertEquals(Splitter.fixedLength(10).splitToList("2,2,3,4").get(0), "2,2,3,4");

        // 按照&为第一次分割，=为第二次分割，转换为map
        Map<String, String> map = Splitter.on("&").withKeyValueSeparator("=").split("name1=value1&name2=value2&name3=value3");
        assertEquals(map.toString(), "{name1=value1, name2=value2, name3=value3}");

        // 二次分割，结果大于2，throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> Splitter.on("&").withKeyValueSeparator("=").split("name1=value1=name1-2&name2=value2&name3=value3"));
    }

}
