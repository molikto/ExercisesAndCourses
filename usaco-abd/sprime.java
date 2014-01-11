/*
ID: pirripe1
LANG: JAVA
TASK: sprime
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


// gei gui le 
// The problem can be simplified slightly by noticing that any even palindrome 
// is divisible by 11. Therefore, 11 is the ONLY even prime palindrome. This
// eliminates the need to deal with 2 cases:


public class sprime {

	private static final String DEBUG_STRING = "4";

	private static final boolean DEBUG = false;
	public static final String PRO = "sprime";
	
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

	private static void problem(Scanner cin, Writer fw) throws IOException {
		int N = cin.nextInt();
		int[] primes = genPrimes((int) Math.sqrt((int) Math.pow(10, N)));
		int[][] ribs = new int[N+1][];
		ribs[1] = new int[]{2, 3, 5, 7};
		for (int i = 2; i <= N; i++) {
			// gen rib of len i
			ArrayList<Integer> res = new ArrayList<Integer>();
			for (int rib : ribs[i-1]) {
				for (int pre = 1; pre < 10; pre +=2) {
					int r = Integer.parseInt(Integer.toString(rib)
							+ Integer.toString(pre));
					if (isPrime(primes, r)) {
						res.add(r);
					}
				}
			}
			ribs[i] = listToArray(res);
		}
		for (int p : ribs[N]) {
			fw.write(p + "\n");
		}
	}

	private static boolean isPrime(int[] primes, int r) {
		for (int p : primes) {
			if (p == r)
				return true;
			if (r % p == 0) {
				return false;
			}
		}
		return true;
	}

	// shit.. should not use this at all....
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

}
