package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.*;

public class Searcher implements Searchible {
    HashMap<Character, HashMap<String, Pair>> dictionary = new HashMap<>();
    private String delimiterRow = ",";
    private Properties properties = new Properties();
    private String filename = "airports.csv";

    private Path path = Path.of(ClassLoader.getSystemResource(filename).toURI());

    public Searcher() throws URISyntaxException {
        this.filling();
    }

    public void filling() {
        try (BufferedReader bufferedReader = new BufferedReader
                (new FileReader(path.toFile()))) {

            String row = bufferedReader.readLine();
            int counter = 0;
            while (row != null) {
                String[] rowsArray = row.split(delimiterRow);
                String key = rowsArray[1].toLowerCase().replaceAll("\"", "").trim();
                char c = key.charAt(0);
                HashMap<String, Pair> chunk = dictionary.get(c);
                if (chunk == null) {
                    chunk = new HashMap<>();
                }
                Pair pair = new Pair(counter, row.getBytes().length);
                chunk.put(key, pair);
                dictionary.put(c, chunk);
                counter += row.getBytes().length + 1;
                row = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        System.out.println(dictionary);
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

    @Override
    public List<String> getData(String startString) {
        var startTime = System.nanoTime();
        List<String> outputData = new ArrayList<>();
        List<Pair> lines = getLines(startString);
        System.out.println("затраченное время на поиск id:  " + (System.nanoTime() - startTime) / 1_000_000);
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(path.toFile(), "r");) {
            for (Pair line : lines) {
                randomAccessFile.seek(line.getLengthBeforeRow());
                byte[] bytes = new byte[line.getLengthOfRow()];
                randomAccessFile.read(bytes);
                String row = new String(bytes);
                String[] rowData = row.replaceAll("\"", "").split(delimiterRow);
                if (MyFilter.filterHardQuery(rowData))
                    outputData.add(String.format("%s [%s]", rowData[1], row));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(String.format("Количество найденных строк: %d, затрачено на поиск: %d мс.",
                outputData.size(), (System.nanoTime() - startTime) / 1_000_000));

        return outputData;
    }
}
