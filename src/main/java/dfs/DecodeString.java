package dfs;

import java.util.Deque;
import java.util.LinkedList;

public class DecodeString {
    public static String decodeString(String s) {
        Deque<Integer> num_stack = new LinkedList<>();
        Deque<StringBuilder> str_stack = new LinkedList<>();

        int num = 0;
        StringBuilder cur = new StringBuilder();
        for (char ch: s.toCharArray()) {
            if (ch >= '0' && ch <= '9') {
                num = num * 10 + ch - '0';
            }else if (ch == '[') {
                num_stack.push(num);
                str_stack.push(cur);
                num = 0;
                cur = new StringBuilder();
            }else if (ch >= 'a' && ch <= 'z') {
                cur.append(ch);
            }else if (ch == ']') {
                int n = num_stack.poll();
                StringBuilder peek = str_stack.poll();
                for (int i = 0;i < n;i++) {
                    peek.append(cur);
                }
                cur = peek;
            }
        }
        //最后的一次匹配，就是最终结果
        return cur.toString();
    }

    public static void main(String[] args) {
        System.out.println(decodeString("3[a2[b]]"));
    }
}
