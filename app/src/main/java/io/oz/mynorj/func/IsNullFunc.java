package io.oz.mynorj.func;

import io.oz.mynorj.ExecutionException;
import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class IsNullFunc extends Func {

    public static void sign() {
        new IsNullFunc().register();
    }

    private IsNullFunc() {
        super("isNull");
    }

    @Override
    public Object runOn(Tub tub, Box box) {
        if (tub.remain() < 1) {
            throw new ExecutionException("Argument missing! Try again with: isNull <any>");
        }
        Object val = box.getValue(Object.class, tub.pop());
        boolean isnull = val == null;
        return isnull;
    }
}

