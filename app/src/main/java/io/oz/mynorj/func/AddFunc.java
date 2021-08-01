package io.oz.mynorj.func;

import io.oz.mynorj.ExecutionException;
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

    @Override
    public Object runOn(Tub tub, Box box) {
        if (tub.remain() < 2) {
            throw new ExecutionException("Argument missing! Try again with: add <num> <num>");
        }
        String rawX = tub.pop();
        String rawY = tub.pop();
        Double x = box.getNumber(rawX);
        Double y = box.getNumber(rawY);
        return x + y;
    }
}

