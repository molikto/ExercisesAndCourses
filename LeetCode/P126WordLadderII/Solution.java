import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Solution {



    public static class Node {
        public String str;
        public ArrayList<Node> adjs = new ArrayList<Node>();
        public int score = Integer.MAX_VALUE;
    }

    public HashMap<String, Node> nodes = new HashMap<String, Node>();


    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        System.out.println(System.currentTimeMillis());
        if (beginWord.equals(endWord)) {
            ArrayList<List<String>> sol = new ArrayList<List<String>>();
            sol.add(new ArrayList<String>());
            return sol;
        }
        for (String s : wordList) {
            Node node = new Node();
            node.str = s;
            nodes.put(node.str, node);
        }

        if (nodes.get(endWord) == null) return new ArrayList<List<String>>();
        if (nodes.get(beginWord) == null) {
            Node node = new Node();
            node.str = beginWord;
            nodes.put(node.str, node);
            //wordList = new ArrayList<String>(wordList);
            wordList.add(beginWord);
        }
        for (int i = 0; i < wordList.size(); i++) {
            String a = wordList.get(i);
            for (int j = 0; j < i; j++) {
                String b = wordList.get(j);
                if (adj(a, b)) {
                    Node na = nodes.get(a);
                    Node nb = nodes.get(b);
                    na.adjs.add(nb);
                    nb.adjs.add(na);
                }
            }
        }
        visit(nodes.get(beginWord), nodes.get(endWord));
        int endScore = nodes.get(endWord).score;
        if (endScore == Integer.MAX_VALUE) return new ArrayList<List<String>>();

        ArrayList<List<String>> collects = new ArrayList<List<String>>();
        ArrayList<String> collects0 = new ArrayList<String>();
        collects0.add(beginWord);
        collects.add(collects0);
        System.out.println(System.currentTimeMillis());
        collects = collect(collects, 1, endScore, endWord);
        System.out.println(System.currentTimeMillis());
        return collects;
    }

    public ArrayList<List<String>> collect(ArrayList<List<String>> prev, int ofScore, int endScore, String endNode) {
        ArrayList<List<String>> collects = new ArrayList<List<String>>();
        for (List<String> p : prev) {
            //for (String s : p) System.out.println(s);
            //System.out.println();
            Node last = nodes.get(p.get(p.size() - 1));
            for (Node adj : last.adjs) {
                if (ofScore < endScore || adj.str.equals(endNode)) {
                    if (adj.score == ofScore) {
                        ArrayList<String> newList = new ArrayList<String>(p);
                        newList.add(adj.str);
                        collects.add(newList);
                    }
                }
            }
        }
        if (ofScore < endScore) {
            return collect(collects, ofScore + 1, endScore, endNode);
        } else { // can only be equal
            return collects;
        }
    }


    public void visit(Node start, Node end) {
        int score = 1;
        start.score = 0;
        ArrayList<Node> unvisited = new ArrayList<Node>();
        unvisited.add(start);
        while (!unvisited.isEmpty()) {
            ArrayList<Node> newList= new ArrayList<Node>();
            for (Node node : unvisited) {
                for (Node adj : node.adjs) {
                    if (adj.score > score) {
                        adj.score = score;
                        newList.add(adj);
                        if (node == end) {
                            return;
                        }
                    }
                }
            }
            score += 1;
            unvisited = newList;
        }
    }

    public boolean adj(String a, String b) {
        int len = a.length();
        boolean hasDiff = false;
        for (int i = 0; i < len; i++) {
            boolean same = a.charAt(i) == b.charAt(i);
            if (!same && hasDiff) return false;
            else if (!same) hasDiff = true;
        }
        return hasDiff;
    }
}