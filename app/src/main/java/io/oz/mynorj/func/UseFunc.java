package io.oz.mynorj.func;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.oz.mynorj.ExecutionException;
import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class UseFunc extends Func {

    public static void sign() {
        new UseFunc().register();
    }

    UseFunc() {
        super("use");
    }

    @Override
    public Object runOn(Tub tub, Box box) {
        if (tub.remain() < 3) {
            throw new ExecutionException("Argument missing! Try again with: use <var> from <ap>");
        }
        String varName = tub.pop();
        String kw = tub.pop();
        String ap = tub.pop();
        if (!"from".equals(kw)) {
            throw new ExecutionException("Syntax error! Try again with: use <var> from <ap>");
        }
        box.newVar(varName, pickValue(ap, box.getGround()));
        return null;
    }

    private Object pickValue(String ap, JsonNode ground) {
        Tub tub = new Tub(ap.split("\\."));
        return travel(tub, ground);
    }
    
    private Object travel(Tub tub, JsonNode ground) {
        if (tub.remain() == 0) { // travel to the end of the path
            if (ground.isContainerNode()) {
                return null; // path exhausted
            }
            if (ground.isNull()) {
                return null;
            }
            if (ground.isBoolean()) {
                return ground.asBoolean();
            }
            if (ground.isNumber()) {
                return ground.asDouble();
            }
            return ground.asText(); // if nothing else then try text
        }
        if (ground.isValueNode()) {
            return null; // unable to travel to end of path -> unreachable
        }
        String step = tub.pop();
        if (ground.isArray()) {
            int idx = toIndex(step);
            ArrayNode arr = (ArrayNode) ground;
            if (idx < 0 || idx >= arr.size()) {
                return null; // unreal index -> unreachable
            }
            return travel(tub, arr.get(idx));
        } else { // otherwise it is object
            ObjectNode obj = (ObjectNode) ground;
            JsonNode next = obj.get(step);
            if (next == null) {
                return null; // non-exists step
            }
            return travel(tub, next);
        }
    }
    
    private int toIndex(String step) {
        try {
            return Integer.parseInt(step);
        } catch(Throwable ignored) { }
        return -1;
    }
}

