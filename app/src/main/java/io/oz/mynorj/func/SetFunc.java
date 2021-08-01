package io.oz.mynorj.func;

import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class SetFunc extends Func {

    public static void sign() {
        new SetFunc().register();
    }

    private SetFunc() {
        super("set");
    }
}

