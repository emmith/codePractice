package DataStructure;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

public class Trie {
    final int DEFAULT_CAPACITY =  26;
    int key;
    Trie []element;

    public Trie() {
        element = new Trie[DEFAULT_CAPACITY];
    }

    public void insert(String word) {
        Trie node = this;
        for (char ch : word.toCharArray()) {
            if (node.element[ch - 'a'] == null) {
                node.element[ch - 'a'] = new Trie();
            }
            node = node.element[ch - 'a'];
        }
        node.key = 1;
    }

    public boolean search(String word) {
        return searchKey(word) == 1;
    }

    public boolean startWith(String word) {
        return searchKey(word) >= 0;
    }

    private int searchKey(String word) {
        Trie node = this;

        for (char ch : word.toCharArray()) {
            //-1表示没找到
            if (node.element[ch - 'a'] == null) {
                return -1;
            }
            node = node.element[ch - 'a'];
        }
        //找到了，key为1表示为单词，key为零为前缀
        return node.key;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));

        System.out.println(trie.search("app"));
        trie.insert("app");
        System.out.println(trie.startWith("apple"));
    }
}
