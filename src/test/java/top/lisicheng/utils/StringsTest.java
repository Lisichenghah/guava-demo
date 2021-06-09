package top.lisicheng.utils;

import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class StringsTest {

    @Test
    void test() {

        // 获取两个字符串公共的前缀（从第一个字符开始），没有公共前缀则返回空（empty）
        assertEquals(Strings.commonPrefix("acdf", "acef"), "ac");
        assertEquals(Strings.commonPrefix("123.jpg", "223.jpg"), "");

        // 获取两个字符串公共的后缀
        assertEquals(Strings.commonSuffix("123.jpg", "2232.jpg"), ".jpg");

        // 空字符转null
        assertNull(Strings.emptyToNull(""));

        // null转空字符
        assertEquals(Strings.nullToEmpty(null), "");

        // 判断字符是否为空或者null，是返回true
        assertTrue(Strings.isNullOrEmpty(""));

        ;// 复制n个字符
        assertEquals(Strings.repeat("ABC", 2), "ABCABC");

        // 以b字符开头连续到aaa结尾，参数长度为10（aaa长度为3，则 10-3 = 7，则字符为：7个b+aaa）
        assertEquals(Strings.padStart("aaa", 10, 'b'), "bbbbbbbaaa");
        // 如果长度少于原字符，则返回原字符
        assertEquals(Strings.padStart("aaa", 3, 'b'), "aaa");

        // 以123字符开头，参数长度为10，减原字符（123）长度（3），10-3 = 7,则字符为：123 + 7个A
        assertEquals(Strings.padEnd("123", 10, 'A'), "123AAAAAAA");
        // 如果参数长度少于原字符长度，则返回原字符
        assertEquals(Strings.padEnd("123", 2, 'A'), "123");

        // 替换字符串中的%s
        assertEquals(Strings.lenientFormat("121%s,dddd%s", "这是1", "这是2"), "121这是1,dddd这是2");
        // 数量对应不上也不会报错，多出来的参数会打印在后面
        assertEquals(Strings.lenientFormat("121%s,dddd%s", "这是1", "这是2", "这是3"), "121这是1,dddd这是2 [这是3]");
        // 后面的参数少于替换符，对应不上的还是替换符
        assertEquals(Strings.lenientFormat("121%s,dddd%s", "这是1"), "121这是1,dddd%s");

    }

}
