package top.lisicheng.cache;

import com.google.common.base.Ticker;
import com.google.common.cache.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Lists.newArrayList;

class CacheLoaderTest {

    Logger log = LoggerFactory.getLogger(CacheLoaderTest.class);

    @Test
    void test() throws ExecutionException, InterruptedException {

        LoadingCache<String, Student> build = CacheBuilder
                .newBuilder()
                // 长度，超过长度后使用最近最少使用策略
                .maximumSize(3)
                // 过期策略。读写都能保持会话
                // .expireAfterAccess(10, TimeUnit.SECONDS)
                // 过期策略。写能保持会话
                .expireAfterWrite(5, TimeUnit.SECONDS)
                // .expireAfterWrite(Duration.between(LocalTime.parse("16:48:00", DateTimeFormatter.ofPattern("HH:mm:ss")),LocalTime.parse("16:50:00",DateTimeFormatter.ofPattern("HH:mm:ss"))))
                // key软引用
                .weakKeys()
                // value软引用
                .weakValues()
                // value弱引用
                // .softValues()
                // 缓存记录，记录命中率等等
                .recordStats()
                // 失效监听器
                .removalListener(lister -> {
                    // lister.getCause() 失效原因
                    log.info(String.format("%s失效了,%s", lister.getKey(), lister.getCause().toString()));
                })
                .build(cacheLoader());

        log.info(build.get("123").toString());
        while (true) {
            Student student = build.getIfPresent("123");
            // if (student == null) {
            //     break;
            // }
            log.info(String.valueOf(student));
            TimeUnit.SECONDS.sleep(1);
        }

        // log.info(build.get("123"));

        // log.info(build.get("123"));
    }

    /**
     * 不带条件的Cache
     */
    @Test
    void noCondition() {

        LoadingCache<String, Student> loadingCache = CacheBuilder.newBuilder().build(cacheLoader());

        try {
            // 获取并抛出可能发生的异常
            log.info(loadingCache.get("123").toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 获取不抛出异常
        log.info(loadingCache.getUnchecked("456").toString());

        try {
            // 获取缓存，如缓存未命中，则使用自定义cache
            log.info(loadingCache.get("444", () -> formDB("cache")).toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            // 根据集合中元素获取缓存
            ImmutableMap<String, Student> all = loadingCache.getAll(newArrayList("1", "2", "3"));
            all.values().stream().map(String::valueOf).forEach(log::info);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 获取缓存如果存在就从缓存中取，没有则返回null
        loadingCache.getIfPresent("2");

        // 根据集合中元素获取缓存，如果存在则不会返回
        ImmutableMap<String, Student> allPresent = loadingCache.getAllPresent(newArrayList("1", "2", "3", "4"));
        allPresent.values().stream().map(String::valueOf).forEach(log::info);
    }

    @Test
    void testOfSize() {

        LoadingCache<String, Student> build = CacheBuilder.newBuilder()
                // 长度，超过长度后使用最近最少使用策略
                .maximumSize(3)
                .build(CacheLoader.from(this::formDB));

        build.getUnchecked("java");
        build.getUnchecked("js");
        build.getUnchecked("sql");

        Assertions.assertEquals(build.size(), 3L);

        build.getUnchecked("guava");

        Assertions.assertEquals(build.size(), 3L);

        Assertions.assertNull(build.getIfPresent("java"));

        Assertions.assertNotNull(build.getUnchecked("java"));

        Assertions.assertNull(build.getIfPresent("js"));

    }

    @Test
    void testOfWeight() {

        LoadingCache<String, Student> build = CacheBuilder.newBuilder()
                .maximumWeight(3)
                .weigher((key, value) -> ((Student) value).getName().length())
                // 并发级别
                .concurrencyLevel(1)
                .build(CacheLoader.from(this::formDB));

        build.getUnchecked("1");

        build.getUnchecked("2");

        build.getUnchecked("3");

        Assertions.assertEquals(build.size(), 3L);

        build.getUnchecked("123");

        Assertions.assertEquals(build.size(), 1L);
        Assertions.assertNotNull(build.getIfPresent("123"));

        // 超过重量，不能进入缓存
        build.getUnchecked("1234");

        Assertions.assertEquals(build.size(), 1L);
        Assertions.assertNull(build.getIfPresent("1234"));
        Assertions.assertNotNull(build.getIfPresent("123"));

    }

    private CacheLoader<String, Student> cacheLoader() {
        return new CacheLoader<String, Student>() {
            @Override
            public Student load(String key) throws Exception {
                return formDB(key);
            }
        };
    }

    private Student formDB(String key) {
        sleep(1, TimeUnit.SECONDS);
        return new Student(key, new Random().nextInt(20), new Random().nextBoolean());
    }

    private void sleep(long timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Student {

        private String name;

        private Integer age;

        private Boolean sex;

        public Student() {
        }

        Student(String name, Integer age, Boolean sex) {
            this.name = name;
            this.age = age;
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public Student setName(String name) {
            this.name = name;
            return this;
        }

        public Integer getAge() {
            return age;
        }

        public Student setAge(Integer age) {
            this.age = age;
            return this;
        }

        public Boolean getSex() {
            return sex;
        }

        public Student setSex(Boolean sex) {
            this.sex = sex;
            return this;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", sex=" + sex +
                    '}';
        }
    }

}
