package io.oz.mynorj;

import java.util.Map;
import java.util.HashMap;

public abstract class Func {

    private static final Map<String, Func> dict = new HashMap<>(); // store func
    private String name;

    public static Func get(String name) {
        return dict.get(name);
    }

    protected Func(String name) {
        this.name = name;
    }

    public abstract Object runOn(Tub tub, Box box);

    protected void register() {
        dict.merge(name, this, (a,b) -> {
            throw new RuntimeException("Function named <"+name+"> already exists !!!");
        });
    }
}

