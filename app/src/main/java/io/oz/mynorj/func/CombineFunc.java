package io.oz.mynorj.func;

import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class CombineFunc extends Func {

    public static void sign() {
        new CombineFunc().register();
    }

    private CombineFunc() {
        super("combine");
    }
}

