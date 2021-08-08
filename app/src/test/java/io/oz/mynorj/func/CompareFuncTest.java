package io.oz.mynorj.func;

import static org.junit.Assert.*;
import org.junit.Test;
import io.oz.mynorj.Tub;
import io.oz.mynorj.Box;
import io.oz.mynorj.ExecutionException;

public class CompareFuncTest {

    @Test
    public void test_runOn_when_argument_missing_then_throw_exception() {
        assertThrows(ExecutionException.class, () -> new CompareFunc().runOn(new Tub(null), null));
    }

    @Test
    public void test_runOn_when_operation_does_not_one_of_supported_then_throw_exception() {
        assertThrows(ExecutionException.class, () -> new CompareFunc().runOn(new Tub(new String[]{"a", "b", "c"}), null));
    }

    @Test
    public void test_runOn_when_one_of_the_given_number_is_not_number_then_throw_exception() {
        assertThrows(ExecutionException.class, () -> new CompareFunc().runOn(new Tub(new String[]{"true", "=", "true"}), new Box()));
    }

    @Test
    public void test_runOn_when_given_number_is_null_then_treat_it_as_zero() {
        Object o = new CompareFunc().runOn(new Tub(new String[]{"null", "=", "0"}), new Box());
        assertTrue((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"0", "=", "null"}), new Box());
        assertTrue((Boolean)o);
    }

    @Test
    public void test_runOn_when_argument_is_ok_then_return_comparison_result_of_given_args() {
        Object o = new CompareFunc().runOn(new Tub(new String[]{"10", ">", "1"}), new Box());
        assertTrue((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"1", ">", "10"}), new Box());
        assertFalse((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"5", "<", "8"}), new Box());
        assertTrue((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"8", "<", "5"}), new Box());
        assertFalse((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"128", "=", "128"}), new Box());
        assertTrue((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"128", "=", "129"}), new Box());
        assertFalse((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"0", "!=", "102"}), new Box());
        assertTrue((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"102", "!=", "102"}), new Box());
        assertFalse((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"30", ">=", "30"}), new Box());
        assertTrue((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"30", ">=", "29"}), new Box());
        assertTrue((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"29", ">=", "30"}), new Box());
        assertFalse((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"18", "<=", "19"}), new Box());
        assertTrue((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"18", "<=", "18"}), new Box());
        assertTrue((Boolean)o);
        o = new CompareFunc().runOn(new Tub(new String[]{"19", "<=", "18"}), new Box());
        assertFalse((Boolean)o);
    }
}

