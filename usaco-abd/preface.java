/*
ID: pirripe1
LANG: JAVA
TASK: preface
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

public class preface {

	private static final String DEBUG_STRING = "3500";

	private static final boolean DEBUG = false;
	public static final String PRO = "preface";
	
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

	private static final char[] ALP = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
	private static final int[][] REP = {
		{0, 0, 0},
		{1, 0, 0},
		{2, 0, 0},
		{3, 0, 0},
		{1, 1, 0},
		{0, 1, 0},
		{1, 1, 0},
		{2, 1, 0},
		{3, 1, 0},
		{1, 0, 1}
	};
	private static void problem(Scanner cin, Writer fw) throws IOException {
		int N = cin.nextInt();
		int[] counts = new int[9];
		for (int i = 1; i <= N; i++) {
			for (int j = i, k = 0; j > 0; j /= 10, k+=2) {
				int[] rep = REP[j%10];
				for (int ii = 0; ii < 3; ii++) {
					counts[k+ii] += rep[ii];
				}
			}
		}
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > 0)
				fw.write(String.valueOf(ALP[i]) + " " + counts[i] + "\n");
		}
	}
}
