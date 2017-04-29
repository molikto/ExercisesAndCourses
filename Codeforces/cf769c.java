import java.io.*;
import java.math.BigInteger;
import java.util.*;

// WRONG ANSWER, should also add a minial map

public class cf769c {

    static boolean DEBUG = false;
    static Scanner cin;
    static PrintStream out = System.out;


    public static void main(String args[]) throws Exception {
        // input
        if ((args.length != 0 && "test".equals(args[0]))) {
            DEBUG = true;
            String TEST = "2 3 2\n.**\nX..";
            cin = new Scanner(new ByteArrayInputStream(TEST.getBytes()));
        } else {
            cin =  new Scanner(System.in);
        }
        int n = cin.nextInt();
        int m = cin.nextInt();
        int k = cin.nextInt();
        if (k % 2 == 1) {
            out.println("IMPOSSIBLE");
            return;
        }
        k = k / 2;
        char[][] maze = new char[n][];
        // D L R U
        char[] route = new char[k * 2];
        int x = 0, y = 0;
        for (int i = 0; i < maze.length; i++) {
            maze[i] = cin.nextLine().toCharArray();
            if (maze[i].length != m) {
                i-=1;
            } else {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j] == 'X') {
                        x = i;
                        y = j;
                    }
                }
            }
        }

        int a = 0;
        while (a != k) {
            if (x + 1 < maze.length && maze[x + 1][y] != '*') {
                route[a] = 'D';
                x += 1;
                a += 1;
            } else if (y - 1 >= 0 && maze[x][y - 1] != '*') {
                route[a] = 'L';
                y -= 1;
                a += 1;
            } else if (y + 1 < maze[0].length && maze[x][y + 1] != '*') {
                route[a] = 'R';
                y += 1;
                a += 1;
            } else if (x - 1 >= 0 && maze[x - 1][y] != '*') {
                route[a] = 'U';
                x -= 1;
                a += 1;
            } else {
                out.println("IMPOSSIBLE");
                return;
            }
        }
        for (int i = 0; i < k; i++) {
            int j = k + i;
            char c = route[k - i - 1];
            char b;
            if (c == 'D') {
                b = 'U';
            } else if (c == 'L') {
                b = 'R';
            } else if (c == 'R') {
                b = 'L';
            } else {
                b = 'D';
            }
            route[j] = b;
        }
        for (int i = 0; i < 2 * k; i++) {
            out.print(route[i]);
        }
    }
}
