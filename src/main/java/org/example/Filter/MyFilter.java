package org.example.Filter;

import org.example.Enums.ColumnTypes;

import java.util.Scanner;
import java.util.Stack;

public class MyFilter {

    public static boolean filterHardQuery(String[] row,String filter) {
        String[] tokens = filter.split(" ");
        Stack<String> stack = new Stack<>();
        StringBuilder result = new StringBuilder();
        for (String token : tokens) {
            if (MyRPN.isNumeric(token))
                stack.push(token);
            else if (token.matches("[a-z]+")) {
                stack.push(token);
            }
            if (token.equals("=") || token.equals(">") || token.equals("<") || token.equals("!")) {

                result.append(filterSimpleQuery(row, stack.pop(),
                        stack.pop(), token.charAt(0))).append(" ");
            } else if (token.equals("&") || token.equals("|")) {
                result.append(token).append(" ");
            }
        }
        return evaluateBooleanExpression(String.valueOf(result).trim());
    }

    public static String filterSimpleQuery(String[] row, String comparisonElement, String columnNumber, Character sign) {
        int column = Integer.parseInt(columnNumber) - 1;
        boolean isNumeric = ColumnTypes.isNumericField(columnNumber);
        switch (sign) {
            case '=':
                if (isNumeric) {
                    return String.valueOf(Double.parseDouble(comparisonElement) == Double.parseDouble(row[column]));
                } else return String.valueOf(row[column].toLowerCase().equals(comparisonElement));
            case '>':
                if (isNumeric) {
                    return String.valueOf(Double.parseDouble(comparisonElement) < Double.parseDouble(row[column]));
                } else return String.valueOf(row[column].length() > Integer.parseInt(comparisonElement));
            case '<':
                if (isNumeric) {
                    return String.valueOf(Double.parseDouble(comparisonElement) > Double.parseDouble(row[column]));
                } else return String.valueOf(row[column].length() < Integer.parseInt(comparisonElement));
            case '!':
                if (isNumeric) {
                    return String.valueOf(Double.parseDouble(comparisonElement) != Double.parseDouble(row[column]));
                }
                return String.valueOf(!row[column].equals(comparisonElement));
        }
        return "false";
    }

    public static boolean evaluateBooleanExpression(String expression) {
        Scanner scanner = new Scanner(expression);
        boolean result = scanner.nextBoolean();
        while (scanner.hasNext()) {
            String operator = scanner.next();
            boolean rightOperand = scanner.nextBoolean();
            if (operator.equals("&")) {
                result = result && rightOperand;
            } else if (operator.equals("|")) {
                result = result || rightOperand;
            }
        }
        scanner.close();
        return result;
    }
}
