/*
ID: pirripe1
LANG: JAVA
TASK: prefix
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// recurring is bad engough, but simple in code
// using array as hash table you stupid!!
// shit!!! This is your submission #16 for this problem.
// just unable to optimize a recursive version!!!

public class prefix {

	private static final String DEBUG_STRING = "A AA AAA AAAA AAAAA AAAAAA AAAAAA AAAAAAA AAAAAAAAB AAB AB ABB BAB BBA\n.\nAAAAAAAAAAABAABAAAAAAAAAABAABAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAABAAABAAAABAAAAAAAAABAAAAAAAAAAABAAAAAAAAAAAAABAAAAABAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAABAAAAABAAAAAAAAAAAAAAAAAAAAAABAAAA";

	private static final boolean DEBUG = false;
	public static final String PRO = "prefix";
	
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
		StringBuilder sb = new StringBuilder();
		for (String l = cin.nextLine(); !l.equals("."); l = cin.nextLine()) {
			sb.append(l + " ");
		}
		ArrayList<String> ps = new ArrayList<String>();
		ps.addAll(Arrays.asList(sb.toString().split(" ")));
		for (int i = 0; i < ps.size();) {
			String p = ps.get(i);
			ps.remove(i);
			if (solve(p, 0, ps) != p.length()) {
				ps.add(i, p);
				i++;
			}
		}
		sb = new StringBuilder();
		for (String l = cin.nextLine(); l != null;
				l = cin.hasNext() ? cin.nextLine() : null) {
			sb.append(l);
		}
		fw.write(solve(sb.toString(), 0, ps)+"\n");
	}
	private static final int UNTOUCH = 0;
	private static final int GOOD = 1;
	private static final int BAD = 2;
	private static int solve(String str, int i, List<String> ps) {
		int[] td = new int[str.length() + 100];
		return solve0(str, ps, td);
	}
	private static int solve0(String str, List<String> ps, int[] td) {
		int i = 0;
		int max = 0;
		while (true) {
			int front = i;
			for (String p : ps) {
				int c = i+p.length()-1;
				if (str.startsWith(p, i) && td[c] == UNTOUCH) {
					td[c] = GOOD;
					c++;
					max = Math.max(max, c);
					front = Math.max(c, front);
				}
			}
			if (front == i) {
				td[i] = BAD;
				for (; i >=0 && td[i] != GOOD; i--);
				if (i == -1) {
					return max;
				}
			} else {
				i = front;
				if (max == str.length()) {
					return max;
				}
			}
		}
	}

}
