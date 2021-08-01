package io.oz.mynorj;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.oz.mynorj.func.UseFunc;
import io.oz.mynorj.func.IfFamily;
import io.oz.mynorj.func.SetFunc;
import io.oz.mynorj.func.CompareFunc;
import io.oz.mynorj.func.CombineFunc;
import io.oz.mynorj.func.IsNullFunc;
import io.oz.mynorj.func.IsFalseFunc;
import io.oz.mynorj.func.ExitFunc;
import io.oz.mynorj.func.MultiplyFunc;
import io.oz.mynorj.func.AddFunc;
import io.oz.mynorj.func.DivideFunc;

import java.util.function.Supplier;
import java.util.Iterator;
import java.util.List;

public class Engine {

    static {
        UseFunc.sign();
        IfFamily.sign();
        SetFunc.sign();
        CompareFunc.sign();
        CombineFunc.sign();
        IsNullFunc.sign();
        IsFalseFunc.sign();
        ExitFunc.sign();
        MultiplyFunc.sign();
        AddFunc.sign();
        DivideFunc.sign();
    }

    public boolean run(String path2file, Object rawGround) {
        List<Tub> tubs = new Reader().read(path2file);
        Box box = new Box();
        boolean[] stopMidWay = new boolean[]{false};
        Supplier<Boolean> endCb = () -> {
            stopMidWay[0] = true;
            return true;
        };
        box.setEndCallback(endCb);
        JsonNode ground = new ObjectMapper().valueToTree(rawGround);
        box.setGround(ground);
        Iterator<Tub> ite = tubs.iterator();
        int linenum = 0;
        while (ite.hasNext() && !stopMidWay[0]) {
            linenum++;
            Tub t = ite.next();
            if (t.isEmpty()) {
                continue; // ignore empty tub
            }
            String fname = t.pop();
            try {
                box.getFunc(fname).runOn(t, box);
            } catch (ExecutionException e) {
                throw new ExecutionException(e, "Line " + linenum + ": " + e.getMessage());
            }
        }
        return box.outcome();
    }
}

