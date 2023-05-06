package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Searchible {

    List<Pair> getLines(String startString);

    void filling() throws FileNotFoundException;

    List<String> getData(String startString) throws IOException;
}
