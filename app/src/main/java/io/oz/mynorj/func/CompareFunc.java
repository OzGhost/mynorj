package io.oz.mynorj.func;

import io.oz.mynorj.ExecutionException;
import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class CompareFunc extends Func {

    public static void sign() {
        new CompareFunc().register();
    }

    CompareFunc() {
        super("compare");
    }

    @Override
    public Object runOn(Tub tub, Box box) {
        if (tub.remain() < 3) {
            throw new ExecutionException("Argument missing! Try again with: compare <num> <op> <num>");
        }
        String rx = tub.pop();
        String op = tub.pop();
        String ry = tub.pop();
        if (!op.matches("^(?:[><=]|[!><]=)$")) {
            throw new ExecutionException("Syntax error! Try again with: compare <num> <op> <num>");
        }
        Double x = box.getNumber(rx);
        Double y = box.getNumber(ry);
        int relationship = x.compareTo(y);
        switch (op) {
            case "<":
                return relationship < 0;
            case ">":
                return relationship > 0;
            case "=":
                return relationship == 0;
            case "!=":
                return relationship != 0;
            case "<=":
                return relationship <= 0;
            case ">=":
                return relationship >= 0;
            default:
                break; // nothing to do here
        }
        throw new ExecutionException("[compare] Internal error! Encounter weird operation: '"+op+"'");
    }
}

