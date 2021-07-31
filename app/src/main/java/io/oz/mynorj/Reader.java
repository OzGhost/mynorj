package io.oz.mynorj;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Reader {
    public List<Tub> read(String pathToFile) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(new File(pathToFile).toPath());
        } catch (IOException e) {
            e.printStackTrace();
            lines = Collections.emptyList();
        }
        List<Tub> elements = new ArrayList<>(lines.size());
        for (String line: lines) {
            elements.add( toElements(line) );
        }
        return elements;
    }

    private Tub toElements(String line) {
        return new Tub(line.trim().split("\\s+"));
    }
}

