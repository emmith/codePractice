package DataStructure;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Calculator {
    static Map<Character, Integer> prior = new HashMap<>();

    static {
        prior.put('(', 3);
        prior.put(')', 3);
        prior.put('/', 2);
        prior.put('*', 2);
        prior.put('+', 1);
        prior.put('-', 1);
    }

    //运算符栈
    Deque<Character> operation_stack = new LinkedList<>();
    //数字栈
    Deque<Integer> num_stack = new LinkedList<>();

    //前缀表达式到逆波兰表达式(后缀表达式)
    public String infixToSuffix(String s) {
        StringBuilder res = new StringBuilder();
        int cur = 0;
        for (char ch : s.toCharArray()) {
            if (isDigit(ch)) {
                //数字直接输出
                res.append(ch);
            } else {
                if (isOperation(ch)) {
                    res.append(" ");
                    if (operation_stack.isEmpty() || ch == '(') {
                        operation_stack.push(ch);
                    } else {
                        if (ch == ')') { //如果是右括号，弹出栈中左括号之前的所有括号
                            while (operation_stack.peek() != '(') {
                                res.append(operation_stack.pop());
                            }
                            //弹出左括号
                            operation_stack.pop();
                        } else {
                            while (!operation_stack.isEmpty() && operation_stack.peek() != '(' && prior.get(operation_stack.peek()) >= prior.get(ch)) {
                                res.append(operation_stack.pop());
                            }
                            operation_stack.push(ch);
                        }
                    }
                }
            }
        }

        while (!operation_stack.isEmpty()) {
            res.append(operation_stack.pop());
        }
        return res.toString();
    }

    //用后缀表达式计算
    public int calcSuffix(String suffix) {
        for (int i = 0; i < suffix.length();) {
            char ch = suffix.charAt(i);
            if (isDigit(ch)) {
                int cur = 0;
                while (isDigit(suffix.charAt(i))) {
                    cur = cur * 10 + (suffix.charAt(i) - '0');
                    i++;
                }
                num_stack.push(cur);
            } else {
                if (isOperation(ch)) {
                    int b = num_stack.pop();
                    int a = num_stack.pop();
                    num_stack.push(calc(a, b, ch));
                }
                i++;
            }
        }
        return num_stack.peek();
    }

    private int calc(int a, int b, char ch) {
        if (ch == '+') {
            return a + b;
        }
        if (ch == '-') {
            return a - b;
        }
        if (ch == '*') {
            return a * b;
        }
        if (ch == '/') {
            return a / b;
        }
        return 0;
    }

    private boolean isOperation(char ch) {
        if (ch == '+' || ch == '-' || ch == '/' || ch == '*' || ch == '(' || ch == ')') {
            return true;
        }
        return false;
    }

    private boolean isDigit(char ch) {
        if (ch >= '0' && ch <= '9') {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // a+b*c+(d*e+f)*g
        //calc.infixToSuffix("15 + 78 * 99 + ( 12 * 23 + 90)*18")
        Calculator calc = new Calculator();
//        System.out.println(calc.infixToSuffix("15 + 78 * 99 + 12 * 23 + 90 * 18"));
        System.out.println(calc.calcSuffix("15 78 99 *+12 23 *+90 18*+"));
    }
}
