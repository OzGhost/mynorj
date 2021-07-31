package io.oz.mynorj;

import java.util.function.Supplier;
import java.util.Iterator;
import java.util.List;

public class Engine {

    public boolean run(String path2file) {
        List<Tub> tubs = new Reader().read(path2file);
        Box box = new Box();
        boolean[] stopMidWay = new boolean[]{false};
        Supplier<Boolean> endCb = () -> {
            stopMidWay[0] = true;
            return true;
        };
        box.setEndCallback(endCb);
        Iterator<Tub> ite = tubs.iterator();
        while (ite.hasNext() && !stopMidWay[0]) {
            Tub t = ite.next();
            String fname = t.pop();
            box.getFunc(fname)
                .runOn(t, box);
        }
        return box.outcome();
    }
}

