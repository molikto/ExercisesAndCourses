/*
ID: pirripe1
LANG: JAVA
TASK: contact
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


// seems the solution used bithack...
// never mind. I have got a linear one, this is enough
public class contact {

	private static final String DEBUG_STRING = "2 4 10\n01010010010001000111101100001010011001111000010010011110010000000";
	private static final boolean DEBUG = false;
	public static final String PRO = "contact";
	
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


	public static class FPNode {
		final char c;
		final int depth;
		int count;
		final ArrayList<FPNode> children = new ArrayList<FPNode>();
		public FPNode(char c2, int d) {
			c = c2;
			depth = d;
		}
		public FPNode cth(char c) {
			for (FPNode child : children) {
				if (child.c == c)
					return child;
			}
			FPNode child = new FPNode(c, depth+1);
			children.add(child);
			return child;
		}
		public ArrayList<IntString> freqs() {
			ArrayList<IntString> res = new ArrayList<IntString>();
			freqs(res, "");
			Collections.sort(res);
			return res;
		}
		private void freqs(ArrayList<IntString> res, String pre) {
			pre = pre+String.valueOf(c);
			res.add(new IntString(pre, count));
			for (FPNode child : children) {
				child.freqs(res, pre);
			}
		}
	}
	public static class IntString implements Comparable<IntString>{
		public String str;
		public final int c;
		public IntString(String s, int i) {str = s; c = i;}
		@Override
		public int compareTo(IntString o) {
			int res = o.c - c;
			if (res != 0)
				return res;
			res = str.length() - o.str.length();
			if (res != 0)
				return res;
			res = str.compareTo(o.str);
			return res;
		}
	}
	public static class FPTree extends FPNode {
		
		int maxDepth, minDepth;
		ArrayList<FPNode> nodes = new ArrayList<FPNode>();
		public FPTree(int mind, int maxd) {
			super('S', 0);
			minDepth = mind;
			maxDepth = maxd;
		} 
		public void feed(char c) {
			nodes.add(this);
			for (int i = 0; i < nodes.size();) {
				FPNode pn = nodes.get(i);
				if (pn.depth < maxDepth) {
					FPNode an = pn.cth(c);
					nodes.set(i, an);
					an.count++;
					i++;
				} else {
					nodes.remove(i);
				}
			}
		}
		@Override
		public ArrayList<IntString> freqs() {
			ArrayList<IntString> res = super.freqs();
			for (int i = 0; i < res.size();) {
				res.get(i).str = res.get(i).str.substring(1);
				if (res.get(i).str.length() < minDepth) {
					res.remove(i);
				} else {
					i++;
				}
			}
			return res;
		}
		
	}
	private static void problem(Scanner cin, Writer fw) throws IOException {
		int A = cin.nextInt();
		int B = cin.nextInt();
		int N = cin.nextInt();
		cin.nextLine();
		StringBuilder sb = new StringBuilder();
		while (cin.hasNext()) {
			sb.append(cin.nextLine());
		}
		FPTree fpt = new FPTree(A, B);
		for (char c : sb.toString().toCharArray()) {
			fpt.feed(c);
		}
		ArrayList<IntString> res = fpt.freqs();
		int index = 0;
		for (; N > 0 && index < res.size(); N--) {
			int f = res.get(index).c;
			fw.write(f+"\n");
			int size = 0;
			for (;index < res.size() && res.get(index).c == f; index++) {
				fw.write(res.get(index).str);
				size++;
				if (size % 6 == 0 || index == res.size()-1 || res.get(index+1).c != f) {
					fw.write("\n");
				} else {
					fw.write(" ");
				}
			}
		}
	}
	
}
