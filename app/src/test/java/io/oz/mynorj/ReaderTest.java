package io.oz.mynorj;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

public class ReaderTest {
    @Test public void test_read_when_ever_then_read_file_and_return_lines() {
        List<String[]> lines = new Reader().read("src/test/resources/reader_sample.txt");
        assertEquals(2, lines.size());
        String[] line = lines.get(0);
        assertEquals(4, line.length);
        assertEquals("use", line[0]);
        assertEquals("a", line[1]);
        assertEquals("from", line[2]);
        assertEquals("my.input.firstValue", line[3]);
        line = lines.get(1);
        assertEquals(5, line.length);
        assertEquals("if", line[0]);
        assertEquals("compare", line[1]);
        assertEquals("a", line[2]);
        assertEquals(">", line[3]);
        assertEquals("b", line[4]);
    }
}
