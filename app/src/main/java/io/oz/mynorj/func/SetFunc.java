package io.oz.mynorj.func;

import io.oz.mynorj.ExecutionException;
import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class SetFunc extends Func {

    public static void sign() {
        new SetFunc().register();
    }

    SetFunc() {
        super("set");
    }

    @Override
    public Object runOn(Tub tub, Box box) {
        if (tub.remain() < 3) {
            throw new ExecutionException("Argument missing! Try again with: set <var> = <func>");
        }
        String varname = tub.pop();
        String kw = tub.pop();
        String funcname = tub.pop();
        if ( ! "=".equals(kw)) {
            throw new ExecutionException("Syntax error! Try again with: set <var> = <func>");
        }
        Object out = box.getFunc(funcname).runOn(tub, box);
        box.newVar(varname, out);
        return null;
    }
}

