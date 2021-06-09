package top.lisicheng.collect.range;

import com.google.common.collect.TreeBasedTable;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.SortedMap;

class TableTest {

    @Test
    void testCreate(){

        TreeBasedTable<String, String, String> comparableComparableObjectTreeBasedTable = TreeBasedTable.create();
        comparableComparableObjectTreeBasedTable.put("k1","k2","v1");
        SortedMap<String, String> k1 = comparableComparableObjectTreeBasedTable.row("k1");

        System.out.println(k1);

        Map<String, String> k2 = comparableComparableObjectTreeBasedTable.column("k2");

        System.out.println(k2);

    }

}
