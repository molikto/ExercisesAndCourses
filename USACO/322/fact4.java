/*
ID: pirripe1
LANG: JAVA
TASK: fact4
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

public class fact4 {

	private static final String DEBUG_STRING = "143";

	private static final boolean DEBUG = false;
	public static final String PRO = "fact4";
	
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



	// we keep a table of 
	// long[value of N][difference of two set] = number of partitions * 2
	private static void problem(Scanner cin, Writer fw) throws IOException {
		int n = cin.nextInt();
		long v = 1;
		for (int i = 1; i <= n; i++) {
			v = (i * v);
			while (v % 10 == 0) v = v / 10;
			v = v % 1000000000000L;
		}
		while (v % 10 == 0) v = v / 10;
		fw.write((v % 10) + "\n");
	}
}
