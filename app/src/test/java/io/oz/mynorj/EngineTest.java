package io.oz.mynorj;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

public class EngineTest {
    @Test public void test_run() {
        new Engine().run("src/test/resources/reader_sample.txt");
    }
}
