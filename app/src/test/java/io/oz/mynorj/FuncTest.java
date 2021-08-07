package io.oz.mynorj;

import static org.junit.Assert.*;
import org.junit.Test;

public class FuncTest {

    @Test
    public void test_register_when_got_duplicate_name_then_throw_exception() {
        FuncDeciple fd = new FuncDeciple();
        fd.publicRegister(); // first time it still ok
        RuntimeException e = assertThrows(RuntimeException.class, () -> fd.publicRegister());
        assertEquals("Function named <funcdeciple> already exists !!!", e.getMessage());
    }
    
    private static class FuncDeciple extends Func {
        FuncDeciple() {
            super("funcdeciple");
        }
        @Override
        public Object runOn(Tub tub, Box box) {
            return null;
        }
        public void publicRegister() {
            this.register();
        }
    }
}
