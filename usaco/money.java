/*
ID: pirripe1
LANG: JAVA
TASK: money
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;

// yes, this is the goddam shitty problem! and I write it for... month!!!

public class money {

	private static final String DEBUG_STRING = "3 10 1 2 5";
	private static final boolean DEBUG = false;
	public static final String PRO = "money";
	
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


	private static void problem(Scanner cin, Writer fw) throws IOException {
		int C = cin.nextInt();
		int N = cin.nextInt();
		int[] coins = new int[C];
		for (int i = 0; i < C; i++) {
			coins[i] = cin.nextInt();
		}
		long[][] res = new long[N+1][C];
		res[0][0] = 1;
		for (int ci = 0; ci < C; ci++) {
		//	res[0][ci] = 1;
		}
		for (int i = 1; i <= N; i++) {
			for (int ci = 0; ci < C; ci++) {
				if (i-coins[ci] >= 0) {
					for (int cii = 0; cii <= ci; cii++) {
						res[i][ci] += res[i-coins[ci]][cii];
					}
				}
			}
		}
		long rs = 0;
		for (int ci = 0; ci < C; ci++) {
			rs+=res[N][ci];
		}
		fw.write(rs+"\n");
	}
	
}
