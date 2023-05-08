package org.example;

import java.util.Stack;

public class MyRPN {

    public static StringBuilder filter;

    public MyRPN(String filter) {
        this.filter = reversePN(filter);
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static StringBuilder reversePN(String query) {
        String[] tokens = query.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)|(?<=\\W)|(?=\\W)");
        Stack<String> stack = new Stack<>();
        StringBuilder output = new StringBuilder();

        for (String token : tokens) {
            if (isNumeric(token)) {
                output.append(token).append(" ");
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.peek().equals("(")) {
                    output.append(stack.pop()).append(" ");
                }
                stack.pop();
            } else if (token.matches("[<>]=?|=")) {
                while (!stack.isEmpty() && stack.peek().matches("[<>]=?")) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(token);
            } else if (token.equals("&") || token.equals("|")) {
                while (!stack.isEmpty() && (stack.peek().equals("&") || stack.peek().equals("|"))) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(token);
            } else if (!(token.equals("column")) && token.matches("[a-z]+")) {
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(" ");
        }

        return output;
    }
}
