package top.lisicheng.collect.range;

import com.google.common.collect.BoundType;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

class RangeTest {

    @Test
    void test() {

        long start = LocalDateTime.of(2020, 1, 1, 2, 0, 0).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long end = LocalDateTime.of(2020, 1, 1, 3, 0, 0).toInstant(ZoneOffset.of("+8")).toEpochMilli();

        long targetStart = LocalDateTime.of(2020, 1, 1, 2, 30, 0).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long targetEnd = LocalDateTime.of(2020, 1, 1, 4, 0, 0).toInstant(ZoneOffset.of("+8")).toEpochMilli();

        //{x|a<x<=b}
        Range<Long> range = Range.open(start, end);
        Range<Long> targetRange = Range.open(targetStart, targetEnd);

        // range.
    }

    @Test
    void testCreate() {

        // 无穷大/小没有闭环概率（无穷大没有具体数值）

        // 两端无穷大/小
        Range<Integer> all = Range.all();

        // 两端不闭环  {1< x<10}
        Range<Integer> open = Range.open(1, 10);

        // 两端闭环 {1<= x <=10}
        Range.closed(1, 10);

        // 开始不闭环，结束闭环 {1<x<=10}
        Range.openClosed(1, 10);

        // 开始闭环，结束不闭环 {1<=x<10}
        Range.closedOpen(1, 10);

        // 开始闭环为1，结束无穷大
        Range.atLeast(1);

        // 开始无穷小，结束闭环为1
        Range.atMost(1);

        // 开始不闭环为1，结束无穷大
        Range.greaterThan(1);

        // 开始无穷小，结束不闭环为1
        Range.lessThan(1);

        // 开始为1，自定义是否闭环（由第二参数，枚举决定），结束无穷大
        Range.downTo(1, BoundType.CLOSED);

        // 结束为1，自定义是否闭环（由第二参数，枚举决定），结束无穷小
        Range.upTo(1, BoundType.CLOSED);

        // 构建开始，结束都是闭环为1的区间
        Range.singleton(1);

        // 通过集合构建，开始为闭环集合最小值，结束为闭环集合最大值
        Range.encloseAll(Lists.newArrayList(1, 2, 3));
    }

    /**
     * 判断参数区间是否在调用区间内
     * <P></P>
     * 参数区间，整个都要在调用区间内（可以等于）
     * source:start:2;end:3
     * param:start:2;end:4
     * paramStart(2) <= sourceStart(2)  and  paramEnd(4) <= sourceEnd(3)
     * 上述即为false
     */
    @Test
    void testEncloses() {

        Range<Integer> open = Range.open(2, 4);

        Range<Integer> open1 = Range.open(3, 5);

        Range<Integer> open2 = Range.open(5, 6);

        Range<Integer> closed = Range.closed(3, 3);

        System.out.println(open.encloses(open));
        System.out.println(open.encloses(open1));
        System.out.println(open.encloses(open2));
        System.out.println(open.encloses(closed));
    }

    /**
     * 是否可连接（连续）
     * <p></p>
     * 例子1：A区间 = {2<x<3} ，B区间 = {3<x<4}
     * 结果：false。两区间都没有等于3，缺少3作为连续值
     * <p></p>
     * 例子2：A区间 = {2<x<=3} ，B区间 = {3<x<4}
     * 结果：true
     */
    @Test
    void testIsConnected() {

        Range<Integer> open = Range.open(2, 4);
        Range<Integer> open1 = Range.open(4, 5);
        Range<Integer> closedOpen = Range.closedOpen(5, 6);

        Assertions.assertFalse(open.isConnected(open1));

        Assertions.assertTrue(open1.isConnected(closedOpen));

        // 正反都能连接
        Assertions.assertTrue(closedOpen.isConnected(open1));

    }

    /**
     * 返回两个range的交集，如果没有交集，则会抛出异常
     * <p></p>
     * 例子：A区间 {2<x<4} ,B区间 {3<x<5}
     * 结果：3<x<4
     */
    @Test
    void testIntersection() {

        Range<Integer> open = Range.open(2, 4);

        Range<Integer> open1 = Range.open(3, 5);

        Range<Integer> open2 = Range.open(5, 6);

        Range<Integer> closed = Range.closed(3, 3);

        System.out.println(open.intersection(open1));

        System.out.println(open.intersection(closed));

        try {
            System.out.println(open.intersection(open2));
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    /**
     * 合并两个range，将两个的range的最小值，与最大值组成新的range
     * 例子：A区间={4<x<=5} , B区间={2<=x<10}
     * 结果：4<x<10
     */
    @Test
    void testSpan() {

        Range<Integer> open = Range.open(2, 4);

        Range<Integer> open1 = Range.open(3, 5);

        Range<Integer> open2 = Range.open(5, 6);

        Range<Integer> closed = Range.closed(3, 3);

        System.out.println(open.span(open1));
        System.out.println(open.span(open2));
        System.out.println(open.span(closed));

        System.out.println("--------------");

        System.out.println(open1.span(open2));
        System.out.println(open1.span(closed));
    }

    /**
     * 求两区间的间隙区间
     * <p></p>
     * 如区间交叠，则会抛出异常
     * <p></p>
     * 间隙区间 = A区间{2<x<4} (反endPoint操作符，即<变为<=，<=变为<) | 4<=x<10 | (反startPoint操作符)B区间{10<=x<11}
     * 例子：A区间={2<x<4},B区间={10<=x<11}
     * 结果为：4<=x<10
     */
    @Test
    void testGap() {
        Range<Integer> open = Range.open(2, 4);

        Range<Integer> open1 = Range.open(3, 5);

        Range<Integer> open2 = Range.open(5, 6);

        Range<Integer> closed = Range.closed(2, 4);

        Range<Integer> closed1 = Range.closed(10, 11);

        // 值交叠
        System.out.println(open.gap(open1));

        // 有区间（）
        System.out.println(open.gap(open2));

        System.out.println(closed.gap(closed1));

        System.out.println(open.gap(closed));

    }

}
