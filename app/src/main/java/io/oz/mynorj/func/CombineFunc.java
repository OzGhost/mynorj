package io.oz.mynorj.func;

import io.oz.mynorj.ExecutionException;
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

    @Override
    public Object runOn(Tub tub, Box box) {
        if (tub.remain() < 3) {
            throw new ExecutionException("Argument missing! Try again with: combine <bool> <op> <bool>");
        }
        String rx = tub.pop();
        String op = tub.pop();
        String ry = tub.pop();
        if ( ! "and".equals(op) && ! "or".equals(op) ) {
            throw new ExecutionException("Syntax error! Try again with: combine <bool> <op> <bool>");
        }
        Boolean x = box.getValue(Boolean.class, rx);
        Boolean y = box.getValue(Boolean.class, ry);
        if (x == null || y == null) {
            throw new ExecutionException("Illegal argument! Expect <bool> but was <null>");
        }
        if ("and".equals(op)) {
            return x && y;
        }
        return x || y;
    }
}

