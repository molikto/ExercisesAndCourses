import javafx.util.Pair;

import java.util.Stack;

public class Solution {
    public void solve(char[][] board) {
        if (board.length == 0 || board[0].length == 0) return;
        for (int i = 0; i < board.length; i++) {
            mark(board, i, 0);
            mark(board, i, board[0].length - 1);
        }
        for (int i = 0; i < board[0].length; i++) {
            mark(board, 0, i);
            mark(board, board.length - 1, i);
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'M') board[i][j] = 'O';
            }
        }
    }

    public static class Pair {
        int i;
        int i1;

        public Pair(int i, int i1) {
            this.i = i;
            this.i1 = i1;
        }
    }

    private void mark(char[][] board, int ii, int jj) {
        if (board[ii][jj] == 'O') {
            Stack<Pair> stack = new Stack<Pair>();
            stack.push(new Pair(ii, jj));
            while (!stack.isEmpty()) {
                Pair p = stack.pop();
                int i = p.i;
                int i1 = p.i1;
                board[i][i1] = 'M';
                if (i > 0) {
                    if (board[i - 1][i1] == 'O') stack.push(new Pair(i - 1, i1));
                }
                if (i < board.length - 1) {
                    if (board[i + 1][i1] == 'O') stack.push(new Pair(i + 1, i1));
                }
                if (i1 > 0) {
                    if (board[i][i1 - 1] == 'O') stack.push(new Pair(i, i1 - 1));
                }
                if (i1 < board[0].length - 1) {
                    if (board[i][i1 + 1] == 'O') stack.push(new Pair(i, i1 + 1));
                }
            }
        }
    }
}