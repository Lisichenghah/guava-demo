package top.lisicheng.utils;

import com.google.common.base.Joiner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Joiner工具类的使用列举
 */
@SpringBootTest
class JoinerTest {

    @Test
    void test() {
        // 普通join
        assertEquals(Joiner.on("|").join("a", "b", "c"), "a|b|c");

        // 忽略null
        assertEquals(Joiner.on(",").skipNulls().join("1", 2, null, "3"), "1,2,3");

        // 将null替换为指定内容
        assertEquals(Joiner.on("-").useForNull("isNull").join(Arrays.asList('q', 'w', 'e', null)), "q-w-e-isNull");

        // 返回StringBuilder
        assertEquals(Joiner.on(".").appendTo(new StringBuilder(), "1", 2, 3, "DIDI").toString(), "1.2.3.DIDI");

        // 返回指定类型必须是 implement Appendable (如StringBuffer),且 throws IOException
        try {
            assertEquals(Joiner.on(".").appendTo(new StringBuffer(), "1", 2, 3, "DIDI").toString(), "1.2.3.DIDI");
        } catch (IOException e) {
            // exception
        }

        Map<String, Object> map = new LinkedHashMap<String, Object>() {{
            put("name1", "value1");
            put(null, "value2");
            put("name3", null);
        }};

        // join map ,withKeyValueSeparator(key与value的拼接符)
        assertEquals(Joiner.on("、").withKeyValueSeparator("=").useForNull("isNull").join(map), "name1=value1、isNull=value2、name3=isNull");

        // join map时，如果对map中的null不做处理，会 throws NullPointException
        assertThrows(NullPointerException.class, () -> Joiner.on("&").withKeyValueSeparator("=").appendTo(new StringBuilder(), map.entrySet()));
    }

}
