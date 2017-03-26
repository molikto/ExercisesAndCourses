/*
ID: pirripe1
LANG: JAVA
TASK: butter
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.math.BigInteger;

public class butter {

    private static final String DEBUG_STRING = "5 10 24          4          5          1          8          5          8 1 187          2 10 11          3 10 144          3 8 30          3 9 123          10 1 222          10 7 170          7 4 169          2 3 158          6 8 124          7 5 213          2 6 14          4 2 192          10 6 87          4 8 207          7 1 212          4 1 12          10 8 64          6 1 160          5 1 38          7 6 28          5 3 62          9 1 79          4 3 125";



    private static final boolean DEBUG = false;
    public static final String PRO = "butter";

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

    public static class Edge implements Comparable<Edge> {
        public int to;
        public int length;

        @Override
        public int compareTo(Edge o) {
            if (o.length > length) {
                return -1;
            } else if (o.length < length) {
                return 1;
            }
            return o.to - to;
        }
    }
    public static class Node {
        public List<Edge> edges = new ArrayList<Edge>();
        int cows = 0;
    }


    private static void problem(Scanner cin, Writer fw) throws IOException {
        int cows_size = cin.nextInt();
        int places_size = cin.nextInt();
        int paths_size = cin.nextInt();
        // somehow this is FASTER than cows_place...
        Node[] nodes = new Node[places_size];
        int paths[][] = new int[places_size][];
        int MAX = Integer.MAX_VALUE / 3;
        for (int i = 0; i < places_size; i++) {
            nodes[i] = new Node();
            paths[i] = new int[places_size];
            for (int j = 0; j < places_size; j++) {
                paths[i][j] = MAX;
            }
            paths[i][i] = 0;
        }
        for (int i = 0; i < cows_size; i++) {
            nodes[cin.nextInt() - 1].cows += 1;
        }
        for (int i = 0; i < paths_size; i++) {
            int a = cin.nextInt() - 1;
            int b = cin.nextInt() - 1;
            int n = cin.nextInt();
            paths[a][b] = n;
            paths[b][a] = n;
            Edge ea = new Edge();
            ea.to = b;
            ea.length = n;
            Edge eb = new Edge();
            eb.to = a;
            eb.length = n;
            nodes[a].edges.add(ea);
            nodes[b].edges.add(eb);
        }

        int dis = places_size * places_size - places_size - paths_size * 2;

        Edge[] tempEdges = new Edge[places_size];
        for (int i = 0; i < places_size; i++) {
            tempEdges[i] = new Edge();
            tempEdges[i].to = i;
        }
        for (int i = 0; i < places_size; i++) {
            if (nodes[i].cows > 0 && dis > 0) {
                int[] ps = paths[i];
                PriorityQueue<Edge> q = new PriorityQueue<Edge>();
                for (int j = 0; j < places_size; j++) {
                    int p = ps[j];
                    if (p != MAX && p != 0) {
                        Edge d = tempEdges[j];
                        d.length = p;
                        q.add(d);
//                    } else {
//                        inQueue[j] = false;
                    }
                }
                while (!q.isEmpty()) {
                    Edge u = q.poll();
                    int uuu = ps[u.to];
                    for (Edge e : nodes[u.to].edges) {
                        int alt = uuu + e.length;
                        int eee = ps[e.to];
                        if (alt < eee) {
                            // by NOT REMOVING it is actually a lot faster.... SHIT
//                            if (inQueue[e.to]) {
//                                q.remove(tempEdges[e.to]);
//                            } else {
//                                inQueue[e.to] = true;
//                            }
                            if (eee == MAX) dis -= 2;
                            ps[e.to] = alt;
                            paths[e.to][i] = alt;
                            tempEdges[e.to].length = alt;
                            q.add(tempEdges[e.to]);
                        }
                    }
                }
            }
        }

        // OLD ONE
//        for (int k = 0; k < places_size; k++) {
//            for (int i = 0; i < places_size; i++) {
//                // getting this out of the loop saved me 0.2 seconds from 1.1 seconds...
//                int pp = paths[i][k];
//                // calculating only one side saved me 0.6 seconds...
//                for (int j = i + 1; j < places_size; j++) {
//                    int c = pp + paths[k][j];
//                    if (paths[i][j] > c) {
//                        paths[i][j] = c;
//                        paths[j][i] = c;
//                    }
//                }
//            }
//        }
        
        int costs_min = Integer.MAX_VALUE;
        for (int i = 0; i < places_size; i++) {
            int costs = 0;
            for (int j = 0; j < places_size; j++) {
                costs += nodes[j].cows * paths[j][i];
            }
            if (costs < costs_min) costs_min = costs;
        }
        fw.write(costs_min + "\n");
    }
}
