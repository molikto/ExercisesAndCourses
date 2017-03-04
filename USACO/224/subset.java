/*
ID: pirripe1
LANG: JAVA
TASK: subset
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

public class subset {

	private static final String DEBUG_STRING = "39";

	private static final boolean DEBUG = false;
	public static final String PRO = "subset";
	
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
		int N = cin.nextInt();
		long[][] results = new long[N+1][];
		results[0] = new long[] {1};
		for (int i = 1; i <= N; i++) {
			results[i] = new long[i*(i+1)+1];
			int cn = i*(i+1)/2;
			int cp = i*(i-1)/2;
			for (int j = -cp; j <= cp; j++) {
				results[i][cn+j+i] += results[i-1][cp+j];
				results[i][cn+j-i] += results[i-1][cp+j];
			}
		}
		fw.write(results[N][N*(N+1)/2]/2+ "\n");
	}
}
