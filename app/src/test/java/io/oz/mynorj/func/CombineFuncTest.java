package io.oz.mynorj.func;

import static org.junit.Assert.*;
import org.junit.Test;
import io.oz.mynorj.Tub;
import io.oz.mynorj.Box;
import io.oz.mynorj.ExecutionException;

public class CombineFuncTest {
    
    @Test
    public void test_runOn_when_argument_missing_then_throw_exception() {
        assertThrows(ExecutionException.class, () -> new CombineFunc().runOn(new Tub(null), null));
    }

    @Test
    public void test_runOn_when_given_operation_not_good_then_throw_exception() {
        assertThrows(ExecutionException.class, () -> new CombineFunc().runOn(new Tub(new String[]{"a", "b", "c"}), null));
    }

    @Test
    public void test_runOn_when_one_of_given_param_is_null_then_treat_it_as_false() {
        Object o = new CombineFunc().runOn(new Tub(new String[]{"true", "and", "null"}), new Box());
        assertFalse((Boolean)o);
        o = new CombineFunc().runOn(new Tub(new String[]{"null", "and", "true"}), new Box());
        assertFalse((Boolean)o);
    }

    @Test
    public void test_runOn_when_all_argument_is_good_then_return_expected_combine_of_them() {
        Object
        o = new CombineFunc().runOn(new Tub(new String[]{"true", "and", "true"}), new Box());
        assertTrue((Boolean)o);
        o = new CombineFunc().runOn(new Tub(new String[]{"true", "and", "false"}), new Box());
        assertFalse((Boolean)o);
        o = new CombineFunc().runOn(new Tub(new String[]{"false", "and", "false"}), new Box());
        assertFalse((Boolean)o);
        o = new CombineFunc().runOn(new Tub(new String[]{"false", "and", "true"}), new Box());
        assertFalse((Boolean)o);

        o = new CombineFunc().runOn(new Tub(new String[]{"true", "or", "true"}), new Box());
        assertTrue((Boolean)o);
        o = new CombineFunc().runOn(new Tub(new String[]{"true", "or", "false"}), new Box());
        assertTrue((Boolean)o);
        o = new CombineFunc().runOn(new Tub(new String[]{"false", "or", "false"}), new Box());
        assertFalse((Boolean)o);
        o = new CombineFunc().runOn(new Tub(new String[]{"false", "or", "true"}), new Box());
        assertTrue((Boolean)o);
    }
}

