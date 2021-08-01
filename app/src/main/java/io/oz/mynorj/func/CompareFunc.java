package io.oz.mynorj.func;

import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class CompareFunc extends Func {

    public static void sign() {
        new CompareFunc().register();
    }

    private CompareFunc() {
        super("compare");
    }
}

