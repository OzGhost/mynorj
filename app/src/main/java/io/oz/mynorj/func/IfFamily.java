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

    private static class IfFunc extends Func {
        private IfFunc() {
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
                throw new ExecutionException("Illegal argument! Expect <func> to return <bool> but was <"+type+">");
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

    private static class ElseFunc extends Func {
        private ElseFunc() {
            super("else");
        }
        void sign() { register(); }

        @Override
        public Object runOn(Tub tub, Box box) {
            if (box.currentState() == State.OK) {
                box.silent(name -> "endif".equals(name));
            } else
            if (box.currentState() == State.NOK) {
                box.release();
            } else {
                throw new ExecutionException("Context failure! No if ahead!");
            }
            return null;
        }
    }

    private static class EndIfFunc extends Func {
        private EndIfFunc() {
            super("endif");
        }
        void sign() { register(); }

        @Override
        public Object runOn(Tub tub, Box box) {
            box.popState(State.OK);
            box.popState(State.NOK);
            box.release();
            return null;
        }
    }
}

