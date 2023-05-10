package org.example.Filter;

import org.example.Enums.ErrorText;

import java.util.Stack;

public class MyRPN {

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

    public static String reversePN(String query) {
        String[] tokens = query.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)|(?<=\\W)|(?=\\W)");
        Stack<String> stack = new Stack<>();
        StringBuilder output = new StringBuilder();
        try {
            for (int i = 0; i < tokens.length; i++) {
                String token = tokens[i];
                if (isNumeric(token)) {
                    output.append(token).append(" ");
                } else if (token.equals("(")) {
                    stack.push(token);
                } else if (token.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        output.append(stack.pop()).append(" ");
                    }
                    stack.pop();
                } else if (token.matches("[<>!]=?|=")) {
                    if (token.equals("<") && tokens[i + 1].equals(">")) {
                        token = "!";
                        tokens[i + 1] = "#";
                    }
                    while (!stack.isEmpty() && stack.peek().matches("[<>]=?")) {
                        output.append(stack.pop()).append(" ");
                    }
                    stack.push(token);
                } else if (token.equals("&") || token.equals("|")) {
                    if (token.equals("|") && tokens[i + 1].equals("|")) {
                        tokens[i + 1] = "#";
                    }
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
        } catch (Exception e){
            System.out.println(ErrorText.FILTER_PARSE_ERROR.getText());
            return "";
        }
        return String.valueOf(output);
    }
}
