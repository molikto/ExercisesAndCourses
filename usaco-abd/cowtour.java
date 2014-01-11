/*
ID: pirripe1
LANG: JAVA
TASK: cowtour
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class cowtour {

	
	// shit. forget the self max situation!!!
	private static final String DEBUG_STRING = "8"+
"\n10 10"+
"\n15 10"+
"\n20 10"+
"\n15 15"+
"\n20 15"+
"\n30 15"+
"\n25 10"+
"\n30 10"+
"\n01000000"+
"\n10111000"+
"\n01001000"+
"\n01001000"+
"\n01110000"+
"\n00000010"+
"\n00000101"+
"\n00000010";

	private static final boolean DEBUG = false;
	public static final String PRO = "cowtour";
	
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

	
	public static final double VERYBIG = 100000000;
	private static void problem(Scanner cin, Writer fw) throws IOException {
		int N = cin.nextInt();
		int[][] ps = new int[N][2];
		char[][] adjs = new char[N][];
		double[][] dis = new double[N][N];
		for (int i = 0; i < N; i++) {
			ps[i][0] = cin.nextInt();
			ps[i][1] = cin.nextInt();
			Arrays.fill(dis[i], VERYBIG);
			dis[i][i] = 0;
		}
		cin.nextLine();
		for (int i = 0; i < N; i++) {
			adjs[i] = cin.nextLine().toCharArray();
		}
		for (int a = 0; a < N; a++) {
			for (int b = 0; b < a; b++) {
				if (adjs[a][b] == '1') {
					dis[b][a] = dis[a][b] = distence(ps[a], ps[b]);
				}
			}
		}
		boolean changed = true;
		while (changed) {
			changed = false;
			for (int k = 0; k < N; k++) { // do not understand!!!
				for (int a = 0; a < N; a++) {
					for (int b = 0; b < N; b++) {
						if (dis[a][b] > dis[a][k] + dis[k][b]) {
							dis[a][b] = dis[a][k] + dis[k][b];
							changed = true;
						}
					}
				}
			}
		}
		double max2 = 0;
		double[] maxdis = new double[N];
		for (int a = 0; a < N; a++) {
			double max = 0;
			for (int i = 0; i< N; i++) {
				if (dis[a][i] != VERYBIG) {
					max = Math.max(max, dis[a][i]);
				}
			}
			maxdis[a] = max;
			max2 = Math.max(max2, max);
		}
		double min = Double.MAX_VALUE;
		for (int a = 0; a < N; a++) {
			for (int b = 0; b < N; b++) {
				if (dis[a][b] == VERYBIG) {
					min = Math.min(min, distence(ps[a], ps[b])+maxdis[a]+maxdis[b]);
				}
			}
		}
		min = Math.max(max2, min);
		fw.write(String.format("%.6f\n", min));
	}

	private static double distence(int[] a, int[] b) {
		return Math.sqrt(Math.pow(a[0]-b[0], 2)+Math.pow(a[1]-b[1], 2));
	}
}
