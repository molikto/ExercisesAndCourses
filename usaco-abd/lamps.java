/*
ID: pirripe1
LANG: JAVA
TASK: lamps
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class lamps {

	private static final String DEBUG_STRING = "10 100 -1 7 -1";

	private static final boolean DEBUG = false;
	public static final String PRO = "lamps";
	
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

	public static Comparator<int[]> COM = new Comparator<int[]>() {
		@Override
		public int compare(int[] lhs, int[] rhs) {
			for (int i = 0; i < lhs.length; i++) {
				int c = lhs[i] - rhs[i];
				if (c != 0)
					return c;
			}
			return 0;
		}};
	private static void problem(Scanner cin, Writer fw) throws IOException {
		int N = cin.nextInt();
		int C = cin.nextInt();
		int[] res = new int[N];
		Arrays.fill(res, -1);
		boolean has = false;
		for (int i = cin.nextInt(); i != -1; i = cin.nextInt()) {
			res[i-1] = 1;
		}
		for (int i = cin.nextInt(); i != -1; i = cin.nextInt()) {
			res[i-1] = 0;
		}
		TreeSet<int[]> results = new TreeSet<int[]>(COM);
		int[] result0 = new int[N];
		Arrays.fill(result0, 1);
		results.add(result0);
		for (int i = 1; i <= C; i++) {
			TreeSet<int[]> news = new TreeSet<int[]>(COM);
			for (Iterator<int[]> ir = results.iterator(); ir.hasNext();) {
				int[] f = ir.next();
				news.add(reverse(f, 0, 1));
				news.add(reverse(f, 1, 2));
				news.add(reverse(f, 0, 2));
				news.add(reverse(f, 0, 3));
			}
			results = news;
		}
		for (Iterator<int[]> ir = results.iterator(); ir.hasNext();) {
			int[] f = ir.next();
			boolean good = true;
			for (int i = 0; i < N; i++) {
				if (res[i] >= 0) {
					if (f[i] != res[i]) {
						good = false;
						break;
					}
				}
			}
			if (good) {
				has = true;
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < N; i++) {
					sb.append(f[i]);
				}
				sb.append("\n");
				fw.write(sb.toString());
			}
		}
		if (!has) {
			fw.write("IMPOSSIBLE\n");
		}
	}
	private static int[] reverse(int[] f, int start, int step) {
		int[] t = Arrays.copyOf(f, f.length);
		for (int j = start; j < t.length; j+=step) {
			t[j] = (t[j] + 1) % 2;
		}
		return t;
	}
}
