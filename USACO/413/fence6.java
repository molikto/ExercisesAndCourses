/*
ID: pirripe1
LANG: JAVA
TASK: fence6
*/


import javax.swing.text.Segment;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

public class fence6 {

    private static final String DEBUG_STRING = "10 1 16 2 2 2 7 10 6 2 3 2 2 1 7 8 3 3 3 2 1 8 2 4 4 8 1 3 3 9 10 5 5 8 3 1 9 10 4 6 6 6 1 2  5  1 10 7 5 2 2  1 2 8 9 8 4 2 2 2 3 7 9 9 5 2 3 7 8 4 5 10 10 10 2 3 1 6 4 9 5";


    private static final boolean DEBUG = false;
    public static final String PRO = "fence6";

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
                cin = new Scanner(new FileReader(PRO + ".in"));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            FileWriter fw = null;
            try {
                fw = new FileWriter(PRO + ".out");
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



    public static class Node {
        int id;
        ArrayList<Edge> edges = new ArrayList<Edge>();
    }

    public static class Edge {
        int id;
        Node left;
        Node right;
        int length;
    }


    public static class NodeKey {
        int[] att;
        int index;
        public NodeKey(int[] att, int index) {
            this.att = att;
            this.index = index;
        }
    }

    private static void problem(Scanner cin, Writer fw) throws IOException {
        int N = cin.nextInt();
        Node[] nodes = new Node[N * 2];
        Edge[] edges = new Edge[N];
        int nlen = 0;
        NodeKey[][] nodesMap = new NodeKey[N][];
        for (int i = 0; i < N; i++) {
            nodesMap[i] = new NodeKey[2];
        }
        for (int i = 0; i < N; i++) {
            int j = cin.nextInt() - 1;
            int len = cin.nextInt();
            int n1 = cin.nextInt();
            int n2 = cin.nextInt();
            int[] left = new int[n1 + 1];
            int[] right = new int[n2 + 1];
            for (int i1 = 0; i1 < n1; i1++) left[i1] = cin.nextInt() - 1;
            left[n1] = j;
            for (int i2 = 0; i2 < n2; i2++) right[i2] = cin.nextInt() - 1;
            right[n2] = j;
            Arrays.sort(left);
            Arrays.sort(right);
            Edge edge = new Edge();
            edge.length = len;
            edges[j] = edge;
            edge.id = j;
            Node nleft, nright;
            if (Arrays.equals(left, right)) {
                if (nodesMap[left[0]][0] == null) {
                    nleft = new Node();
                    nleft.id = nlen;
                    nlen += 1;
                    nodes[nleft.id] = nleft;
                    nodesMap[left[0]][0] = new NodeKey(left, nleft.id);

                    nright = new Node();
                    nright.id = nlen;
                    nlen += 1;
                    nodes[nright.id] = nright;
                    nodesMap[left[0]][0] = new NodeKey(left, nright.id);
                } else {
                    nleft = nodes[nodesMap[left[0]][0].index];
                    nright = nodes[nodesMap[left[0]][1].index];
                }
            } else {
                if (nodesMap[left[0]][0] != null && Arrays.equals(nodesMap[left[0]][0].att, left)) {
                    nleft = nodes[nodesMap[left[0]][0].index];
                } else if (nodesMap[left[0]][1] != null && Arrays.equals(nodesMap[left[0]][1].att, left)) {
                    nleft = nodes[nodesMap[left[0]][1].index];
                } else {
                    nleft = new Node();
                    nleft.id = nlen;
                    nlen += 1;
                    nodes[nleft.id] = nleft;
                    nodesMap[left[0]][nodesMap[left[0]][0] == null ? 0 : 1] = new NodeKey(left, nleft.id);
                }

                if (nodesMap[right[0]][0] != null && Arrays.equals(nodesMap[right[0]][0].att, right)) {
                    nright = nodes[nodesMap[right[0]][0].index];
                } else if (nodesMap[right[0]][1] != null && Arrays.equals(nodesMap[right[0]][1].att, right)) {
                    nright = nodes[nodesMap[right[0]][1].index];
                } else {
                    nright = new Node();
                    nright.id = nlen;
                    nlen += 1;
                    nodes[nright.id] = nright;
                    nodesMap[right[0]][nodesMap[right[0]][0] == null ? 0 : 1] = new NodeKey(right, nright.id);
                }
            }
            nleft.edges.add(edge);
            nright.edges.add(edge);
            edge.left = nleft;
            edge.right = nright;
        }
        ArrayList<Segment> segments = new ArrayList<Segment>();
        for (Edge e : edges) {
            Segment seg = new Segment();
            seg.nodes = new Node[] {e.left, e.right};
            seg.length = e.length;
            segments.add(seg);
        }
        int min = Integer.MAX_VALUE;
        while (!segments.isEmpty()) {
            ArrayList<Segment> newSegments = new ArrayList<Segment>();
            for (Segment seg : segments) {
                Node last = seg.nodes[seg.nodes.length - 1];
                for (Edge e: last.edges) {
                    Node other = e.left == last ? e.right : e.left;
                    int newLength = seg.length + e.length;
                    if (newLength < min) {
                        if (other == seg.nodes[0]) {
                            if (seg.nodes.length > 2) min = newLength;
                        } else {
                            boolean loop = false;
                            for (int i = 1; i < seg.nodes.length; i++) {
                                if (seg.nodes[i] == other) {
                                    loop = true;
                                    break;
                                }
                            }
                            if (!loop) {
                                Segment se = new Segment();
                                se.nodes = Arrays.copyOf(seg.nodes, seg.nodes.length + 1);
                                se.nodes[se.nodes.length - 1] = other;
                                se.length = seg.length + e.length;
                                newSegments.add(se);
                            }
                        }
                    }
                }
            }
            segments = newSegments;
        }
        fw.write(min + "\n");
    }

    public static class Segment {
        Node[] nodes;
        int length;
    }

}
