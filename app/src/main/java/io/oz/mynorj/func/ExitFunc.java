package io.oz.mynorj.func;

import io.oz.mynorj.ExecutionException;
import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class ExitFunc extends Func {

    public static void sign() {
        new ExitFunc().register();
    }

    private ExitFunc() {
        super("exit");
    }

    @Override
    public Object runOn(Tub tub, Box box) {
        if (tub.remain() < 1) {
            throw new ExecutionException("Missing parameter. Try again with: exit <bool>");
        }
        Boolean outcome = box.getValue(Boolean.class, tub.pop());
        if (outcome == null) { // TODO: may see null as false
            throw new ExecutionException("[exit] Given value must have type <bool> but was <null>");
        }
        box.end(outcome);
        return null;
    }
}

