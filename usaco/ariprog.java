/*
ID: pirripe1
LANG: JAVA
TASK: ariprog
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


// I know it can be improved, but the silliest thing I do is in the search
// linear search is o.k. if <100, but what the hack with > 100?

public class ariprog {

	private static final String DEBUG_STRING = "21  200";

	private static final boolean DEBUG = false;
	public static final String PRO = "ariprog";
	
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
		int plen = cin.nextInt();
		int max = cin.nextInt();
		boolean has = false;
		int[] nums = new int[(max+2)*(max+1)/2];
		for (int p = 0, index = 0; p <= max; p++) {
			for (int q = 0; q <= p; q++) {
				nums[index++] = p*p + q*q;
			}
		}
		Arrays.sort(nums);
		for (int d = 1; d < nums[nums.length-1]/(plen-1) + 1; d++) {
			ArrayList<Integer> results = new ArrayList<Integer>();
			for (int sindex = nums.length-1; sindex >= 0; sindex--) {
				if (sindex > 0 && nums[sindex-1] == nums[sindex])
					continue;
				int ind = sindex;
				if (nums[sindex] - (plen-1) * d < 0)
					break;
				for (int p = 1; p < plen; p++) {
					ind = search(nums, ind, nums[sindex] - p*d);
					if (ind < 0)
						break;
				}
				if (ind >= 0) {
					has = true;
					results.add(ind);
				}
			}
			for (int i = results.size()-1; i >= 0; i--) {
				fw.write(nums[results.get(i)] + " "+ d +"\n");
			}
		}
		if (!has)
			fw.write("NONE\n");
	}

	private static int search(int[] nums, int ind, int k) {
		if (nums[ind] - k > 100)
			return Arrays.binarySearch(nums, 0, ind, k);
		if (ind < 0)
			return -1;
		for (int i = ind; i >= 0; i--) {
			if (nums[i] == k)
				return i;
			else if (nums[i] < k)
				return -i;
		}
		return -1;
	}
	
}
