package io.oz.mynorj.func;

import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class MultiplyFunc extends Func {

    public static void sign() {
        new MultiplyFunc().register();
    }

    private MultiplyFunc() {
        super("multiply");
    }
}

