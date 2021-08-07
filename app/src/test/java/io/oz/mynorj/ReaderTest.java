package io.oz.mynorj;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

public class ReaderTest {
    @Test
    public void test_read_when_ever_then_read_file_and_return_lines() {
        List<Tub> lines = new Reader().read("src/test/resources/reader_sample.txt");
        assertEquals(5, lines.size());
        Tub line = lines.get(0);
        assertEquals(4, line.size());
        assertEquals("use", line.pop());
        assertEquals("a", line.pop());
        assertEquals("from", line.pop());
        assertEquals("my.input.firstValue", line.pop());
        line = lines.get(1);
        assertEquals(5, line.size());
        assertEquals("if", line.pop());
        assertEquals("compare", line.pop());
        assertEquals("a", line.pop());
        assertEquals(">", line.pop());
        assertEquals("b", line.pop());
        line = lines.get(2);
        assertTrue(line.isEmpty());
        line = lines.get(3);
        assertTrue(line.isEmpty());
        line = lines.get(4);
        assertEquals(1, line.size());
        assertEquals("endif", line.pop());
    }

    @Test
    public void test_read_when_unable_to_read_the_given_file_then_return_empty_list() {
        List<Tub> lines = new Reader().read("src/test/resources/unreal.txt");
        assertTrue(lines.isEmpty());
    }
}
