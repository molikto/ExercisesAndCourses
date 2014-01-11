/*
ID: pirripe1
LANG: JAVA
TASK: hamming
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class hamming {

	private static final String DEBUG_STRING = "16 7 3";

	private static final boolean DEBUG = false;
	public static final String PRO = "hamming";
	
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

	public static int L;
	public static boolean[][] numBools;
	private static void problem(Scanner cin, Writer fw) throws IOException {
		int N = cin.nextInt();
		L = cin.nextInt();
		numBools = new boolean[1 << L][];
		int D = cin.nextInt();
		int[] nums = new int[N];
		nums[0] = 0;
		int index = 1;
		for (int i = 1; i < N; i++) {
			boolean find = false;
			for (int n = nums[index-1]; !find; n++) {
				calNumBools(n);
				find = true;
				for (int j = index-1; j >= 0; j--) {
					if (distence(n, nums[j]) < D) {
						find = false;
						break;
					}
				}
				if (find) {
					nums[index++] = n;
				}
			}
		}
		for (int i = 0, ten = 0; i < N; i++, ten = (ten+1)%10) {
			fw.write(nums[i] + ((ten == 9 || i == N-1) ? "\n" : " "));
		}
	}

	private static void calNumBools(int n) {
		if (numBools[n] == null) {
			numBools[n] = new boolean[L];
			byte[] bs = Integer.toBinaryString(n).getBytes();
			for (int i = bs.length-1, j = L-1; i >= 0 && j>=0; i--, j-- ) {
				numBools[n][j] = bs[i] == '1';
			}
		}
	}

	private static int distence(int ia, int ib) {
		int d = 0;
		for (int i = 0; i < L; i++) {
			if (numBools[ia][i] != numBools[ib][i]) {
				d++;
			}
		}
		return d;
	}

	
}
