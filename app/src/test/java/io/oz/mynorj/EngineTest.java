package io.oz.mynorj;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class EngineTest {

    @Test
    public void test_static_block_when_loaded_then_engine_will_register_all_the_listed_func() {
        new Engine();
        assertNotNull(Func.get("use"));
        assertNotNull(Func.get("if"));
        assertNotNull(Func.get("else"));
        assertNotNull(Func.get("endif"));
        assertNotNull(Func.get("set"));
        assertNotNull(Func.get("compare"));
        assertNotNull(Func.get("combine"));
        assertNotNull(Func.get("isNull"));
        assertNotNull(Func.get("isFalse"));
        assertNotNull(Func.get("exit"));
        assertNotNull(Func.get("multiply"));
        assertNotNull(Func.get("add"));
        assertNotNull(Func.get("divide"));
    }

    @Test
    public void test_run_when_hit_exit_before_run_out_of_line_then_stop_execute_the_remain_func() {
        boolean out = new Engine().run("src/test/resources/engine_test_early_exit.txt", null);
        assertTrue(out);
    }

    @Test
    public void test_run_when_hit_empty_line_then_ignore_the_line_but_still_count_up() {
        ExecutionException ex = assertThrows(ExecutionException.class, () -> new Engine().run("src/test/resources/engine_test_ignore_empty_line.txt", null));
        assertEquals("Line 9: Function named <unreal> does not exists.", ex.getMessage());
    }

    @Test
    public void test_run_when_hit_exit_at_the_end_of_the_file_then_exit_normally() {
        boolean out = new Engine().run("src/test/resources/engine_test_normal_exit.txt", null);
        assertTrue(out);
    }
}
