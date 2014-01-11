/*
ID: pirripe1
LANG: JAVA
TASK: humble
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;

import org.pirriperdos.algorithms.LongList;

public class humble {

	// optimize: use index counter
	// use current Nth to evaluate
	// using merge instead of sort
	
	// this is the ugliest program I ever write in USACO

	private static final String DEBUG_STRING = "100 100000 2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97 101 103 107 109 113 127 131 137 139 149 151 157 163 167 173 179 181 191 193 197 199 211 223 227 229 233 239 241 251 257 263 269 271 277 281 283 293 307 311 313 317 331 337 347 349 353 359 367 373 379 383 389 397 401 409 419 421 431 433 439 443 449 457 461 463 467 479 487 491 499 503 509 521 523 541";

	private static final boolean DEBUG = false;
	public static final String PRO = "humble";
	
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
		int K = cin.nextInt();
		int N = cin.nextInt();
		int[] ps = new int[K];
		for (int i = 0; i < K; i++) ps[i] = cin.nextInt();
		// different treatment for phase and dense array. shit!!!
		if (K > 20) {
			// using int instead of long to pass the time limit
			for (int num = 2;; num++) {
				int tn = num;
				for (int p : ps) {
					while (tn % p == 0) {
						tn /= p;
					}
				}
				if (tn == 1) {
					N--;
					if (N == 0) {
						fw.write(num+"\n");
						return;
					}
				}
			}
		}
		N--;
		LongList nums = new LongList(0);
		LongList[] results = new LongList[K];
		for (int i = 0; i < K; i++) {
			results[i] = new LongList(0);
			results[i].append(ps[i]);
			nums.append(ps[i]);
		}
		long Nth = Long.MAX_VALUE;
		long counter = ps[0];
		while (true) {
			counter *= ps[0];
			LongList preNums = new LongList(0);
			LongList[] newResults = new LongList[K];
			for (int i = 0; i < K; i++) {
				preNums.merge(results[i]);
				newResults[i] = new LongList(preNums.length());
				for (int j = 0; j < preNums.length(); j++) {
					long num = preNums.get(j);
					if (Nth / ps[i] >= num) {
						num = num * ps[i];
						newResults[i].append(num);
					}
				}
			}
			LongList newNums = new LongList(0);
			for (int i = 0; i < K; i++) {
				newNums.merge(newResults[i]);
			}
			nums.merge(newNums);
			int Ndif = nums.index(counter);
			if (Ndif < 0) {
				break;
			}
			nums.sublist(Ndif, nums.length());
			N-=Ndif;
			if (nums.length() > N) {
				nums.setLength(N+1);
				Nth = nums.get(N);
			}
			results = newResults;
		}
		System.gc();
		fw.write(nums.get(N)+"\n");
	}
	
}
