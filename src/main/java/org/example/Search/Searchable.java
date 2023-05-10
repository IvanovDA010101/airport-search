package org.example.Search;

import java.util.List;

public interface Searchable {

    List<String> getData(String startString,String filter);

    String getTimeInfo();
}
