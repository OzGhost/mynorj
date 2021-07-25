package io.oz.mynorj;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Reader {
    public List<String[]> read(String pathToFile) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(new File(pathToFile).toPath());
        } catch (IOException e) {
            e.printStackTrace();
            lines = Collections.emptyList();
        }
        List<String[]> elements = new ArrayList<>(lines.size());
        for (String line: lines) {
            elements.add( toElements(line) );
        }
        return elements;
    }

    private String[] toElements(String line) {
        return line.trim().split("\\s+");
    }
}

