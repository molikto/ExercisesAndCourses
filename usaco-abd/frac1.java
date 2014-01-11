/*
ID: pirripe1
LANG: JAVA
TASK: frac1
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

// using u /d - ou/od is BAD!!! wrong answers!!!
// the one in solution is .... fantastic...

public class frac1 {

	private static final String DEBUG_STRING = "160";

	private static final boolean DEBUG = false;
	public static final String PRO = "frac1";
	
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

	private static int[] genPrimes(int to) {
		ArrayList<Integer> primes = new ArrayList<Integer>();
		for (int i = 2; i <= to; i++) {
			boolean is = true;
			for (int p : primes) {
				if (i % p == 0) {
					is = false;
					break;
				}
			}
			if (is)
				primes.add(i);
		}
		return listToArray(primes);
	}

	private static int[] listToArray(ArrayList<Integer> primes) {
		int[] as = new int[primes.size()];
		for (int i = 0; i < as.length; i++) {
			as[i] = primes.get(i);
		}
		return as;
	}
	
	public static class Rational implements Comparable<Rational>{
		public Rational(int a, int b) {
			u = a;
			d = b;
		}
		int u;
		int d;
		@Override
		public int compareTo(Rational o) {
			return u * o.d - o.u * d;
		}
	}
	public static class ReduceRationals {
		static int[] primes;
		static int[] tempPrimes;
		static {
			primes = genPrimes(200);
			tempPrimes = new int[primes.length];
		}
		int[] pfs;
		int d;
		public ReduceRationals(int d) {
			this.d = d;
			int ti = 0;
			for (int p : primes) {
				if (d % p == 0) {
					tempPrimes[ti++] = p;
				}
			}
			pfs = Arrays.copyOf(tempPrimes, ti);
		}
		public ArrayList<Rational> getList() {
			ArrayList<Rational> rationals = new ArrayList<Rational>();
			for (int i = 0; i <= d; i++) {
				if (reduced(i)) {
					rationals.add(new Rational(i, d));
				}
			}
			return rationals;
		}
		
		private boolean reduced(int i) {
			for (int f : pfs) {
				if (i % f == 0) {
					return false;
				}
			}
			return true;
		}
	}
	private static void problem(Scanner cin, Writer fw) throws IOException {
		int N = cin.nextInt();
		int[] couts = new int[N];
		ArrayList<Rational> rs = new ArrayList<Rational>();
		for (int i = 1; i <= N; i++) {
			rs.addAll(new ReduceRationals(i).getList());
		}
		Collections.sort(rs);
		for (Rational r : rs) {
			fw.write(r.u + "/" + r.d + "\n");
		}
	}

}
