package io.oz.mynorj.func;

import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;
import java.util.Map;
import java.util.HashMap;

public class UseFunc extends Func {

    public static void sign() {
        new UseFunc().signIn();
    }

    @Override
    public Object runOn(Tub tub, Box box) {
        System.out.println("__[o0] use function is running");
        return "";
    }

    private void signIn() {
        register("use");
    }
}

