package org.example;


import java.net.URISyntaxException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws URISyntaxException {
        Searcher searcher = new Searcher();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите запрос для фильтра");
        String filter = scanner.nextLine();
        MyRPN myRPN = new MyRPN(filter);
        System.out.println("Введите начало названия аэропорта");
        String startString = scanner.nextLine();
        System.out.printf(searcher.getData(startString).toString());

    }
}
