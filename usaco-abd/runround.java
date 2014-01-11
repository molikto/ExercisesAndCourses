/*
ID: pirripe1
LANG: JAVA
TASK: runround
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

public class runround {

	private static final String DEBUG_STRING = "99";

	private static final boolean DEBUG = false;
	public static final String PRO = "runround";
	
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
		boolean[] mask = new boolean[10];
		mask[0] = true;
		for (int i = N+1; true; i++) {
			boolean good = true;
			int k = 0;
			for (int j = i; j > 0; j /= 10, k++) {
				int m = j % 10;
				if (mask[m]) {
					good = false;
					break;
				} else {
					mask[m] = true;
				}
			}
			if (good) {
				int[] f = new int[k];
				int[] t = new int[k];
				for (int j = i, kk = 0; j > 0; j /= 10, kk++) {
					f[k-1-kk] = j % 10;
				}
				boolean[] visited = new boolean[k];
				for (int j = 0, b = 0; j < k; j++) {
					b = (b + f[b]) % k;
					if (visited[b]) {
						good = false;
						break;
					}
					visited[b] = true;
				}
				if (good) {
					fw.write(i+"\n");
					return;
				}
			}
			for (int j = 1; j < 10; j++)
				mask[j] = false;
		}
	}
}
