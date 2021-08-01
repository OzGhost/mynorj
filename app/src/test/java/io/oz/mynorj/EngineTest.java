package io.oz.mynorj;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class EngineTest {
    @Test
    public void test_run() {
        Map<String, Object> b = new HashMap<>();
        b.put("b", 550);
        Map<String, Object> a = new HashMap<>();
        a.put("a", b);
        Map<String, Object> g = new HashMap<>();
        g.put("g", a);
        boolean val = new Engine().run("src/test/resources/list_func.txt", g);
    }
}
