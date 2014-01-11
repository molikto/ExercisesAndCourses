/*
ID: pirripe1
LANG: JAVA
TASK: comehome
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

// I even do not know why this method works...
// please look at analysis!!
// it is the d algorithm....

public class comehome {

	private static final String DEBUG_STRING = "3 Z a 1000 a A 1000   A B 1000 ";
	private static final boolean DEBUG = false;
	public static final String PRO = "comehome";
	
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
				FileReader fr = new FileReader(PRO+".in");
				cin = new Scanner(fr);
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

	
	public static class Path implements Comparable<Path> {
		Node a;
		Node b;
		int length;
		public Path(Node aa, Node bb, int l) {
			a = aa;
			b = bb;
			a.paths.add(this);
			b.paths.add(this);
			length = l;
		}
		@Override
		public int compareTo(Path o) {
			return length - o.length;
		}
	}
	public static class Node implements Comparable<Node> {
		public char name;
		public boolean visited = false;
		ArrayList<Path> paths = new ArrayList<Path>();
		public Node(char c) {
			name = c;
		}
		public int dis = Integer.MAX_VALUE;
		public void travel(int d) {
			if (d < dis) {
				dis = d;
			}
			PriorityQueue<Node> pq = new PriorityQueue<Node>();
			pq.add(this);
			while (!pq.isEmpty()) {
				Node n = pq.poll();
				if (!n.visited) {
					Collections.sort(n.paths);
					for (Path p : n.paths) {
						Node o = p.a == n ? p.b : p.a;
						if (!o.visited) {
							o.release(n.dis + p.length);
							pq.add(o);
						}
					}
					n.visited = true;
				}
			}
		}
		private void release(int i) {
			dis = Math.min(i, dis);
		}
		@Override
		public int compareTo(Node o) {
			return dis - o.dis;
		}
	}
	public static class Graph {
		public Node[] nodes = new Node['z'-'A'+1];
		public Node cth(char c) {
			return nodes[c-'A'];
		}
		public Graph() {
			for (char c = 'A'; c <= 'z'; c++) {
				if (c <= 'Z' || c >= 'a') {
					nodes[c-'A'] = new Node(c);
				}
			}
		}
		public Node min() {
			Node min = new Node('0');
			min.dis = Integer.MAX_VALUE;
			for (Node n : nodes) {
				if (n != null && n.name < 'Z' && n.dis < min.dis) {
					min = n;
				}
			}
			return min;
		}
	}
	private static void problem(Scanner cin, Writer fw) throws IOException {
		int pathNumber = cin.nextInt();
		Graph graph = new Graph();
		for (int i = 0; i < pathNumber; i++) {
			new Path(graph.cth(cin.next("[a-zA-Z]").charAt(0)), 
					graph.cth(cin.next("[a-zA-Z]").charAt(0)), cin.nextInt());
		}
		graph.cth('Z').travel(0);
		Node mn = graph.min();
		fw.write(mn.name + " " + mn.dis + "\n");
	}
	
}
