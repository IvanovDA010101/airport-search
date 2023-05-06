package org.example;


import java.net.URISyntaxException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws URISyntaxException {
        Searcher searcher = new Searcher();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите начало названия аэропорта");
        String startString = scanner.nextLine();
        searcher.getData(startString);

    }
}
