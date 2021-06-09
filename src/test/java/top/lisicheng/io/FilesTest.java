package top.lisicheng.io;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.hash.Hashing;
import com.google.common.io.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FilesTest {

    private final String SOURCE_PATH = "C:\\ProJects\\IdeaProjects\\练习\\guava-demo\\src\\main\\resources\\source.txt";

    private final String TARGET_PATH = "C:\\ProJects\\IdeaProjects\\练习\\guava-demo\\src\\main\\resources\\target.txt";

    @Test
    void touch() throws IOException {
        // 创建一个文件
        Files.touch(new File("test"));

        // 文件是否存在
        Assertions.assertTrue(Files.isFile().test(new File("test")));

        // 删除文件
        java.nio.file.Files.delete(Paths.get("test"));

        // 文件是否存在
        Assertions.assertFalse(Files.isFile().test(new File("test")));
    }

    @Test
    void byteSource() throws IOException {

        InputStream inputStream = Files.asByteSource(new File(SOURCE_PATH)).openStream();

        Files.asByteSource(new File(SOURCE_PATH)).hash(Hashing.goodFastHash(256));

        Files.asByteSource(new File(SOURCE_PATH)).asCharSource(UTF_8);

    }

    @Test
    void charSource() throws IOException {

        String content = "hello!\n" +
                "this is source txt.\n" +
                "没有其他说明。\n" +
                "end.";

        CharSource charSource = Files.asCharSource(new File(SOURCE_PATH), UTF_8);

        Assertions.assertEquals(charSource.read(), content);

        Assertions.assertEquals(Joiner.on("\n").join(charSource.readLines()), content);

        String readLinesByLineProcessor = charSource.readLines(new LineProcessor<String>() {

            private final StringJoiner stringJoiner = new StringJoiner("\n");

            @Override
            public boolean processLine(String line) throws IOException {

                if (line.contains("没有")) {
                    return false;
                }

                stringJoiner.add(line);

                return true;
            }

            @Override
            public String getResult() {
                return stringJoiner.toString();
            }
        });

        Assertions.assertEquals(readLinesByLineProcessor, "hello!\nthis is source txt.");

        Assertions.assertEquals(charSource.length(), 39);

        Assertions.assertEquals(charSource.readFirstLine(), "hello!");

        // stream
        charSource.lines();

        // foreach
        charSource.forEachLine(System.out::println);

    }

    @Test
    void charSink() {

        CharSink charSink = Files.asCharSink(new File(SOURCE_PATH), UTF_8);

        // charSink.w
    }

    @Test()
    void test() throws IOException {

        // 复制文件；将源文件内容复制到目标路径文件（不存在则创建）
        Files.copy(new File(SOURCE_PATH), new File(TARGET_PATH));

        // 移动文件
        Files.move(new File(SOURCE_PATH), new File(TARGET_PATH));

    }

    @Test
    void readTest() throws IOException {
        // 1.读取文件，以每行的内容为元素的list
        assertEquals(Files.readLines(new File(SOURCE_PATH), Charsets.UTF_8), Arrays.asList("hello!", "this is source txt.", "没有其他文明。", "end."));

        // 2.读取文件，以匿名函数参数为操作
        String result = Files.readLines(new File(SOURCE_PATH), Charsets.UTF_8, new LineProcessor<String>() {
            private final StringBuilder stringBuilder = new StringBuilder();

            @Override
            // 读到行，为false时停止读取
            public boolean processLine(String line) throws IOException {
                stringBuilder.append(line);
                return line.length() > 10;
            }

            @Override
            public String getResult() {
                return stringBuilder.toString();
            }
        });

        assertEquals(result, "hello!");

        // 3.该方法为1的内部实现，不同于的是该方法返回的是不可变list，且1为废弃方法
        assertEquals(Files.asCharSource(new File(SOURCE_PATH), Charsets.UTF_8).readLines(), Arrays.asList("hello!", "this is source txt.", "没有其他文明。", "end."));

        // 5.该方法为2的内部实现
        String result2 = Files.asCharSource(new File(SOURCE_PATH), Charsets.UTF_8).readLines(new LineProcessor<String>() {

            final StringBuilder stringBuilder = new StringBuilder();

            @Override
            public boolean processLine(String line) throws IOException {
                stringBuilder.append(line);
                return line.length() > 10;
            }

            @Override
            public String getResult() {
                return stringBuilder.toString();
            }
        });

        assertEquals(result2, "hello!");

    }

    /**
     * 文件hash
     *
     * @throws IOException io
     */
    @Test
    void hash() throws IOException {

        // 文件的md5
        System.out.println(Files.hash(new File(SOURCE_PATH), Hashing.md5()));

        // Files.hash是废弃方法，具体实现为该方法，加密方法Hashing提供实现，具体后续补充
        System.out.println(Files.asByteSource(new File(SOURCE_PATH)).hash(Hashing.goodFastHash(128)));

    }

    @Test
    void write() throws IOException {

        // 1.写入内容到文件
        String content = "this is content（内容）";
        Files.write(content.getBytes(Charsets.UTF_8), new File(TARGET_PATH));

        // 2.追加内容到文件
        String content2 = "\n追加的内容";
        Files.append(content2, new File(TARGET_PATH), Charsets.UTF_8);

        // 等同于1,1的内部实现
        Files.asCharSink(new File(TARGET_PATH), Charsets.UTF_8).write(content);

        // 等于与2,2的内部实现
        Files.asCharSink(new File(TARGET_PATH), Charsets.UTF_8, FileWriteMode.APPEND).write(content2);

    }

    @Test
    void jdkFilesTest() throws IOException {
        List<String> list = java.nio.file.Files.readAllLines(Paths.get(SOURCE_PATH));
        list.forEach(System.out::println);
    }

    //    @AfterEach
    void after() throws IOException {
        java.nio.file.Files.delete(new File(TARGET_PATH).toPath());
    }
}
