package io.oz.mynorj.func;

import static org.junit.Assert.*;
import org.junit.Test;
import io.oz.mynorj.Tub;
import io.oz.mynorj.Box;
import io.oz.mynorj.ExecutionException;

public class DivideFuncTest {
    
    @Test
    public void test_runOn_when_second_number_is_0_then_throw_exception() {
        ExecutionException e = assertThrows(ExecutionException.class, () -> new DivideFunc().runOn(new Tub(new String[]{"50", "0"}), new Box()));
        assertEquals("[divide] Illegal argument! Divide by zero was forbidden!", e.getMessage());
    }

    @Test
    public void test_runOn_when_args_all_good_then_return_divide_output() {
        Object o = new DivideFunc().runOn(new Tub(new String[]{"25", "2"}), new Box());
        assertEquals(12.5d, (Double)o, 0.0001);
    }

    @Test
    public void test_runOn_when_one_of_two_given_number_is_null_then_it_will_be_treat_as_zero() {
        assertThrows(ExecutionException.class, () -> new DivideFunc().runOn(new Tub(new String[]{"50", "null"}), new Box()));
        Object o = new DivideFunc().runOn(new Tub(new String[]{"null", "2"}), new Box());
        assertEquals(0d, (Double)o, 0.0001);
    }

    @Test
    public void test_runOn_when_given_args_is_not_number_then_throw_exception() {
        assertThrows(ExecutionException.class, () -> new DivideFunc().runOn(new Tub(new String[]{"true", "0"}), new Box()));
        assertThrows(ExecutionException.class, () -> new DivideFunc().runOn(new Tub(new String[]{"0", "false"}), new Box()));
        assertThrows(ExecutionException.class, () -> new DivideFunc().runOn(new Tub(new String[]{"false", "false"}), new Box()));
    }

    @Test
    public void test_runOn_when_missing_args_then_throw_exception() {
        assertThrows(ExecutionException.class, () -> new DivideFunc().runOn(new Tub(null), new Box()));
    }
}

