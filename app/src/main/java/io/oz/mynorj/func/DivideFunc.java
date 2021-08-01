package io.oz.mynorj.func;

import io.oz.mynorj.ExecutionException;
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

    @Override
    public Object runOn(Tub tub, Box box) {
        if (tub.remain() < 2) {
            throw new ExecutionException("Argument missing! Try again with: divide <num> <num>");
        }
        String rawX = tub.pop();
        String rawY = tub.pop();
        Double x = box.getNumber(rawX);
        Double y = box.getNumber(rawY);
        if (y.compareTo(0d) == 0) {
            throw new ExecutionException("Illegal argument! Divide by zero was forbidden");
        }
        return x / y;
    }
}

