package io.oz.mynorj;

import io.oz.mynorj.func.UseFunc;
import java.util.Map;
import java.util.HashMap;

public abstract class Func {

    private static final Map<String, Func> dict = new HashMap<>(); // store func

    public static Func get(String name) {
        return dict.get(name);
    }

    static {
        UseFunc.sign();
    }

    public abstract Object runOn(Tub tub, Box b);

    protected void register(String name) {
        dict.merge(name, this, (a,b) -> {
            throw new RuntimeException("Duplicate register for name: <"+name+">");
        });
    }
}

