package org.example.Search;

import org.example.Search.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Searchable {

    List<Pair> getLines(String startString);

    void filling() throws FileNotFoundException;

    List<String> getData(String startString,String filter) throws IOException;
}
