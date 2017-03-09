/*
ID: pirripe1
LANG: JAVA
TASK: spin
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

public class spin {

	private static final String DEBUG_STRING = "1 1 140 359          1 1 200 359          1 1 4 1          2 1 6 1          1 1 300 300 ";

	private static final boolean DEBUG = false;
	public static final String PRO = "spin";
	
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

	static class Wheel {
		int speed;
		int[] ws = new int[360];
	}

	static int add(int a, int b) {
		return (a + b) % 360;
	}

	static void pp(int[] s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 100; i += 1) {
			sb.append(s[i]);
		}
		System.out.println(sb.toString());
	}

	private static void problem(Scanner cin, Writer fw) throws IOException {
		Wheel[] ws = new Wheel[5];
		for (int i = 0; i < 5; i++) {
			Wheel w = new Wheel();
			ws[i] = w;
			w.speed = cin.nextInt();
			int c = cin.nextInt();
			for (int j = 0; j < c; j++) {
				int s = cin.nextInt();
				int e = cin.nextInt();
				w.ws[s] = 1;
				for (int k = add(s, 1); k != add(s, e + 1); k = add(k, 1)) {
					w.ws[k] = 1;
				}
			}
		}
		for (int i = 0; i < 360; i++) { // speed
			int[] s = new int[360];
			for (int k = 0; k < 360; k++) { // pos
				for (int j = 0; j < 5; j++) {
					Wheel w = ws[j];
					if (w.ws[k] == 1) {
						s[add(w.speed * i, k)] += 1;
					}
				}
			}
			for (int k = 0; k < 360; k++) {
				if (s[k] == 5) {
					fw.write(i + "\n");
					return;
				}
			}
		}
		fw.write("none" + "\n");
	}
}
