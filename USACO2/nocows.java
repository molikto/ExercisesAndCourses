/*
ID: pirripe1
LANG: JAVA
TASK: nocows
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;


// overflow... like a moster
// you cannot even...
//   bad: reminder at total = int
//   usedL reminder at total even as Long!!!

// I think this has same order of growth as the answer, and it is recursive!!!

public class nocows {

	// if cannot optimize, always look for saves!!
	private static final String DEBUG_STRING = "99 15"; // 3 2

	private static final boolean DEBUG = true;
	public static final String PRO = "nocows";
	
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
		int K = cin.nextInt();
		
		// next try to cal the N(level), such that sum(N(level)) = N
		// 0 != N(level+1) <= 2*N(level)
		// max{N, N(level) != 0} = K
		
		// or, the simplest, try to enumarate the tree structures
		
		// this is asked 
		fw.write(treegen(1, N-1, K-1) % 9901 + "\n");
	}

	public static class Key {
		int a, b, c;
		public Key(int aa, int bb, int cc) {
			a = aa;
			b = bb;
			c = cc;
		}
		@Override
		public boolean equals(Object o) {
			if (o instanceof Key) {
				Key ok = (Key) o;
				return a == ok.a && b == ok.b && c== ok.c;
			}
			return false;
		}
		@Override
		public int hashCode() {
			return a*100000 + b* 1000 + c;
			
		}
		
	}
	private static final HashMap<Key, Long> saves = new HashMap<Key, Long>();
	private static long treegen(int pnum, int num, int level) {
		if (level == 0 && num == 0) {
			return num == 0 ? 1 : 0;
		}
		Key key = new Key(pnum, num, level);
		if (!saves.containsKey(key)) {
			long total = 0;
			for (int i = 1; i <= pnum && num-2*i >= 0; i++) {
				total += C(pnum, i) * treegen(i*2, num-i*2, level-1);
			}
			total = total % 9901;
			saves.put(new Key(pnum, num, level), total);
		}
		return saves.get(key);
	}

	private static int C(int p, int i) {
		int r = 1;
		int b = 1;
		i++;
		for (; i <= p; i++, b++) {
			r *= i;
			r /= b;
		}
		return r;
	}
	
}
