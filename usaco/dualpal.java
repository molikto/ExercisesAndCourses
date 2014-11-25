/*
ID: pirripe1
LANG: JAVA
TASK: dualpal
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;


public class dualpal {


	private static final String DEBUG_STRING = "3 25";

	private static final boolean DEBUG = false;
	public static final String PRO = "dualpal";
	
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
		int S = cin.nextInt();
		int n = 0;
		for (int i = S+1; n < N; i++) {
			int bc = 0;
			for (int base = 2; base <= 10; base++) {

				byte[] s = Integer.toString(i, base).getBytes();
				boolean is = true;
				for (int j = 0; j < s.length/2; j++) {
					if (s[j] != s[s.length-1-j]) {
						is = false;
						break;
					}
				}
				if (is)
					bc++;
				if (bc >= 2)
					break;
			}
			if (bc >= 2) {
				n++;
				fw.write(Integer.toString(i)+"\n");
			}
		}
	}
	
}
