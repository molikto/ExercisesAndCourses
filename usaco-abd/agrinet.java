/*
ID: pirripe1
LANG: JAVA
TASK: agrinet
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;

import org.pirriperdos.algorithms.UndirectedGraph;

public class agrinet {

	private static final String DEBUG_STRING = "4 0 4 9 21 4 0 8 17 9 8 0 16 21 17 16 0";

	private static final boolean DEBUG = false;
	public static final String PRO = "agrinet";
	
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
		UndirectedGraph graph = new UndirectedGraph(N);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				graph.link(i, j, cin.nextInt());
			}
		}
		fw.write(graph.minSpanTree().weight()+"\n");
	}
	
}
