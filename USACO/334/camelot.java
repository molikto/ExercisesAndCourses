/*
ID: pirripe1
LANG: JAVA
TASK: camelot
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.regex.Pattern;

public class camelot {

    private static final String DEBUG_STRING = "8 8          G 4          E 4 D 6";



    private static final boolean DEBUG = false;
    public static final String PRO = "camelot";

    public static void main(String[] args) {
        if (DEBUG) {
            Scanner cin = new Scanner(DEBUG_STRING);
            StringWriter sw = new StringWriter();
            try {
                problem(cin, sw);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(sw.toString());
        } else {
            Scanner cin = null;
            try {
                cin = new Scanner(new FileReader(PRO+".in"));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            FileWriter fw = null;
            try {
                fw = new FileWriter(PRO+".out");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                problem(cin, fw);
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            cin.close();
        }
    }


    public static class Position {
        int row;
        int col;
        public Position(int r, int c) {
            row = r;
            col = c;
        }
    }

    static ArrayList<Position>  ks;
    static Position king;

    static int[][][][] small;


    static void map(int[][] small, int r, int c, int s) {
        if (r >= 0 && c >= 0 && r < R && c < C) {
            int cs = small[r][c];
            if (s + 1 < cs) {
                small[r][c] = s + 1;
                waiting.add(new Position(r, c));
            }
        }
    }

    static Queue<Position> waiting = new LinkedList<Position>();

    static int[][] init(int ssr, int ssc) {
        int[][] small = new int[R][];
        for (int i = 0; i < small.length; i++) {
            small[i] = new int[C];
            for (int j = 0; j < C; j++) small[i][j] = Integer.MAX_VALUE / 10;
        }
        small[ssr][ssc] = 0;
        waiting.clear();
        waiting.add(new Position(ssr,ssc));
        while (!waiting.isEmpty()) {
            Position p = waiting.poll();
            int s = small[p.row][p.col];
            map(small, p.row - 2, p.col - 1, s);
            map(small, p.row - 2, p.col + 1, s);
            map(small, p.row + 2, p.col - 1, s);
            map(small, p.row + 2, p.col + 1, s);
            map(small, p.row - 1, p.col - 2, s);
            map(small, p.row - 1, p.col + 2, s);
            map(small, p.row + 1, p.col - 2, s);
            map(small, p.row + 1, p.col + 2, s);
        }
        return small;
    }
    static int KK = 8;
    static int DR;
    static int DC;
    static void init() {
        DR = Math.min(KK, R);
        DC = Math.min(KK, C);
        small = new int[DR][][][];
        for (int i = 0; i < DR; i++) {
            small[i] = new int[DC][][];
            for (int j = 0; j < DC; j++) {
                small[i][j] = init(i, j);
            }
        }
    }

    static int length(int r, int c, int rr, int cc) {
        int RR = R - 1;
        int CC = C - 1;
        if (r < DR && c < DC) {
            return small[r][c][rr][cc];
        } else if (RR - r < DR && c < DC) {
            return small[RR - r][c][RR - rr][cc];
        } else if (r < DR && CC- c < DC) {
            return small[r][CC - c][rr][CC - cc];
        } else if (RR - r < DR && CC - c < DC) {
            return small[RR - r][CC - c][RR - rr][CC - cc];
        } else if (rr < DR && cc < DC) {
            return small[rr][cc][r][c];
        } else if (RR - rr< DR && cc < DC) {
            return small[RR- rr][cc][RR - r][c];
        } else if (rr < DR && CC- cc < DC) {
            return small[rr][CC - cc][r][CC - c];
        } else if (RR- rr < DR && CC- cc < DC) {
            return small[RR - rr][CC - cc][RR - r][CC - c];
        } else {
            int dr = Integer.MAX_VALUE;
            dr = Math.min(dr, r);
            dr = Math.min(dr, RR - r);
            dr = Math.min(dr, rr);
            dr = Math.min(dr, RR - rr);
            dr = Math.min(dr, DR - 1);
            int dc = Integer.MAX_VALUE;
            dc = Math.min(dc, c);
            dc = Math.min(dc, CC - c);
            dc = Math.min(dc, cc);
            dc = Math.min(dc, CC - cc);
            dc = Math.min(dc, DC - 1);
            return small[dr][dc][Math.abs(r - rr) + dr][Math.abs(c - cc) + dc];
        }
    }

    static int min = Integer.MAX_VALUE;


    static void solve(int r, int c) {
        if (r < R && r >= 0 && c < C && c >= 0) {
            int total = 0;
            int getKingAddMin = Integer.MAX_VALUE;
            for (Position k : ks) {
                int normal = length(k.row, k.col, r, c);
                total += normal;
                int getKing = Integer.MAX_VALUE;
                int DD = 2;
                for (int ki = king.row - DD; ki < king.row + DD + 1; ki++) {
                    for (int kj = king.col - 2; kj < king.col + DD + 1; kj++) {
                        if (ki >= 0 && ki < R && kj >= 0 && kj < C) {
                            int add = Math.max(Math.abs(king.row - ki), Math.abs(king.col - kj));
                            int dis = length(k.row, k.col, ki, kj) + length(ki , kj ,r,  c) + add;
                            if (dis < getKing) getKing = dis;
                        }
                    }
                }
                int added = getKing - normal;
                if (getKingAddMin > added) getKingAddMin = added;
            }
            total += getKingAddMin;
            if (total < min) min = total;
        }
    }
    static int R;
    static int C;
    private static void problem(Scanner cin, Writer fw) throws IOException {
        R = cin.nextInt();
        C = cin.nextInt();
        ks = new ArrayList<Position>();
        cin.useDelimiter(Pattern.compile("[ \\n]+"));
        while (cin.hasNext()) {
            String b = cin.next();
            char bb = b.charAt(0);
            while (cin.hasNext()) {
                if (bb >= 'A' && bb <= 'Z') {
                    break;
                }
                b = cin.next();
                bb = b.charAt(0);
            }
            if (bb >= 'A' && bb <= 'Z') {
                Position p = new Position(cin.nextInt() - 1, bb - 'A');
                ks.add(p);
            }
        }
        king = ks.get(0);
        ks.remove(0);
        init();

        long rs = 0;
        long cs = 0;
        for (Position k : ks) {
            rs += k.row; cs += k.col;
        }
        if (ks.size() == 0) {
            min = 0;
        } else {
            int rc = (int) (rs / ks.size());
            int cc = (int) (cs / ks.size());
            int RAD = 3;
            if (ks.size() < 10) {
                for (int i = 0; i < R; i++) {
                    for (int j = 0; j < C; j++) {
                        solve(i, j);
                    }
                }
            } else {
                for (int i = rc - RAD; i < rc + RAD + 1; i++) {
                    for (int j = cc - RAD; j < cc + RAD + 1; j++) {
                        solve(i, j);
                    }
                }
            }
        }
        fw.write(min + "\n");
    }
}
