package io.oz.mynorj;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.function.Supplier;
import java.util.function.Predicate;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;

public class Box {
        
    private static final Object NULL_OBJ = new Object(){};
    private static final Func SILENT_FUNC = new SilentFunc();

    private final Map<String, Object> heap = new HashMap<>(); // <- store variables
    private boolean endval;
    private boolean stoped = false;
    private Supplier<Boolean> endCallback;
    private JsonNode ground;
    private Queue<Object> states = new LinkedList<>();
    private boolean silenced = false;
    private Predicate<String> exceptor;

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
        boolean noex = false;
        if (silenced) {
            noex = true;
            if (exceptor != null && exceptor.test(name)) {
                noex = false;
            }
        }
        if (noex) {
            return SILENT_FUNC;
        }
        Func func = Func.get(name);
        if (func == null) {
            throw new ExecutionException("Function named <"+name+"> does not exists.");
        }
        return func;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(Class<T> type, String raw) {
        if (raw == null) {
            return null;
        }
        switch (raw) {
            case "null":
                return null;
            case "true":
                checkType(type, Boolean.class);
                return (T)Boolean.TRUE;
            case "false":
                checkType(type, Boolean.class);
                return (T)Boolean.FALSE;
            default:
                break; // do nothing
        }
        if (raw.matches("^[0-9]+(?:\\.[0-9]+)?$")) {
            try {
                checkType(type, Double.class);
                return (T)Double.valueOf(raw);
            } catch(NumberFormatException ignored) { } // the given string may be a variable :)
        }
        if (raw.charAt(0) == '"' && raw.charAt(raw.length()-1) == '"') {
            checkType(type, String.class);
            return (T)raw;
        }
        Object x = heap.get(raw);
        if (x == null) {
            throw new ExecutionException("Variable named <"+raw+"> does not exists !");
        }
        if (x == NULL_OBJ) {
            return null;
        }
        checkType(type, x.getClass());
        return (T)x;
    }

    private void checkType(Class<?> wanted, Class<?> actual) {
        if (wanted != Object.class && wanted != actual) {
            throw new ExecutionException("Expected <"+toName(wanted)+"> but was <"+toName(actual)+">");
        }
    }

    private String toName(Class<?> c) {
        return c.getSimpleName().toLowerCase();
    }

    public void newVar(String varname, Object value) {
        if (value == null) {
            heap.put(varname, NULL_OBJ); 
        } else {
            heap.put(varname, value);
        }
    }

    public JsonNode getGround() {
        return ground;
    }

    public void setGround(JsonNode ground) {
        this.ground = ground;
    }

    public void pushState(Object state) {
        states.offer(state);
    }

    public Object currentState() {
        return states.peek();
    }

    public Object popState(Object state) {
        if (states.peek() == state) { // allow to poll if given state is current state
            return states.poll();
        }
        return null;
    }

    public void silent(Predicate<String> ex) {
        silenced = true;
        exceptor = ex;
    }

    public void release() {
        silenced = false;
        exceptor = null;
    }

    private static class SilentFunc extends Func {
        SilentFunc() { super("silent"); }
        @Override
        public Object runOn(Tub tub, Box box) {
            System.out.println("__[o0] silent ...");
            return null;
        }
    }
}

