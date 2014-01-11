/*
ID: pirripe1
LANG: JAVA
TASK: namenum
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;


public class namenum {

	
	/*
	 * 
	 * Or, we can examine each word in the dictionary to see if it ma
	 * ps to the digits of the number in question. This has the the advan
	 * tage of a shorter program that probably will work right first time.


	 */

	private static final String DEBUG_STRING = "4734";

	private static final boolean DEBUG = false;
	public static final String PRO = "namenum";
	
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

	public static class NameIterator implements Iterator<String> {
		private byte[][] table;
		int i = 0;
		private int len;

		public NameIterator(NameTable nameTable) {
			table = nameTable.table;
			len = table.length;
			
		}

		@Override
		public boolean hasNext() {
			return i < Math.pow(3, len);
		}

		@Override
		public String next() {
			byte[] bs = new byte[len];
			int n = i;
			for(int d = 0; d < len; d++) {
				bs[len-1-d] = table[len-1-d][n%3];
				n /= 3;
			}
			i++;
			return new String(bs);
		}

		@Override
		public void remove() {
		}
	}
	public static class NameTable implements Iterable<String>{

		byte[][] table;
		public static final String[] MAP = {
			"ABC", "DEF", "GHI", "JKL", "MNO", "PRS", "TUV", "WXY"
		};
		public NameTable(byte[] num) {
			table = new byte[num.length][];
			for (int i = 0; i < table.length; i++) {
				table[i] = MAP[num[i]-'2'].getBytes();
			}
		}
		@Override
		public Iterator<String> iterator() {
			return new NameIterator(this);
		}

	}
	private static void problem(Scanner cin, Writer fw) throws IOException {
		byte[] num = cin.nextLine().getBytes();
		FileReader fdic = new FileReader("dict.txt");
		BufferedReader bf= new BufferedReader(fdic);
		ArrayList<String> ar = new ArrayList<String>();
		for (String str = bf.readLine(); str != null; str = bf.readLine()) {
			ar.add(str);
		}
		bf.close();
		fdic.close();
		NameTable nt = new NameTable(num);
		boolean find = false;
		for (String name : nt) {
			if (Collections.binarySearch(ar, name) > 0) {
				fw.write(name+"\n");
				find = true;
			}
		}
		if (!find) {
			fw.write("NONE\n");
		}
	}
	
}
