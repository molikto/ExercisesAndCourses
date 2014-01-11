/*
ID: pirripe1
LANG: JAVA
TASK: concom
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeSet;


// I do not understand....

// when I repeat controll2 3times, it worked!!!

public class concom {

	private static final String DEBUG_STRING = "3 1 2 80 2 3 80 3 1 20";
	private static final boolean DEBUG = false;
	public static final String PRO = "concom";
	
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


	public static class Share {
		int amount;
		Comp comp;
		public Share(int a, Comp c) {
			amount = a;
			comp = c;
		}
	}
	public static class Comp implements Comparable<Comp> {
		int key;
		ArrayList<Share> shares = new ArrayList<Share>();
		TreeSet<Comp> control = new TreeSet<Comp>();
		public Comp(int a) {
			key = a;
		}
		@Override
		public boolean equals(Object o) {
			if (o instanceof Comp) {
				return key == ((Comp) o).key;
			}
			return false;
		}
		@Override
		public int hashCode() {
			return key;
		}
		@Override 
		public int compareTo(Comp o) {
			return key - o.key;
		}
		public void calControl0() {
			control.add(this);
			for (Share s : shares) {
				if (s.amount > 50) {
					control.add(s.comp);
				}
			}
		}
		
		public void calControl2() {
			TreeSet<Comp> res = new TreeSet<Comp>();
			for (Comp c : control) {
				res.addAll(c.control);
			}
			control.addAll(res);
		}
		public boolean calControl() {
			int ol = control.size();
			HashMap<Comp, Integer> ts = new HashMap<Comp, Integer>();
			for (Comp c : control) {
				for (Share s : c.shares) {
					if (!control.contains(s.comp)) {
						if (ts.get(s.comp) == null) {
							ts.put(s.comp, 0);
						}
						ts.put(s.comp,  ts.get(s.comp) + s.amount);
					}
				}
			}
			for (Entry<Comp, Integer> cv : ts.entrySet()) {
				if (cv.getValue() > 50) {
					control.add(cv.getKey());
				}
			}
			return control.size() - ol > 0;
		}
	}
	private static void problem(Scanner cin, Writer fw) throws IOException {
		int N = cin.nextInt();
		int MAX = 0;
		Comp[] comps = new Comp[200];
		for (int i = 0; i < N; i++) {
			int a = cin.nextInt();
			if (comps[a] == null) {
				comps[a] = new Comp(a);
			}
			int b = cin.nextInt();
			if (comps[b] == null) {
				comps[b] = new Comp(b);
			}
			comps[a].shares.add(new Share(cin.nextInt(), comps[b]));
			MAX = Math.max(MAX, a);
			MAX = Math.max(MAX, b);
		}
		MAX++;
		for (int i = 0; i < MAX; i++) {
			Comp c = comps[i];
			if (c != null) {
				c.calControl0();
			}
		}
		boolean changed = true;
		while (changed) {
			changed = false;
			for (int i = 0; i < MAX; i++) {
				Comp c = comps[i];
				if (c != null) {
					c.calControl2();
					c.calControl2();
					c.calControl2();
					changed = changed || c.calControl();
				}
			}
		}
		for (int i = 0; i < MAX; i++) {
			Comp c = comps[i];
			if (c != null) {
				for (Comp d : c.control) {
					if (c.key != d.key)
						fw.write(c.key + " " + d.key + "\n");
				}
			}
		}
	}
	
}
