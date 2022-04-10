package dfs;

import java.util.ArrayList;
import java.util.List;

public class LetterCombinations {
    public List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        dfs(digits.toCharArray(), list, 0, new StringBuilder());
        list.stream().forEach(System.out::println);
        return null;
    }

    public void dfs(char[] digits, List<String> list, int len, StringBuilder str) {
        if (len == digits.length) {
            list.add(str.toString());
            return;
        }

        for (int j = 0; j < 3; j++) {
            char word = (char) ((j + (digits[len] - '2') * 3 + 'a'));
            dfs(digits, list, len + 1, str.append(word));
            str.deleteCharAt(str.length() - 1);
        }
    }

    public static void main(String[] args) {
        LetterCombinations lc = new LetterCombinations();
        lc.letterCombinations("23");
    }
}
