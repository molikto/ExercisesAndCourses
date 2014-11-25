/*
ID: pirripe1
LANG: JAVA
TASK: holstein
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Scanner;

// dynamic programming rocks

public class holstein {

	private static final String DEBUG_STRING = "5     10 20 30 40 50  5  10 10 10 10 10   0 10 10 10 10   0 0 10 10 10    0 0 0 10 10     0 0 0 0 10";

	private static final boolean DEBUG = false;
	public static final String PRO = "holstein";
	
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

	static int VN, SN;
	static int[][] SVS;
	static int[] VS;
	public static class Result {
		boolean[] ss;
		int[] vs;
		public Result() {
			ss = new boolean[SN];
			vs = new int[VN];
		}
		public Result(Result o, int a) {
			ss = Arrays.copyOf(o.ss, o.ss.length);
			vs = Arrays.copyOf(o.vs, o.vs.length);
			if (!ss[a]) {
				ss[a] = true;
				for (int i = 0; i < VN; i++) {
					vs[i] += SVS[a][i];
				}
			}
		}
		public boolean good() {
			for (int i = 0; i < VS.length; i++) {
				if (vs[i] < VS[i]) {
					return false;
				}
			}
			return true;
		}
	}
	
	private static void problem(Scanner cin, Writer fw) throws IOException {
		VN = cin.nextInt();
		VS = new int[VN];
		for (int i = 0; i < VS.length; i++) {
			VS[i] = cin.nextInt();
		}
		SN = cin.nextInt();
		SVS = new int[SN][VN];
		for (int i = 0; i < SVS.length; i++) {
			SVS[i] = new int[VN];
			for (int j = 0; j < SVS[i].length; j++) {
				SVS[i][j] = cin.nextInt();
			}
		}
		Result[][] results = new Result[SN+1][];
		results[0] = new Result[]{new Result()};
		for (int len = 1; len <= SN; len++) {
			results[len] = new Result[results[len-1].length * (SN - len + 1)/ len];
			int index = 0;
			for (Result oldResult : results[len-1]) {
				int j = oldResult.ss.length-1;
				for (; j >= 0 && !oldResult.ss[j]; j--);
				for (j++; j < oldResult.ss.length; j++) {
					Result newResult = new Result(oldResult, j);
					if (newResult.good()) {
						fw.write(len + " ");
						int ci = 0;
						for (int i = 0; i < newResult.ss.length; i++) {
							if (newResult.ss[i]) {
								fw.write((i+1) + (ci == len-1 ? "\n" : " "));
								ci++;
							}
						}
						return;
					}
					results[len][index++] = newResult;
				}
			}
		}
	}

}
