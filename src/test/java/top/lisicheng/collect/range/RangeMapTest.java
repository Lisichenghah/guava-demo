package top.lisicheng.collect.range;

import com.google.common.collect.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class RangeMapTest {

    @Test
    void test() {

        TreeRangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closedOpen(0, 60), "C");
        rangeMap.put(Range.closedOpen(60, 80), "B");
        rangeMap.put(Range.closedOpen(80, 100), "A");

        Assertions.assertEquals(rangeMap.get(71), "B");

    }

}
