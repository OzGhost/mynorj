package io.oz.mynorj;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

public class Box {
        
    private final Map<String, Object> heap = new HashMap<>(); // <- store variables
    private boolean endval;
    private boolean stoped = false;
    private Supplier<Boolean> endCallback;

    public void end(boolean val) { // exit will call this one
        if (stoped) {
            throw new ExecutionException("Unable to end an exection which already stoped.");
        }
        endval = val;
        stoped = true;
        if (endCallback != null) {
            endCallback.get();
        }
    }

    private void runningCheck() {
        if (stoped) {
            throw new ExecutionException("The execution is not running !!!");
        }
    }

    public boolean outcome() {
        if ( ! stoped) {
            throw new ExecutionException("No outcome available !!!");
        }
        return endval;
    }

    public void setEndCallback(Supplier<Boolean> endCallback) {
        this.endCallback = endCallback;
    }

    public Func getFunc(String name) {
        Func func = Func.get(name);
        if (func == null) {
            throw new ExecutionException("Function named <"+name+"> does not exists.");
        }
        return func;
    }
}


