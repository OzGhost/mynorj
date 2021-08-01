package io.oz.mynorj.func;

import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class DivideFunc extends Func {

    public static void sign() {
        new DivideFunc().register();
    }

    private DivideFunc() {
        super("divide");
    }
}

