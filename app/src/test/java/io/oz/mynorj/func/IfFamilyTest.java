package io.oz.mynorj.func;

import static org.junit.Assert.*;
import org.junit.Test;
import io.oz.mynorj.Tub;
import io.oz.mynorj.Box;
import io.oz.mynorj.Func;
import io.oz.mynorj.ExecutionException;

public class IfFamilyTest {

    static {
        new MyFunc().pRegisiter();
    }
    
    @Test
    public void test_if_when_given_func_return_true_then_do_not_silent_the_box() {
        Func f = new IfFamily.IfFunc();
        Box b = new Box();
        assertTrue(b.getFunc("omy") instanceof MyFunc); // speakable
        f.runOn(new Tub(new String[]{"omy", "true"}), b);
        assertTrue(b.getFunc("omy") instanceof MyFunc); // stay speakable
    }

    @Test
    public void test_if_when_given_func_return_false_then_silent_the_box_until_else_or_endif_hit() {
        Func f = new IfFamily.IfFunc();
        Box b = new Box();
        assertTrue(b.getFunc("omy") instanceof MyFunc); // speakable
        f.runOn(new Tub(new String[]{"omy", "false"}), b);
        assertEquals("SilentFunc", b.getFunc("omy").getClass().getSimpleName()); // silence
        assertThrows(ExecutionException.class, () -> b.getFunc("else"));
        assertThrows(ExecutionException.class, () -> b.getFunc("endif"));
    }

    @Test
    public void test_if_when_given_func_return_something_not_bool_then_throw_exception() {
        Func f = new IfFamily.IfFunc();
        Box b = new Box();
        assertThrows(ExecutionException.class, () -> f.runOn(new Tub(new String[]{"omy", "500"}), b));
        assertThrows(ExecutionException.class, () -> f.runOn(new Tub(new String[]{"omy", "null"}), b));
    }

    @Test
    public void test_if_when_missing_argument_then_throw_exception() {
        Func f = new IfFamily.IfFunc();
        assertThrows(ExecutionException.class, () -> f.runOn(new Tub(null), null));
    }

    @Test
    public void test_else_when_current_state_is_ok_then_silent_the_box_until_endif_hit() {
        Func sub = new IfFamily.IfFunc();
        Box b = new Box();
        sub.runOn(new Tub(new String[]{"omy", "true"}), b); // load ok state
        assertTrue(b.getFunc("omy") instanceof MyFunc); // speakable
        Func f = new IfFamily.ElseFunc();
        f.runOn(null, b);
        assertEquals("SilentFunc", b.getFunc("omy").getClass().getSimpleName()); // silence
        assertThrows(ExecutionException.class, () -> b.getFunc("endif"));
    }

    @Test
    public void test_else_when_current_state_is_not_ok_then_release_the_box() {
        Func sub = new IfFamily.IfFunc();
        Box b = new Box();
        sub.runOn(new Tub(new String[]{"omy", "false"}), b); // load nok state
        assertEquals("SilentFunc", b.getFunc("omy").getClass().getSimpleName()); // silence
        Func f = new IfFamily.ElseFunc();
        f.runOn(null, b);
        assertTrue(b.getFunc("omy") instanceof MyFunc); // speakable
    }

    @Test
    public void test_else_when_current_state_not_what_if_put_in_then_throw_exception() {
        Box b = new Box();
        Func f = new IfFamily.ElseFunc();
        assertThrows(ExecutionException.class, () -> f.runOn(null, b)); // no state
        b.pushState("somestring");
        assertThrows(ExecutionException.class, () -> f.runOn(null, b)); // not match state
    }

    @Test
    public void test_endif_when_current_state_is_something_if_put_in_then_release_the_box() {
        Func sub = new IfFamily.IfFunc();
        Box b = new Box();
        sub.runOn(new Tub(new String[]{"omy", "false"}), b); // load nok state
        assertEquals("SilentFunc", b.getFunc("omy").getClass().getSimpleName()); // silence
        Func f = new IfFamily.EndIfFunc();
        f.runOn(null, b);
        assertTrue(b.getFunc("omy") instanceof MyFunc); // speakable

        sub.runOn(new Tub(new String[]{"omy", "true"}), b); // load ok state
        assertTrue(b.getFunc("omy") instanceof MyFunc); // speakable
        f.runOn(null, b);
        assertTrue(b.getFunc("omy") instanceof MyFunc); // stay speakable
    }

    @Test
    public void test_endif_when_current_state_is_not_what_if_put_in_then_throw_exception() {
        Box b = new Box();
        Func f = new IfFamily.EndIfFunc();
        assertThrows(ExecutionException.class, () -> f.runOn(null, b)); // no state
        b.pushState("somestring");
        assertThrows(ExecutionException.class, () -> f.runOn(null, b)); // not match state
    }

    private static class MyFunc extends Func {
        MyFunc() {
            super("omy");
        }
        @Override
        public Object runOn(Tub t, Box b) {
            return b.getValue(Object.class, t.pop());
        }
        public void pRegisiter() {
            register();
        }
    }
}

