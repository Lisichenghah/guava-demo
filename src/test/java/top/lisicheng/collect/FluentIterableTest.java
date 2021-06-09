package top.lisicheng.collect;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.lang.System.out;

class FluentIterableTest {

    private List<Integer> list = Lists.newArrayList(1, 2, 3);

    @Test
    void testCreate() {

        FluentIterable<Object> of = FluentIterable.of();

        of.forEach(out::println);

        out.println("-----");

        FluentIterable<Integer> concat = FluentIterable.concat(list);

        concat.forEach(out::println);

        out.println("-----");

        FluentIterable<Integer> concat2 = FluentIterable.concat(list, list);

        concat2.forEach(out::println);

        out.println("------");

        FluentIterable<String> concat3 = FluentIterable.concat(Lists.newArrayList(Lists.newArrayList("1", "2", "3"), Lists.newArrayList("4", "5", "c")));

        concat3.forEach(out::println);

        out.println("------");

        FluentIterable<Integer> from = FluentIterable.from(list);

        from.forEach(out::println);

    }

    @Test
    void test2() {
        FluentIterable<String> strings = FluentIterable.from(Lists.newArrayList(1, 2, 3)).transformAndConcat(this::create);
        out.println(strings);
    }

    private List<String> create(int a) {
        String s = String.valueOf(a);
        return Lists.newArrayList(s, s, s);
    }



}
