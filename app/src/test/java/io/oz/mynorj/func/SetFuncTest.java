package io.oz.mynorj.func;

import static org.junit.Assert.*;
import org.junit.Test;
import io.oz.mynorj.Tub;
import io.oz.mynorj.Box;
import io.oz.mynorj.Func;
import io.oz.mynorj.ExecutionException;

public class SetFuncTest {
    static {
        new MyFunc().pRegisiter();
    }

    @Test
    public void test_runOn_when_given_func_return_something_then_that_something_will_be_store_to_var() {
        Box b = new Box();
        new SetFunc().runOn(new Tub(new String[]{"x", "=", "pmy", "null"}), b);
        assertNull(b.getValue(Object.class, "x"));
        new SetFunc().runOn(new Tub(new String[]{"x", "=", "pmy", "true"}), b);
        assertTrue(b.getValue(Boolean.class, "x"));
        new SetFunc().runOn(new Tub(new String[]{"x", "=", "pmy", "500"}), b);
        assertEquals(500d, b.getValue(Double.class, "x"), 0.0001);
        new SetFunc().runOn(new Tub(new String[]{"x", "=", "pmy", "\"noyou\""}), b);
        assertEquals("noyou", b.getValue(String.class, "x"));
    }

    @Test
    public void test_runOn_when_given_func_is_unsupported_function_then_throw_exception() {
        assertThrows(ExecutionException.class, () -> new SetFunc().runOn(new Tub(new String[]{"x", "=", "unreal"}), new Box()));
    }

    @Test
    public void test_runOn_when_second_args_is_not_equal_sign_then_throw_exception() {
        assertThrows(ExecutionException.class, () -> new SetFunc().runOn(new Tub(new String[]{"x", "+", "unreal"}), new Box()));
    }

    @Test
    public void test_runOn_when_argument_missing_then_throw_exception() {
        assertThrows(ExecutionException.class, () -> new SetFunc().runOn(new Tub(null), new Box()));
    }

    private static class MyFunc extends Func {
        MyFunc() {
            super("pmy");
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

