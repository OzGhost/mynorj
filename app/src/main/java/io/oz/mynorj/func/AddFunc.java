package io.oz.mynorj.func;

import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class AddFunc extends Func {

    public static void sign() {
        new AddFunc().register();
    }

    private AddFunc() {
        super("add");
    }
}

