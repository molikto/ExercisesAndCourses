import java.io.*;
import java.util.*;


class Solution {
  
  String[] words;
  boolean[] visited = new boolean[26]; // if visited
  boolean[] visiting = new boolean[26];
  boolean[][] edges = new boolean[26][];
  {
    for (int i = 0; i < 26; i++) edges[i] = new boolean[26];
  }
  
  public Solution(String[] words) {
    this.words = words;
    // build the edges
    for (int i = 0; i < words.length - 1; i++) {
      String first = words[i];
      String second = words[i + 1];
      int length = Math.min(first.length(), second.length());
      for (int j = 0; j < length; j++) {
        int cf = first.charAt(j) - 'a';
        int cs = second.charAt(j) - 'a';
        if (cs != cf) {
          edges[cf][cs] = true;
          j = length;
        }
      }
    }
    // search
    for (int i = 0; i < 26; i++) {
      if (!cyclic && !visited[i]) {
        visit(i);
      }
    }
    if (!cyclic) {
      StringBuilder sb = new StringBuilder();
      for (int i : order) sb.append((char) (i + 'a'));
      result = sb.toString();
    }
  }
  
  // if cyclic = true, result = null
  boolean cyclic = false;
  String result = null;
  ArrayList<Integer> order = new ArrayList<Integer>();
  
  
  
  public void visit(int i) {
    if (visiting[i]) {
      cyclic = true;
    } else {
      visiting[i] = true;
      for (int j = 0; j < 26; j++) {
        if (edges[i][j]) { /// there is a edge from i to j
          visit(j);
        }
      }
      visiting[i] = false;
      visited[i] = true;
      order.add(0, i);
    }
  }
  
  public static void main(String[] args) {
    String[] words = new String[] {"wrt", "wrf", "er", "ett", "rftt"};
    
    Solution sol = new Solution(words);
    if (!sol.cyclic) System.out.println(sol.result);
  }
}
