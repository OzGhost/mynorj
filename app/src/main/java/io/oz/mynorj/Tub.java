
package io.oz.mynorj;

public class Tub {
    private final String[] els;
    private int head = 0;
    
    public Tub(String[] els) {
        if (els == null) {
            this.els = new String[0];
        } else {
            this.els = els;
        }
    }

    public String pop() {
        if (remain() == 0) {
            return null;
        }
        return els[head++];
    }

    public int size() {
        return els.length;
    }

    public int remain() {
        return els.length - head;
    }
}

