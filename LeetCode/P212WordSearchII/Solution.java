import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by molikto on 09/04/2017.
 */
public class Solution {

    public static class Pos {
        int x;
        int y;
        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return x * 101 + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pos) {
                Pos o = (Pos) obj;
                return x == o.x && y == o.y;
            }
            return false;
        }
    }

    static char[][] board;

    public List<String> findWords(char[][] board, String[] words) {
        Solution.board = board;
        HashSet<String> found = new HashSet<String>();
        int[] fraq = new int[26];
        for (int j = board[0].length - 1; j >= 0; j--) {
            for (int i = board.length - 1; i >= 0; i--) {
                char c = board[i][j];
                fraq[c - 'a'] += 1;
            }
        }
        for (String w : words) {
            String o  = w;
            if (fraq[w.charAt(0)- 'a'] > fraq[w.charAt(w.length() - 1) - 'a']) {
                w = reverse(w);
            }
            boolean mached = false;
            for (int j = board[0].length - 1; !mached && j >= 0; j--) {
                for (int i = board.length - 1; !mached && i >= 0; i--) {
                    char c = board[i][j];
                    if (c == w.charAt(0)) {
                        board[i][j] = 'A';
                        mached = match(i, j, w.substring(1));
                        board[i][j] = c;
                        if (mached) {
                            found.add(o);
                        }
                    }
                }
            }
        }
        return new ArrayList<String>(found);
    }

    private String reverse(String w) {
        StringBuilder sb = new StringBuilder();
        for (int i = w.length()-1;i>=0;i--) {
            sb.append(w.charAt(i));
        }
        return sb.toString();
    }

    private boolean match(int x, int y, String substring) {
        if (substring.isEmpty()) {
            return true;
        } else {
            char c = substring.charAt(0);
            if (x > 0 && board[x - 1][y] == c) {
                board[x - 1][y] = 'A';
                boolean mached = match(x - 1, y, substring.substring(1));
                board[x - 1][y] = c;
                if (mached) return true;
            }
            if (x + 1 < board.length && board[x + 1][y] == c) {
                board[x + 1][y] = 'A';
                boolean mached = match(x + 1, y, substring.substring(1));
                board[x + 1][y] = c;
                if (mached) return true;
            }
            if (y > 0 && board[x][y - 1] == c) {
                board[x][y - 1] = 'A';
                boolean mached = match(x, y - 1, substring.substring(1));
                board[x][y - 1] = c;
                if (mached) return true;
            }
            if (y  + 1 < board[0].length && board[x][y + 1] == c) {
                board[x][y + 1] = 'A';
                boolean mached = match(x, y + 1, substring.substring(1));
                board[x][y + 1] = c;
                if (mached) return true;
            }
            return false;
        }
    }
}
