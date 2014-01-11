/*
ID: pirripe1
LANG: JAVA
TASK: pprime
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


public class pprime {

	private static final String DEBUG_STRING = "5 500";

	private static final boolean DEBUG = false;
	public static final String PRO = "pprime";
	
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
		int from = cin.nextInt();
		int to = cin.nextInt();
		String fromStr = Integer.toString(from);
		int odd = fromStr.length()%2;
		int gen = from / ((int) Math.pow(10, fromStr.length()/2));
		int[] primes = genPrimes((int)Math.sqrt(to));
		String genStr = Integer.toString(gen);
		for (;;) {
			byte[] bytes = Arrays.copyOf(genStr.getBytes(), genStr.length() * 2 - odd);
			for (int i = 0; i < bytes.length/2; i++) {
				bytes[bytes.length-i-1] = bytes[i];
			}
			int res = Integer.parseInt(new String(bytes));
			// footer
			if (res > to)
				break;
			
			boolean isPrime = true;
			for (int p : primes) {
				if (p == res)
					break;
				if (res % p == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				fw.write(res+"\n");
			}

			gen += 1;
			int next = gen;
			String temp = Integer.toString(next);
			if (temp.length() > genStr.length()) {
				if (odd == 1) {
					odd = 0;
					gen /= 10;
					genStr = Integer.toString(gen);
				} else {
					odd = 1;
					genStr = temp;
				}
			} else {
				genStr = temp;
			}
		}
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
		int[] as = new int[primes.size()];
		for (int i = 0; i < as.length; i++) {
			as[i] = primes.get(i);
		}
		return as;
	}

}
