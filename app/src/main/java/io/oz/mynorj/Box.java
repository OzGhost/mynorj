package io.oz.mynorj;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.function.Supplier;
import java.util.function.Predicate;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Deque;

public class Box {
        
    private static final Object NULL_OBJ = new Object(){};
    private static final Func SILENT_FUNC = new SilentFunc();

    private final Map<String, Object> heap = new HashMap<>(); // <- store variables
    private boolean endval;
    private boolean stoped = false;
    private JsonNode ground;
    private Deque<Object> states = new LinkedList<>();
    private boolean silenced = false;
    private Predicate<String> exceptor;

    public void end(boolean val) { // exit will call this one
        if (stoped) {
            throw new ExecutionException("Unable to end an exection which already stoped.");
        }
        endval = val;
        stoped = true;
    }

    public boolean running() {
        return !stoped;
    }

    public boolean outcome() {
        if ( ! stoped) {
            throw new ExecutionException("No outcome available !!!");
        }
        return endval;
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
                checkType(type, Boolean.class, raw);
                return (T)Boolean.TRUE;
            case "false":
                checkType(type, Boolean.class, raw);
                return (T)Boolean.FALSE;
            default:
                break; // do nothing
        }
        if (raw.matches("^-?[0-9]+(?:\\.[0-9]+)?$")) {
            try {
                checkType(type, Double.class, raw);
                return (T)Double.valueOf(raw);
            } catch(NumberFormatException ignored) { } // the given string may be a variable :)
        }
        if (raw.charAt(0) == '"' && raw.charAt(raw.length()-1) == '"') {
            checkType(type, String.class, raw);
            String quotedString = (String) raw;
            return (T)quotedString.substring(1, quotedString.length() - 1);
        }
        Object x = heap.get(raw);
        if (x == null) {
            throw new ExecutionException("Variable named <"+raw+"> does not exists !");
        }
        if (x == NULL_OBJ) {
            return null;
        }
        checkType(type, x.getClass(), raw);
        return (T)x;
    }

    private void checkType(Class<?> wanted, Class<?> actual, String subject) {
        if (wanted != Object.class && wanted != actual) {
            throw new ExecutionException("Expect <"+subject+"> to be a <"+toName(wanted)+"> but was a <"+toName(actual)+">");
        }
    }

    private String toName(Class<?> c) {
        if (c == Boolean.class) return "bool";
        if (c == Double.class) return "num";
        if (c == String.class) return "string";
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
        states.addFirst(state);
    }

    public boolean isCurrentState(Object state) {
        return !states.isEmpty() && state == states.getFirst();
    }

    public Object popState(Object state) {
        if (states.isEmpty()) return null;
        if (states.getFirst() == state) { // allow to poll if given state is current state
            return states.removeFirst();
        }
        return null;
    }

    public void silent(Predicate<String> ex) {
        silenced = true;
        exceptor = ex;
    }

    // TODO: add restriction so that this function only work under some condition
    public void release() {
        silenced = false;
        exceptor = null;
    }

    public Double getNumber(String raw) {
        Double val = getValue(Double.class, raw);
        if (val == null) {
            return 0d;
        }
        return val;
    }

    public Boolean getBool(String raw) {
        Boolean val = getValue(Boolean.class, raw);
        if (val == null) {
            return Boolean.FALSE;
        }
        return val;
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

