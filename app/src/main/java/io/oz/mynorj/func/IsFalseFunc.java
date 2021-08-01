package io.oz.mynorj.func;

import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class IsFalseFunc extends Func {

    public static void sign() {
        new IsFalseFunc().register();
    }

    private IsFalseFunc() {
        super("isFalse");
    }
}

