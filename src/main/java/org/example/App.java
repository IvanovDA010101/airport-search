package org.example;


import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

import org.example.Filter.MyRPN;
import org.example.Search.Searcher;

public class App {
    public static void main(String[] args) throws URISyntaxException {
        Searcher searcher = new Searcher();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите запрос для фильтра");
            String filter = scanner.nextLine();
            if (filter.equals("!quit")) {
                break;
            } else if (!filter.equals("")) {
                filter = MyRPN.reversePN(filter);
            }
            System.out.println("Введите начало названия аэропорта");
            String startString = scanner.nextLine();
            if (startString.equals("!quit")) {
                break;
            }
            List<String> airports = searcher.getData(startString,filter);
            for (String airport: airports) {
                System.out.println(airport);
            }
            System.out.println(searcher.getTimeInfo());
        }
    }
}
