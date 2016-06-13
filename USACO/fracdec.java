/*
ID: pirripe1
LANG: JAVA
TASK: fracdec
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

public class fracdec {

	private static final String DEBUG_STRING = "59 330";
	private static final boolean DEBUG = false;
	public static final String PRO = "fracdec";
	
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
				FileReader fr = new FileReader(PRO+".in");
				cin = new Scanner(fr);
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
		int D = cin.nextInt();
		int[] mask = new int[D];
		Arrays.fill(mask, 0);
		StringBuilder sb = new StringBuilder();
		sb.append(N / D);
		N %= D;
		sb.append(".");
otter:	while (true) {
			mask[N] = sb.length();
			N *= 10;
			sb.append(N/D);
			N %= D;
			if (N == 0) {
				break;
			} else {
				if (mask[N] != 0) {
					sb.insert(mask[N], "(");
					sb.append(")");
					break otter;
				}
			}
		}
		String str = sb.toString();
		for (int i = 0; i < str.length(); i += 76) {
			int end = Math.min(str.length(), i+76);
			fw.write(str.substring(i, end)+"\n");
		}
	}
	
}
