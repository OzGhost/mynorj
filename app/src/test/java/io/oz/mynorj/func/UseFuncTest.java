package io.oz.mynorj.func;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.oz.mynorj.Tub;
import io.oz.mynorj.Box;
import io.oz.mynorj.ExecutionException;

public class UseFuncTest {

    private static final JsonNode ground;

    static {
        try {
            ground = new ObjectMapper().readTree(new File("src/test/resources/usefunc_subject.txt"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_runOn_when_missing_argument_then_throw_exception() {
        assertThrows(ExecutionException.class, () -> new UseFunc().runOn(new Tub(null), null));
    }

    @Test
    public void test_runOn_when_syntax_error_then_throw_exception() {
        Tub t = new Tub(new String[]{"a", "b", "c"});
        assertThrows(ExecutionException.class, () -> new UseFunc().runOn(t, null));
    }

    @Test
    public void test_runOn_when_given_path_is_fine_then_return_value_at_path_end() {
        Box b = new Box();
        b.setGround(ground);
        Tub t = new Tub(new String[]{"m", "from", "obj.1st"});
        UseFunc uf = new UseFunc();
        uf.runOn(t, b);
        assertEquals(Boolean.TRUE, b.getValue(Boolean.class, "m"));

        t = new Tub(new String[]{"m", "from", "obj.2nd"});
        uf.runOn(t, b);
        assertEquals(300d, b.getValue(Double.class, "m"), 0.0001);

        t = new Tub(new String[]{"m", "from", "obj.3rd"});
        uf.runOn(t, b);
        assertEquals("noyou", b.getValue(String.class, "m"));

        t = new Tub(new String[]{"m", "from", "obj.4th"});
        uf.runOn(t, b);
        assertNull(b.getValue(Object.class, "m"));

        t = new Tub(new String[]{"m", "from", "arr.0.sep"});
        uf.runOn(t, b);
        assertEquals("past", b.getValue(String.class, "m"));

        t = new Tub(new String[]{"m", "from", "arr.0.dec"});
        uf.runOn(t, b);
        assertEquals("future", b.getValue(String.class, "m"));

        t = new Tub(new String[]{"m", "from", "arr.1.aug"});
        uf.runOn(t, b);
        assertEquals("present", b.getValue(String.class, "m"));
    }

    @Test
    public void test_runOn_when_given_path_does_not_point_to_a_leaf_then_return_null() {
        Box b = new Box();
        b.setGround(ground);
        Tub t = new Tub(new String[]{"m", "from", "obj"});
        new UseFunc().runOn(t, b);
        assertNull(b.getValue(Object.class, "m"));
    }

    @Test
    public void test_runOn_when_given_path_exceed_the_ground_then_return_null() {
        Box b = new Box();
        b.setGround(ground);
        Tub t = new Tub(new String[]{"m", "from", "obj.1st.and.beyond"});
        new UseFunc().runOn(t, b);
        assertNull(b.getValue(Object.class, "m"));
    }

    @Test
    public void test_runOn_when_given_path_contain_nonexists_array_index_then_return_null() {
        Box b = new Box();
        b.setGround(ground);
        Tub t = new Tub(new String[]{"m", "from", "arr.9.aug"});
        new UseFunc().runOn(t, b);
        assertNull(b.getValue(Object.class, "m"));
    }

    @Test
    public void test_runOn_when_given_path_contain_nonexists_property_then_return_null() {
        Box b = new Box();
        b.setGround(ground);
        Tub t = new Tub(new String[]{"m", "from", "arr.0.jul"});
        new UseFunc().runOn(t, b);
        assertNull(b.getValue(Object.class, "m"));
    }

    @Test
    public void test_runOn_when_given_path_contain_unreal_index_then_return_null() {
        Box b = new Box();
        b.setGround(ground);
        Tub t = new Tub(new String[]{"m", "from", "arr.3000000000.jul"});
        new UseFunc().runOn(t, b);
        assertNull(b.getValue(Object.class, "m"));
    }
}

