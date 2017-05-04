

import java.util.*;

public class Solution {

    public static class Node {
        String str;
        List<Node> next = null;
    }

    static class Trie {
        String str;
        boolean correct;
        Trie[] childs = new Trie[26];

        public void add(String w) {
            add(w, 0);
        }
        public void add(String w, int start) {
            if (start < w.length()) {
                char c = w.charAt(start);
                Trie t = childs[c-'a'];
                if (t == null) {
                    t = new Trie();
                    t.str = w.substring(0, start + 1);
                    childs[c-'a'] = t;
                }
                t.add(w, start + 1);
            } else {
                correct = true;
            }
        }

        public List<String> walk(String s) {
            ArrayList<String> strs = new ArrayList<String>();
            Trie t = this;
            for (int l = 1; l <= s.length(); l++) {
                char c = s.charAt(l - 1);
                t = t.childs[c-'a'];
                if (t == null) {
                    break;
                } else if (t.correct) {
                    strs.add(t.str);
                }
            }
            return strs;
        }
    }
    public List<String> wordBreak(String s, List<String> wordDict) {
        Trie root = new Trie();
        for (String w : wordDict) {
            root.add(w);
        }
        ArrayList<String> sol = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        boolean[] wrong = new boolean[s.length() + 1];
        collect(root, s, 0, sol, sb, wrong);
        return sol;
    }


    private void collect(Trie wordDict, String s, int i, ArrayList<String> sol, StringBuilder sb, boolean[] wrong) {
        if (s.length() == i) {
            sb.setLength(sb.length() - 1);
            sol.add(sb.toString());
        } else {
            for (String sub : wordDict.walk(s.substring(i))) {
                if (!wrong[i + sub.length()]) {
                    int length = sb.length();
                    sb.append(sub);
                    sb.append(' ');
                    int prev = sol.size();
                    collect(wordDict, s, i + sub.length(), sol, sb, wrong);
                    if (sol.size() == prev) {
                        wrong[i + sub.length()] = true;
                    }
                    sb.setLength(length);
                }
            }
        }
    }
}