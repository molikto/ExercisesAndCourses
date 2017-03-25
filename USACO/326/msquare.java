/*
ID: pirripe1
LANG: JAVA
TASK: msquare
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.math.BigInteger;

public class msquare {

	private static final String DEBUG_STRING = "4 1 2 3 5 8 7 6";



	private static final boolean DEBUG = false;
	public static final String PRO = "msquare";
	
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

	public static class Config {
		int a1, a2, a3, a4;
		int b1, b2, b3, b4;

		Config parent;
		char last;

		@Override
		public int hashCode() {
			return a1 * 2 + a2 * 3 + a3 * 5 + a4 * 7 + b1 * 11 + b2 * 13 + b3 * 17 + b4 * 73;
		}

		public String toString() {
			return a1  +" " + a2   +" " + a3   +" " + a4 + "\n" + b1   +" " + b2  +" "  + b3  +" "  + b4;
		}

		@Override
		public boolean equals(Object k) {
			if (k instanceof Config) {
				Config o = (Config) k;
				return a1 == o.a1 && a2 == o.a2 && a3 == o.a3 && a4 == o.a4 && b1 == o.b1 && b2 == o.b2 && b3 == o.b3 && b4 == o.b4;
			} else {
				return false;
			}
		}

		public Config(int a1, int a2, int a3, int a4, int b1, int b2, int b3, int b4, Config parent, char last) {
			this.a1 = a1;
			this.a2 = a2;
			this.a3 = a3;
			this.a4 = a4;
			this.b1 = b1;
			this.b2 = b2;
			this.b3 = b3;
			this.b4 = b4;
			this.parent = parent;
			this.last = last;
		}
	}


	static Config end;

	static HashSet<Config> sol = new HashSet<Config>();


	static Config solved = null;
	static void test(Config c, ArrayList<Config> current) {
		if (c.equals(end)) {
			solved = c;
		} else if (!sol.contains(c)) {
			sol.add(c);
			current.add(c);
		}
	}

	private static void problem(Scanner cin, Writer fw) throws IOException {
		Config start = new Config(1, 2, 3, 4, 8, 7, 6, 5, null, '\n');
		int[] ps = new int[8];
		for (int i = 0; i < 8; i++) {
			ps[i] = cin.nextInt();
		}
		end = new Config(ps[0], ps[1], ps[2], ps[3], ps[7], ps[6], ps[5], ps[4], null, '0');
		sol.add(start);
		ArrayList<Config> previous = new ArrayList<Config>();
		previous.add(start);

		if (start.equals(end)) {
			fw.write("0\n\n");
			return;
		}

		while (solved == null) {
			ArrayList<Config> current = new ArrayList<Config>(previous.size() * 2);
			for (Config p: previous) {
				test(new Config(p.b1, p.b2, p.b3, p.b4, p.a1, p.a2, p.a3, p.a4, p, 'A'), current);
				if (solved != null) break;
				test(new Config(p.a4, p.a1, p.a2, p.a3, p.b4, p.b1, p.b2, p.b3, p, 'B'), current);
				if (solved != null) break;
				test(new Config(p.a1, p.b2, p.a2, p.a4, p.b1, p.b3, p.a3, p.b4, p, 'C'), current);
				if (solved != null) break;
			}
			previous = current;
		}
		String str = "";
		while (solved != null) {
			str =  solved.last + str;
			solved = solved.parent;
		}
		str = str.substring(1);
		fw.write(str.length() + "\n");
		while (str != null) {
			fw.write(str.substring(0, str.length() > 60 ? 60 : str.length()) + "\n");
			str = str.length() > 60 ? str.substring(60) : null;
		}
	}
}
