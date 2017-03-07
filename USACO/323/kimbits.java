/*
ID: pirripe1
LANG: JAVA
TASK: kimbits
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
import java.math.BigInteger;

public class kimbits {

	private static final String DEBUG_STRING = "        24 20 12936478 ";

	private static final boolean DEBUG = false;
	public static final String PRO = "kimbits";
	
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


	static BigInteger[] fact = new BigInteger[32];

	static long c(int n, int k) {
		return fact[n].divide(fact[k]).divide(fact[n - k]).longValue();
	}

// 17 3
	static long count(int n, int l) {
		long sum = 0;
		int k = n > l ? l : n;
		for (int i = 0; i <= k; i++) {
			sum += c(n, i);
		}
		return sum;
	}

	private static void problem(Scanner cin, Writer fw) throws IOException {
		fact[0] = new BigInteger(1 + "");
		for (int i = 1; i < 32; i++) {
			fact[i] = fact[i - 1].multiply(new BigInteger(i + ""));
		}
		int n = cin.nextInt();
		int l = cin.nextInt();
		long i = cin.nextLong();
		long[][] k = new long[n + 1][l + 1];
		for (int a = 0; a <= n; a++) {
			for (int j = 0; j <= l; j++) {
				k[a][j] = count(a, j);
			}
		}
		String a = "";
		for (int j = n; j >= 1; j--) {
			long m = k[j - 1][l];
			if (i > m) {
				a = a + "1";
				i -= m;
				if (l > 0) l -= 1;
			} else {
				a = a + "0";
			}
		}
		fw.write(a + "\n");
	}
}
