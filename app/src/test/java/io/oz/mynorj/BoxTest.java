package io.oz.mynorj;

import static org.junit.Assert.*;
import org.junit.Test;

public class BoxTest {

    static {
        new MyFunc().pRegister();
    }

    @Test
    public void test_end_when_the_box_is_running_then_turn_it_off_and_store_outcome() {
        Box b = new Box();
        assertTrue(b.running());
        b.end(false);
        assertFalse(b.running());
        assertFalse(b.outcome());
    }

    @Test
    public void test_end_when_the_box_is_not_running_then_throw_exception() {
        Box b = new Box();
        b.end(false); // stop the box
        assertThrows(ExecutionException.class, () -> b.end(false));
    }

    @Test
    public void test_running_when_the_box_running_then_return_true() {
        Box b = new Box(); // the box was born running :P
        assertTrue(b.running());
    }

    @Test
    public void test_running_when_the_box_not_running_then_return_false() {
        Box b = new Box();
        b.end(true);
        assertFalse(b.running());
    }

    @Test
    public void test_outcome_when_the_box_still_running_then_throw_exception() {
        Box b = new Box();
        assertThrows(ExecutionException.class, () -> b.outcome());
    }

    @Test
    public void test_outcome_when_the_box_stoped_then_return_given_outcome() {
        Box b = new Box();
        b.end(false);
        assertFalse(b.outcome());
    }

    @Test
    public void test_getFunc_when_given_name_exists_then_return_function() {
        Func f = new Box().getFunc("my");
        assertNotNull(f);
        assertTrue(f instanceof MyFunc);
    }

    @Test
    public void test_getFunc_when_given_name_does_not_exists_then_throw_exception() {
        assertThrows(ExecutionException.class, () -> new Box().getFunc("unreal"));
    }

    @Test
    public void test_getFunc_when_the_box_silenece_then_return_silence_func() {
        Box b = new Box();
        b.silent(null);
        Func f = b.getFunc("my");
        assertEquals("SilentFunc", f.getClass().getSimpleName());
        f = b.getFunc("any");
        assertEquals("SilentFunc", f.getClass().getSimpleName());
    }

    @Test
    public void test_getFunc_when_the_box_silence_but_given_name_is_expecting_func_then_return_func() {
        Box b = new Box();
        b.silent(x -> "my".equals(x));
        Func f = b.getFunc("my");
        assertTrue(f instanceof MyFunc);
    }

    @Test
    public void test_getValue_when_given_string_is_null_then_return_null() {
        Object o = new Box().getValue(Object.class, "null");
        assertNull(o);
    }

    @Test
    public void test_getValue_when_given_string_is_null_and_there_is_a_variable_with_same_name_then_return_null() {
        Box b = new Box();
        b.newVar("null", 150d);
        Object o = b.getValue(Object.class, "null");
        assertNull(o);
    }

    @Test
    public void test_getValue_when_given_string_is_boolean_literal_then_return_boolean_value() {
        Boolean o = new Box().getValue(Boolean.class, "true");
        assertTrue(o);
        o = new Box().getValue(Boolean.class, "false");
        assertFalse(o);
    }

    @Test
    public void test_getValue_when_given_string_is_boolean_literal_and_there_is_a_variable_with_same_name_then_return_boolean_value() {
        Box b = new Box();
        b.newVar("true", 250d);
        b.newVar("false", 350d);
        Boolean o = b.getValue(Boolean.class, "true");
        assertTrue(o);
        o = b.getValue(Boolean.class, "false");
        assertFalse(o);
    }

    @Test
    public void test_getValue_when_given_string_is_number_literal_then_return_number_value() {
        Double o = new Box().getValue(Double.class, "0");
        assertEquals(0d, o, 0.0001);
        o = new Box().getValue(Double.class, "150.1982");
        assertEquals(150.1982d, o, 0.0001);
        o = new Box().getValue(Double.class, "-203.12");
        assertEquals(-203.12d, o, 0.0001);
    }

    @Test
    public void test_getValue_when_given_string_is_number_literal_and_there_is_a_variable_with_same_name_then_return_number_value() {
        Box b = new Box();
        b.newVar("350", 400d);
        Double o = b.getValue(Double.class, "350");
        assertEquals(350d, o, 0.0001);
    }

    @Test
    public void test_getValue_when_given_string_look_like_number_but_unable_to_parse_then_find_variable_with_same_name() {
        String longstring = Double.MAX_VALUE + "9999";
        Box b = new Box();
        b.newVar(longstring, 400d);
        Double o = b.getValue(Double.class, longstring);
        assertEquals(400d, o, 0.0001);
    }

