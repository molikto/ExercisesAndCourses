/*
ID: pirripe1
LANG: JAVA
TASK: inflate
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;

import org.pirriperdos.algorithms.InfKanpsnack;
import org.pirriperdos.algorithms.Kanpsnack.Item;

public class inflate {

	private static final String DEBUG_STRING = "300 4 100 60 250 120 120 100 35 20";

	private static final boolean DEBUG = false;
	public static final String PRO = "inflate";
	
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
		int S = cin.nextInt();
		int C = cin.nextInt();
		InfKanpsnack ks = new InfKanpsnack(S);
		for (int i = 0; i < C; i++) {
			int p = cin.nextInt();
			int m = cin.nextInt();
			ks.items.add(new Item(m, p));
		}
		fw.write(ks.cal()+"\n");
	}
	
}
