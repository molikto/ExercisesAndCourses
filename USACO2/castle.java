/*
ID: pirripe1
LANG: JAVA
TASK: castle
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class castle {

	public static class UnionFind {

		final int[] arr;
		private int[] counts;
		public UnionFind(int n) {
			arr = new int[n];
			counts = new int[n];
			for (int i = 0; i < n; i++) {
				arr[i] = i;
				counts[i] = 1;
			}
		}

		public UnionFind(UnionFind o) {
			arr = Arrays.copyOf(o.arr, o.arr.length);
			counts = Arrays.copyOf(o.counts, o.counts.length);
		}
		
		public int find(int i) {
			if (arr[i] == i) {
				return i;
			} else {
				return find(arr[i]);
			}
		}
		
		public void union(int i, int j) {
			int ri = find(i), rj = find(j);
			if (ri == rj) return;
			if (counts[ri] >= counts[rj]) {
				arr[rj] = ri;
				counts[ri] += counts[rj];
			} else {
				arr[ri] = rj;
				counts[rj] += counts[ri];
			}
		}
		
		public boolean connected(int i, int j) {
			return find(i) == find(j);
		}
		
		public int[][] compoments() {
			int [][] coms = new int[arr.length][];
			int[] counter = new int[arr.length];
			for (int i = 0; i < arr.length; i++) {
				int c = find(i);
				if (coms[c] == null) {
					coms[c] = new int[counts[c]];
				}
				coms[c][counter[c]++] = i;
			}
			Arrays.sort(coms, Comparators.arrayLenRev);
			int index = 0;
			for (; index < coms.length && coms[index] != null; index++);
			return Arrays.copyOf(coms, index);
		}

		
	}

	private static final String DEBUG_STRING = "7 4 11 6 11 6 3 10 6 7 9 6 13 5 15 5 1 10 12 7 13 7 5 13 11 10 8 10 12 13";

	private static final boolean DEBUG = false;
	public static final String PRO = "castle";
	public static class Comparators {

		public static Comparator<int[]> arrayLenRev = new Comparator<int[]>() {
			@Override
			public int compare(int[] lhs, int[] rhs) {
				if (lhs == null) {
					return 1000;
				}
				if (rhs == null) {
					return -1000;
				}
				return rhs.length - lhs.length;
			}
			
		};
	}
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

	private static void problem(Scanner cin, Writer fw) throws IOException {
		int M = cin.nextInt(), N = cin.nextInt();
		int[][] graph = new int[M*N][];
		for (int i = 0; i < M * N; i++) {
			int num = cin.nextInt();
			int [] raw = new int[4];
			int rawIndex = 0;
			for (int j = 0; j < 4; j++) {
				if (num % 2 == 0) {
					switch (j) {
					case 0: raw[rawIndex++] = i-1; break;
					case 1: raw[rawIndex++] = i-M; break;
					case 2: raw[rawIndex++] = i+1; break;
					case 3: raw[rawIndex++] = i+M; break;
					}
				}
				num >>= 1;
			}
			graph[i] = Arrays.copyOf(raw, rawIndex);
		}
		UnionFind uf = new UnionFind(graph.length);
		for (int i = 0; i < graph.length; i++) {
			for (int j : graph[i]) {
				uf.union(i, j);
			}
		}
		int[][] coms = uf.compoments();
		int maxSum = 0;
		int maxWall = 0;
		char maxOrd = 0;
		for (int i = 0; i < coms.length; i++) {
			for (int j = i + 1; j < coms.length; j++) {
				int[] c1 = coms[i];
				int[] c2 = coms[j];
				int compare = c1.length + c2.length;
				if (compare >= maxSum) {
					int wall = -1;
					char ord = 'N';
					for (int cc : c1) {
						if (Arrays.binarySearch(c2, cc-M) >= 0) {
							if (wall < 0 || transpose(wall, M) < transpose(cc, M)) {
								wall = cc;
								ord = 'N';
							}
						} else if ((cc+1) % M > 0 && Arrays.binarySearch(c2, cc+1) >= 0) {
							if (wall < 0 || transpose(wall, M) < transpose(cc, M)) {
								wall = cc;
								ord = 'E';
							}
						}
					}
					for (int cc : c2) {
						if (Arrays.binarySearch(c1, cc-M) >= 0) {
							if (wall < 0 || transpose(wall, M) < transpose(cc, M)) {
								wall = cc;
								ord = 'N';
							}
						} else if ((cc+1) % M > 0 && Arrays.binarySearch(c1, cc+1) >= 0) {
							if (wall < 0 || transpose(wall, M) < transpose(cc, M)) {
								wall = cc;
								ord = 'E';
							}
						}
					}
					if (wall >= 0 && (compare > maxSum || transpose(wall, M) > transpose(maxWall, M))) {
						maxSum = compare;
						maxWall = wall;
						maxOrd = ord;
					}
				}
			}
		}
		
		fw.write(coms.length + "\n" + coms[0].length + "\n" + maxSum
				+ "\n" + (maxWall / M + 1) + " " + (maxWall % M + 1) + " " + maxOrd + "\n");
	}

	private static int transpose(int wall, int m) {
		return (1000 - wall % m) * 1000 + wall / m;
	}
	
}