    @Test
    public void test_getValue_when_given_string_is_string_literal_then_return_string_value() {
        String o = new Box().getValue(String.class, "\"thestring\"");
        assertEquals("thestring", o);
    }

    @Test
    public void test_getValue_when_given_string_is_string_literal_and_there_is_a_variable_with_same_name_then_return_null() {
        Box b = new Box();
        b.newVar("mystr", 400d);
        String o = b.getValue(String.class, "\"mystr\"");
        assertEquals("mystr", o);
    }

    @Test
    public void test_getValue_when_given_string_is_variable_name_then_return_value_that_variable_holding() {
        Box b = new Box();
        b.newVar("mystr", 400d);
        Double o = b.getValue(Double.class, "mystr");
        assertEquals(400d, o, 0.0001);
    }

    @Test
    public void test_getValue_when_given_string_is_undeclared_variable_name_then_throw_exception() {
        Box b = new Box();
        assertThrows(ExecutionException.class, () -> b.getValue(Double.class, "mystr"));
    }

    @Test
    public void test_getValue_when_expect_type_and_found_value_not_match_then_throw_exception() {
        Box b = new Box();
        // if the end value is null then the expect type not matter
        assertNull(b.getValue(Boolean.class, "null"));
        assertNull(b.getValue(Double.class, "null"));
        assertNull(b.getValue(String.class, "null"));

        ExecutionException ex = assertThrows(ExecutionException.class, () -> b.getValue(Boolean.class, "50"));
        assertEquals("Expect <50> to be a <bool> but was a <num>", ex.getMessage());
        ex = assertThrows(ExecutionException.class, () -> b.getValue(Boolean.class, "\"noyou\""));
        assertEquals("Expect <\"noyou\"> to be a <bool> but was a <string>", ex.getMessage());

        ex = assertThrows(ExecutionException.class, () -> b.getValue(Double.class, "\"na\""));
        assertEquals("Expect <\"na\"> to be a <num> but was a <string>", ex.getMessage());
        ex = assertThrows(ExecutionException.class, () -> b.getValue(Double.class, "false"));
        assertEquals("Expect <false> to be a <num> but was a <bool>", ex.getMessage());

        ex = assertThrows(ExecutionException.class, () -> b.getValue(String.class, "false"));
        assertEquals("Expect <false> to be a <string> but was a <bool>", ex.getMessage());
        ex = assertThrows(ExecutionException.class, () -> b.getValue(String.class, "10"));
        assertEquals("Expect <10> to be a <string> but was a <num>", ex.getMessage());

        b.newVar("myvar", new MyFunc());
        ex = assertThrows(ExecutionException.class, () -> b.getValue(String.class, "myvar"));
        assertEquals("Expect <myvar> to be a <string> but was a <myfunc>", ex.getMessage());
    }

    @Test
    public void test_getValue_when_expect_type_is_object_then_return_value_no_matter_what_type_it_is() {
        Box b = new Box();
        Object o = b.getValue(Object.class, "true");
        assertTrue((Boolean)o);
        o = b.getValue(Double.class, "350");
        assertEquals(350d, (Double)o, 0.0001);
        o = b.getValue(String.class, "\"mystring\"");
        assertEquals("mystring", (String)o);
    }

    @Test
    public void test_newVar_when_variable_not_exists_then_create_new_variable() {
    }

    @Test
    public void test_newVar_when_variable_already_exists_then_update_given_value_to_existing_variable() {
    }

    @Test
    public void test_pushState_whenever_then_push_the_state_to_the_box() {
    }

    @Test
    public void test_currentState_whenever_then_return_the_state_at_the_top_of_the_stack() {
    }

    @Test
    public void test_popState_when_given_state_not_match_current_state_then_do_not_remove_current_state() {
    }

    @Test
    public void test_popState_when_given_state_match_current_state_then_remove_current_state() {
    }

    @Test
    public void test_silent_whenever_then_silent_the_box_until_expect_name_come_up_or_someone_release_the_box() {
    }

    @Test
    public void test_release_whenever_then_turn_of_silent_mode_of_the_box() {
    }
    
    @Test
    public void test_getNumber_when_given_string_end_up_null_then_return_zero() {
    }

    @Test
    public void test_getNumber_when_given_string_end_up_as_a_number_then_return_that_number() {
    }

    @Test
    public void test_getNumber_when_given_string_end_up_not_null_but_not_a_number_then_throw_exception() {
    }

    private static class MyFunc extends Func {
        MyFunc() {
            super("my");
        }
        @Override
        public Object runOn(Tub t, Box b) {
            return null;
        }
        public void pRegister() {
            register();
        }
    }
}

