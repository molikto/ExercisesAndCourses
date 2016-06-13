/*
ID: pirripe1
LANG: JAVA
TASK: crypt1
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


public class crypt1 {

	
	// ugly!!!
	private static final String DEBUG_STRING = "5 2 3 4 6 8";

	private static final boolean DEBUG = false;
	public static final String PRO = "crypt1";
	
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
		int numCount = cin.nextInt();
		int totalPos = numCount * numCount * numCount * numCount * numCount;
		int[] nums = new int[numCount];
		for (int i = 0; i < numCount; i++) {
			nums[i] = cin.nextInt();
		}
		int[] bs = new int[15];
		int totalCount = 0;
		for (int i = 0; i < totalPos; i++) {
			for (int j = 0, t = i; j < 5; j++) {
				bs[j] = nums[t % nums.length];
				t /= nums.length;
			}
			int n1 = bs[0] * 100 + bs[1] * 10 + bs[2];
			int n2 = bs[3] * 10 + bs[4];
			int p1 = n1 * bs[4];
			int p2 = n1 * bs[3];
			int m = n1 * n2;
			if (p1 < 100 || p2 < 100 || m < 1000 || n1 < 100 || n2 < 10)
				continue;
			bs[5] = p1 / 100;
			bs[6] = (p1 / 10) % 10;
			bs[7] = p1 % 10;
			bs[8] = p2 / 100;
			bs[9] = (p2 / 10) % 10;
			bs[10] = p2 % 10;
			bs[11] = m / 1000;
			bs[12] = (m / 100) % 10;
			bs[13] = (m / 10) % 10;
			bs[14] = m % 10;
			boolean contain = false;
			for (int j = 5; j < 15; j++) {
				contain = false;
				for (int k = 0; k < nums.length; k++) {
					if (nums[k] == bs[j]) {
						contain = true;
						break;
					}
				}
				if (!contain)
					break;
			}
			if (contain)
				totalCount++;
		}
		fw.write(totalCount + "\n");
	}
	
}
