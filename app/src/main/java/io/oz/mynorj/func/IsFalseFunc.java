package io.oz.mynorj.func;

import io.oz.mynorj.ExecutionException;
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

    @Override
    public Object runOn(Tub tub, Box box) {
        if (tub.remain() < 1) {
            throw new ExecutionException("Argument missing! Try again with:  isFalse <any>");
        }
        String arg = tub.pop();
        return "false".equals(arg);
    }
}

