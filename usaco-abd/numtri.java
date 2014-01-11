/*
ID: pirripe1
LANG: JAVA
TASK: numtri
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// you can use down-top!!!

public class numtri {

	private static final String DEBUG_STRING = "5 7 3 8 8 1 0 2 7 4 4 4 5 2 6 5";

	private static final boolean DEBUG = false;
	public static final String PRO = "numtri";
	
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
		int dim = cin.nextInt();
		int[][] nums = new int[dim][];
		for (int i = 0; i < dim; i++) {
			nums[i] = new int[i+1];
			for (int j = 0; j <= i; j++) {
				nums[i][j] = cin.nextInt();
			}
		}
		for (int i = 1; i < dim; i++) {
			nums[i][0] +=nums[i-1][0];
			for (int j = 1; j < i; j++)
				nums[i][j] += Math.max(nums[i-1][j-1], nums[i-1][j]);
			nums[i][i] +=nums[i-1][i-1];
		}
		Arrays.sort(nums[dim-1]);
		fw.write(nums[dim-1][dim-1]+"\n");
	}
	
}
