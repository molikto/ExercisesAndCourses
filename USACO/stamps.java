/*
ID: pirripe1
LANG: JAVA
TASK: stamps
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Scanner;


// shit... I am using linear search... but this dp only should check for stamps!!!

// and what the hack is 3 sec?? I am using only 0.3 sec!!!
public class stamps {

	public static class IntList {

		
		private int length;
		private int[] arr;
		public IntList(int n) {
			arr = new int[n+3];
			length = 0;
		}
		
		public int get(int i) {
			return arr[i];
		}
		
		private void ensureLength() {
			if (length > arr.length) {
				arr = Arrays.copyOf(arr, 2*(length+1));
			}
		}
		
		public void append(int v) {
			length++;
			ensureLength();
			arr[length-1] = v;
		}

		public void set(int index, int j) {
			length = Math.max(length, index+1);
			ensureLength();
			arr[index] = j;
		}
		public int length() {
			return length;
		}

		public void sort() {
			Arrays.sort(arr, 0, length);
		}

		public void setLength(int n) {
			if (length > n) {
				length = n;
			}
		}

		public void merge(IntList o) {
			append(Integer.MAX_VALUE);
			o.append(Integer.MAX_VALUE);
			length--;
			o.length--;
			int[] a0 = arr;
			int l0 = length;
			int[] a1 = o.arr;
			int l1 = o.length;
			length = l0+l1;
			arr = new int[length+2];
			for (int i = 0, i0 = 0, i1 = 0; i < l0+l1; i++) {
				if (a0[i0] > a1[i1])
					arr[i] = a1[i1++];
				else
					arr[i] = a0[i0++];
			}
		}
		
		public int index(int l) {
			return Arrays.binarySearch(arr, 0, length, l);
		}

		public void sublist(int s, int e) {
			length = e-s;
			arr = Arrays.copyOfRange(arr, s, e);
		}
	}
	private static final String DEBUG_STRING = "200 14   1 2 4 15 9 31 63 2100 3500 127 255 511 1000 1999";
	private static final boolean DEBUG = false;
	public static final String PRO = "stamps";
	
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
		int K = cin.nextInt();
		int N = cin.nextInt();
		IntList nums = new IntList(0);
		nums.set(0, 0);
		int[] stamps = new int[N];
		for (int i = 0; i < N; i++) {
			stamps[i] = cin.nextInt();
		}
		for (int num = 1;; num++) {
			int min = Integer.MAX_VALUE;
			for (int stamp : stamps) {
				if (num - stamp >= 0) {
					min = Math.min(min, 1+nums.get(num-stamp));
				}
			}
			if (min > K) {
				fw.write((num-1)+"\n");
				return;
			}
			nums.set(num, min);
		}
	}
	
}
