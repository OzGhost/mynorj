package io.oz.mynorj;

import static org.junit.Assert.*;
import org.junit.Test;

public class TubTest {

    @Test
    public void test_constructor_if_given_elements_is_null_then_create_empty_tub() {
        Tub t = new Tub(null);
        assertTrue(t.isEmpty());
    }

    @Test
    public void test_constructor_if_given_elements_is_normal_then_create_tub_with_them() {
        Tub t = new Tub(new String[]{"a"});
        assertEquals(1, t.size());
        assertEquals("a", t.pop());
    }

    
    @Test
    public void test_pop_when_pop_empty_tub_then_throw_exeception() {
        assertThrows(ExecutionException.class, () -> new Tub(null).pop());
    }

    @Test
    public void test_pop_when_pop_exhausted_tub_then_throw_exception() {
        Tub t = new Tub(new String[]{"a"});
        assertEquals(1, t.size());
        t.pop();
        assertThrows(ExecutionException.class, () -> t.pop());
    }

    @Test
    public void test_pop_when_tub_have_not_exhausted_then_return_next_head() {
        Tub t = new Tub(new String[]{"a", "b", "c"});
        assertEquals("a", t.pop());
        assertEquals("b", t.pop());
        assertEquals("c", t.pop());
    }

    @Test
    public void test_size_whenever_then_return_the_elements_size_regardless_of_how_many_remain() {
        Tub t = new Tub(new String[]{"a", "b", "c"});
        assertEquals(3, t.size());
        t.pop();
        assertEquals(3, t.size());
        t.pop();
        assertEquals(3, t.size());
    }

    @Test
    public void test_remain_whenever_then_return_how_many_element_left() {
        Tub t = new Tub(new String[]{"a", "b", "c"});
        assertEquals(3, t.remain());
        t.pop();
        assertEquals(2, t.remain());
        t.pop();
        assertEquals(1, t.remain());
        t.pop();
        assertEquals(0, t.remain());
    }

    @Test
    public void test_isEmpty_when_the_tub_is_empty_then_return_true() {
        assertTrue(new Tub(null).isEmpty());
    }

    @Test
    public void test_isEmpty_when_then_tub_is_not_empty_then_return_false_regardless_of_how_many_remain() {
        Tub t = new Tub(new String[]{"a", "b", "c"});
        assertFalse(t.isEmpty());
        t.pop();
        t.pop();
        t.pop();
        assertFalse(t.isEmpty());
    }
}
