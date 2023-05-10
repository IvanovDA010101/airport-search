package org.example.Search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.Enums.ErrorText;
import org.example.Filter.MyFilter;

public class Searcher implements Searchable {
    HashMap<Character, HashMap<String, Pair>> dictionary = new HashMap<>();
    private final String DELIMITER = ",";
    private final String FILENAME = "airports.csv";

    private String timeInfo;

    private final Path path;

    {
        try {
            path = Path.of(ClassLoader.getSystemResource(FILENAME).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(ErrorText.FILE_ACCESS_ERROR.getText());
        }
    }

    public Searcher() {
        this.filling();
    }

    public void filling() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile(), StandardCharsets.UTF_8))) {
            String row = bufferedReader.readLine();
            int counter = 0;
            while (row != null) {
                String[] rowsArray = row.split(DELIMITER);
                String key = rowsArray[1].toLowerCase().replaceAll("\"", "").trim();
                char c = key.charAt(0);
                HashMap<String, Pair> chunk = dictionary.get(c);
                if (chunk == null) {
                    chunk = new HashMap<>();
                }
                Pair pair = new Pair(counter, row.getBytes().length);
                chunk.put(key, pair);
                dictionary.put(c, chunk);
                counter += row.getBytes().length+1;
                row = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(ErrorText.FILE_READ_ERROR.getText());
        }
    }

    public List<Pair> getLines(String startString) {
        List<Pair> result = new ArrayList<>();
        char c = startString.toLowerCase().charAt(0);
        HashMap<String, Pair> secondaryBranch = dictionary.get(c);
        for (Map.Entry<String, Pair> item : secondaryBranch.entrySet()) {
            if (item.getKey().startsWith(startString)) {
                result.add(item.getValue());
            }
        }

        return result;
    }
    private List<String> getLine(List<Pair> lines,String filter) {
        List<String> result = new ArrayList<>();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(path.toFile(), "r")) {
            for (Pair line : lines) {
                randomAccessFile.seek(line.getLengthBeforeRow());
                byte[] bytes = new byte[line.getLengthOfRow()];
                randomAccessFile.read(bytes);
                String row = new String(bytes);
                String[] rowData = row.replaceAll("\"", "").split(DELIMITER);
                if (!filter.equals("")) {
                    if (MyFilter.filterHardQuery(rowData, filter))
                        result.add(String.format("%s [%s]", rowData[1], row));
                } else result.add(String.format("%s [%s]", rowData[1], row));
            }
        } catch (IOException e) {
            throw new RuntimeException(ErrorText.FILE_READ_ERROR.getText());
        }
        return result;
    }
    @Override
    public List<String> getData(String startString,String filter) {
        var startTime = System.nanoTime();
        List<Pair> lines = getLines(startString);
        List<String> outputData = getLine(lines,filter);
        this.timeInfo=String.format("Количество найденных строк: %d, затрачено на поиск: %d мс.",
                outputData.size(), (System.nanoTime() - startTime) / 1_000_000);
        Collections.sort(outputData);
        return outputData;
    }

    @Override
    public String getTimeInfo() {
        return timeInfo;
    }
}
