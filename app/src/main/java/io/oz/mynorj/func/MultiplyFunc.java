package io.oz.mynorj.func;

import io.oz.mynorj.ExecutionException;
import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class MultiplyFunc extends Func {

    public static void sign() {
        new MultiplyFunc().register();
    }

    private MultiplyFunc() {
        super("multiply");
    }

    @Override
    public Object runOn(Tub tub, Box box) {
        if (tub.remain() < 2) {
            throw new ExecutionException("Argument missing! Try again with: myltiply <num> <num>");
        }
        String rawX = tub.pop();
        String rawY = tub.pop();
        Double x = box.getNumber(rawX);
        Double y = box.getNumber(rawY);
        return x * y;
    }
}

