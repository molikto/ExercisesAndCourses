/*
ID: pirripe1
LANG: JAVA
TASK: sort3
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Scanner;

// at least... I write clean code...

public class sort3 {

	private static final String DEBUG_STRING = "9 2 2 1 3 3 3 2 3 1";

	private static final boolean DEBUG = false;
	public static final String PRO = "sort3";
	
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
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = cin.nextInt();
		}
		int[][] diffCount = new int[3][];
		for (int i = 0; i < 3; i++) {
			diffCount[i] = new int[3];
		}
		int[] sorted = Arrays.copyOf(arr, arr.length);
		Arrays.sort(sorted);
		for (int i = 0; i < N; i++) {
			if (arr[i] != sorted[i]) {
				diffCount[arr[i]-1][sorted[i]-1]++;
			}
		}
		int exchange = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int min = Math.min(diffCount[i][j], diffCount[j][i]);
				diffCount[i][j] -= min;
				diffCount[j][i] -= min;
				exchange += min;
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					int min = Math.min(diffCount[i][j], diffCount[j][k]);
					min = Math.min(min, diffCount[k][i]);
					diffCount[i][j] -= min;
					diffCount[j][k] -= min;
					diffCount[k][i] -= min;
					exchange += 2* min;
				}
			}
		}
		fw.write(exchange + "\n");
	}
	
}
