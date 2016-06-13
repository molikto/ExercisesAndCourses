/*
ID: pirripe1
LANG: JAVA
TASK: barn1
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class barn1 {

	private static final String DEBUG_STRING = "4 50 18 3 4 6 8 14 15 16 17 21 25 26 27 30 31 40 41 42 43";

	private static final boolean DEBUG = false;
	public static final String PRO = "barn1";
	
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

	public static class Seller implements Comparable<Seller> {
		public Seller(int p, int a) {
			price = p;
			amount = a;
		}
		int price;
		int amount;
		
		@Override
		public int compareTo(Seller o) {
			return price - o.price;
		}
	}
	private static void problem(Scanner cin, Writer fw) throws IOException {
		int maxB = cin.nextInt();
		int occS = cin.nextInt();
		ArrayList<Integer> occs = new ArrayList<Integer>();
		for (int i = 0; i < occS; i++) {
			occs.add(cin.nextInt());
		}
		Collections.sort(occs);
		int adj = -1;
		ArrayList<Integer> cemp = new ArrayList<Integer>();
		for (Integer i : occs) {
			if (i - 1 != adj) {
				cemp.add(i - adj - 1);
			}
			adj = i;
		}
		cemp.remove(0);
		Collections.sort(cemp);
		int tot = occS;
		while (cemp.size() > maxB - 1) {
			tot += cemp.get(0);
			cemp.remove(0);
		}
		fw.write(tot+"\n");
	}
	
}
