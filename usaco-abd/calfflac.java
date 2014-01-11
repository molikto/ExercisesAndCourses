/*
ID: pirripe1
LANG: JAVA
TASK: calfflac
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;


public class calfflac {

	private static final String DEBUG_STRING = "";
	private static final boolean DEBUG = false;
	public static final String PRO = "calfflac";
	
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
		String org = "";
		while (cin.hasNextLine())
			org += cin.nextLine() + "\n";
		byte[] bs = org.toLowerCase().getBytes();
		int max = 0;
		int maxL = 0, maxR = 0;
		for (int i = 2; i < bs.length * 4; i+=2) {
			int l = (i - 1)/4, r = (i + 1)/4;
			if ((bs[l] < 'a' || bs[l] > 'z')
					&& (bs[r] < 'a' || bs[r] > 'z'))
				continue;
			int rl = 0, rr = 0;
			int c = l == r ? -1 : 0;
			while (true) {
				while (l >= 0 && (bs[l] < 'a' || bs[l] > 'z'))
					l--;
				if (l < 0)
					break;
				while (r <= bs.length-1 && (bs[r] < 'a' || bs[r] > 'z'))
					r++;
				if (r > bs.length-1)
					break;
				if (bs[l] != bs[r])
					break;
				c+=2;
				rl = l--;
				rr = r++;
			}
			if (c > max) {
				max = c;
				maxL = rl;
				maxR = rr;
			}
		}
		fw.write(max + "\n" + org.substring(maxL, maxR+1) + "\n");
	}
	
}
