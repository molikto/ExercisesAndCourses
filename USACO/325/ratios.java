/*
ID: pirripe1
LANG: JAVA
TASK: ratios
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

public class ratios {

	private static final String DEBUG_STRING = "5 8 0          3 5 4          1 3 0          6 2 0";


	private static final boolean DEBUG = false;
	public static final String PRO = "ratios";
	
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

	static class Ratio {
		int a;
		int b;
		int c;
		public Ratio(Scanner cin) {
			a = cin.nextInt();
			b = cin.nextInt();
			c = cin.nextInt();
		}
	}

	static boolean isr(int a, int b) {
		if (b == 0) {
			if (a == 0) return true;
			else return false;
		} else return a != 0 && a % b == 0;
	}

	private static void problem(Scanner cin, Writer fw) throws IOException {
		Ratio good = new Ratio(cin);
		Ratio[] ms = new Ratio[3];
		for (int i = 0; i < 3; i++)
			ms[i] = new Ratio(cin);
		for (int sum = 1; sum < 99*3 + 1; sum++) {
			for (int a = 0; a <= sum && a < 100; a++) {
				for (int b = 0; b <= sum - a && b < 100; b++) {
					int c = sum - a - b;
					if (c < 100) {
						int aa = a * ms[0].a + b * ms[1].a + c * ms[2].a;
						int bb = a * ms[0].b + b * ms[1].b + c * ms[2].b;
						int cc = a * ms[0].c + b * ms[1].c + c * ms[2].c;
						if (isr(aa, good.a) && isr(bb, good.b) && isr(cc, good.c)) {
							int ra = aa == 0 ? -1 : aa / good.a;
							int rb = bb == 0 ? -1 : bb / good.b;
							int rc = cc == 0 ? -1 : cc / good.c;
							if (a == 0 && b == 38 && c == 7) System.out.println(ra + " " + rb + " " + rc);
							int acc = -1;
							if (acc == -1) acc = ra;
							else if (acc != ra && ra != -1) continue;
							if (acc == -1) acc = rb;
							else if (acc != rb && rb != -1) continue;
							if (acc == -1) acc = rc;
							else if (acc != rc && rc != -1) continue;
							fw.write(a + " " + b + " " + c + " " + ra + "\n");
							return;
						}
					}
				}
			}
		}
		fw.write("NONE" + "\n");
	}
}
