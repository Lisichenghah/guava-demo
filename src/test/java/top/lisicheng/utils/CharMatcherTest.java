package top.lisicheng.utils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Functions;
import com.google.common.base.Predicates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharMatcherTest {

    private final static String STR = "  ROCKY  rocky  RoCkY ~!@#$%^&*() 123 ";

    @Test
    void test() {

        System.out.println(CharMatcher.forPredicate(Predicates.compose(Predicates.containsPattern("^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$"), Functions.toStringFunction()))
                .retainFrom("2019年马拉松赛事评定公布“健康中国”系列赛36场赛事上榜"));

        String a = CharMatcher.forPredicate(Predicates.compose(Predicates.containsPattern("^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$"), Functions.toStringFunction()))
                .retainFrom("旅游 | 篝火晚会、梦幻烟花秀……一大波景区夜游活动即将上线");

        String b = CharMatcher.forPredicate(Predicates.compose(Predicates.containsPattern("^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$"), Functions.toStringFunction()))
                .retainFrom("旅游|篝火晚会、梦幻烟花秀……一大波景区夜游活动即将上线");

        assertEquals(a,b);

        assertEquals(CharMatcher.digit().retainFrom("javaControl2333"), "2333");

        System.out.println(CharMatcher.is('-').or(CharMatcher.is(' ')).removeFrom("鄂D-7 9122"));

        System.out.println(CharMatcher.is(' ').removeFrom("皖 G13180"));

        System.out.println(CharMatcher.anyOf("车牌号：").removeFrom("车牌号：1234556"));


        System.out.println(CharMatcher.javaIsoControl().removeFrom("a\b\n\t"));

        // {@code} digit() 匹配数字；retainFrom保留匹配
        assertEquals(CharMatcher.javaDigit().retainFrom(STR), "123");

        // removeFrom删除匹配
        assertEquals(CharMatcher.javaDigit().removeFrom(STR), "  ROCKY  rocky  RoCkY ~!@#$%^&*()  ");

        // replaceFrom替换匹配，字符上有三个数字，则有三个匹配除，则将所有匹配处替换为d
        assertEquals(CharMatcher.javaDigit().replaceFrom(STR, "d"), "  ROCKY  rocky  RoCkY ~!@#$%^&*() ddd ");

        // collapseFrom替换匹配，将连续匹配！！！（即一串数字）替换为D
        assertEquals(CharMatcher.javaDigit().collapseFrom(STR + " ()999，1,2", 'D'), "  ROCKY  rocky  RoCkY ~!@#$%^&*() D  ()D，D,D");

        // trimFrom去除首尾匹配的字符，直到首尾没有匹配字符
        assertEquals(CharMatcher.javaDigit().trimFrom("123,456,789"), ",456,");
        assertEquals(CharMatcher.javaDigit().trimFrom("123456789"), "");

        // trimLeadingFrom去除首部匹配的字符
        assertEquals(CharMatcher.javaDigit().trimLeadingFrom("123,456,789"), ",456,789");

        // trimTrailingFrom去除尾部匹配的字符
        assertEquals(CharMatcher.javaDigit().trimTrailingFrom("123,456,789"), "123,456,");

        // trimAndCollapseFrom先去除首尾匹配的字符，再替换连续匹配的字符
        assertEquals(CharMatcher.javaDigit().trimAndCollapseFrom("123,456,789", '|'), ",|,");


    }

}
