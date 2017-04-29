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
//        HashMap<Character, ArrayList<Pos>> map = new HashMap<Character, ArrayList<Pos>>();
//        for (int i = 0; i < board.length; i++) {
//            mark[i]= new boolean[board[0].length];
//            for (int j = 0; j < board[0].length; j++) {
//                char c = board[i][j];
//                ArrayList<Pos> pos =  map.get(c);
//                if (pos == null) {
//                    pos = new ArrayList<Pos>();
//                    map.put(c, pos);
//                }
//                pos.add(new Pos(i, j));
//            }
//        }
        HashSet<String> found = new HashSet<String>();
        for (String w : words) {
            boolean mached = false;
            for (int i = 0; !mached && i < board.length; i++) {
                for (int j = 0; !mached && j < board[0].length; j++) {
                    char c = board[i][j];
                    if (c == w.charAt(0)) {
                        board[i][j] = 'A';
                        mached = match(i, j, w.substring(1));
                        board[i][j] = c;
                        if (mached) {
                            found.add(w);
                        }
                    }
                }
            }
        }
//        for (String w : words) {
//            ArrayList<Pos> head = map.get(w.charAt(0));
//            if (head != null) {
//                for (Pos p : head) {
//                    mark[p.x][p.y] = true;
//                    boolean mached = match(p.x, p.y, w.substring(1));
//                    mark[p.x][p.y] = false;
//                    if (mached) {
//                        found.add(w);
//                        break;
//                    }
//                }
//            }
//        }
        return new ArrayList<String>(found);
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
