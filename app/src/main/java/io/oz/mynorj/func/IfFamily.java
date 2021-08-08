package io.oz.mynorj.func;

import io.oz.mynorj.ExecutionException;
import io.oz.mynorj.Func;
import io.oz.mynorj.Box;
import io.oz.mynorj.Tub;

public class IfFamily {

    public static void sign() {
        new IfFunc().sign();
        new ElseFunc().sign();
        new EndIfFunc().sign();
    }

    static class IfFunc extends Func {
        IfFunc() {
            super("if");
        }
        void sign() { register(); }

        @Override
        public Object runOn(Tub tub, Box box) {
            if (tub.remain() < 1) {
                throw new ExecutionException("Argument missing! Try again with: if <func>");
            }
            String func = tub.pop();
            Object out = box.getFunc(func).runOn(tub, box);
            String type = null;
            if (out == null) {
                type = "null";
            } else if ( ! (out instanceof Boolean) ) {
                type = out.getClass().getSimpleName().toLowerCase();
            }
            if (type != null) {
                throw new ExecutionException("[if] Illegal argument! Expect <func> to return <bool> but was <"+type+">");
            }
            if ((Boolean) out) {
                box.pushState(State.OK);
            } else {
                box.pushState(State.NOK);
                box.silent(name -> "endif".equals(name) || "else".equals(name));
            }
            return null;
        }
    }

    private static enum State {
        OK,
        NOK
    }

    static class ElseFunc extends Func {
        ElseFunc() {
            super("else");
        }
        void sign() { register(); }

        @Override
        public Object runOn(Tub tub, Box box) {
            if (box.isCurrentState(State.OK)) {
                box.silent(name -> "endif".equals(name));
            } else
            if (box.isCurrentState(State.NOK)) {
                box.release();
            } else {
                throw new ExecutionException("[else] Context failure! No if ahead!");
            }
            return null;
        }
    }

    static class EndIfFunc extends Func {
        EndIfFunc() {
            super("endif");
        }
        void sign() { register(); }

        @Override
        public Object runOn(Tub tub, Box box) {
            if (box.popState(State.OK) == null && box.popState(State.NOK) == null) {
                throw new ExecutionException("[endif] Context failure! No if ahead!");
            }
            box.release();
            return null;
        }
    }
}

