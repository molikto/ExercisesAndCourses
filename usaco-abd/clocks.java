/*
ID: pirripe1
LANG: JAVA
TASK: clocks
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


// first used HashMap and ArrayList, all slow
// and I am forced to sue my own SymbolTable
// failed
// so I must use meet in the middle
// and found TreeSet useful
// but... a huge bug is... retain!!!
// arraylist is painful.
// and hashmap also
// and

// turns out the simplest is ... SHIT!!!!!!
// you can precalculate
// and you can solve equation...
// I am sad....




public class clocks {

	private static final String DEBUG_STRING = "12 3 3    3 6 6    12 3 6 ";

	private static final boolean DEBUG = false;
	public static final String PRO = "clocks";
	
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

	public static final int[] GOAL = {12, 12, 12, 12, 12, 12, 12, 12, 12};
	
	public static final class Move implements Comparable<Move>{
		public Move(int[] is, int i, int d) {
			pos = is;
			name = i;
			this.d = d;
		}
		int[] pos;
		int name;
		int d;
		@Override
		public int compareTo(Move o) {
			return name - o.name;
		}
		
	}
	private static final Move[] MOVES_L = new Move[9], MOVES_R = new Move[9];
	private static final int[][] MOVES_POS = {
		 {0, 1, 3, 4},
		 {0, 1, 2},
		 {1, 2, 4, 5},
		 {0, 3, 6},
		 {1, 3, 4, 5, 7},
		 {2, 5, 8},
		 {3, 4, 6, 7},
		 {6, 7, 8},
		 {4, 5, 7, 8}
	};
	static {
		for (int i = 0; i < 9; i++) {
			MOVES_L[i] = new Move(MOVES_POS[i], i+1, 3);
			MOVES_R[i] = new Move(MOVES_POS[9-1-i], 9-i, -3);
		}
	}
	/*
	public static class SymbolTable {
		public static SymbolTable root = new SymbolTable();
		SymbolTable[] childs = new SymbolTable[4];
		
		public SymbolTable() {
		}
		
		public static boolean addOrNot(int[] times) {
			SymbolTable st = root;
			boolean has = true;
			for (int t : times) {
				if (st.childs[t/3-1] == null) {
					has = false;
					st.childs[t/3-1] = new SymbolTable();
					st = st.childs[t/3-1];
				}
			}
			return has;
		}
	}
	*/
	public static final class Clock implements Comparable<Clock>{
		int[] times;
		Move[] moves;
		int moveIndex;
		int count = 0;
		Clock previous;
		public Clock(int[] time, Move[] moves) {
			times = time;
			moveIndex = 0;
			this.moves = moves;
		}
		public Clock(Clock clock, int i) {
			times = Arrays.copyOf(clock.times, 9);
			previous = clock;
			moves = previous.moves;
			count = clock.moveIndex == i ? clock.count +1: 1;
			moveIndex = i;
			for (int p : moves[moveIndex].pos) {
				times[p] += moves[i].d;
				if (times[p] > 12)
					times[p] -= 12;
				else if (times[p] <= 0) {
					times[p] += 12;
				}
			}
		}

		public void getChilds(Collection<Clock> newResults) {
			for (int i = (count > 3 ? moveIndex+1: moveIndex); i < moves.length; i++) {
				newResults.add(new Clock(this, i));
			}
		}

		@Override
		public int compareTo(Clock o) {
			for (int i = 0; i < 9; i++) {
				int c = times[i] - o.times[i];
				if (c != 0)
					return c;
			}
			return 0;
		}


		@Override
		public boolean equals(Object c) {
			return Arrays.equals(times, ((Clock)c).times);
		}
	}
	
	public static final class Grower {
		TreeSet<Clock> lastResults =  new TreeSet<Clock>();
		public Grower(Clock start) {
			lastResults.add(start);
		}
		public void grow() {
			TreeSet<Clock> newResults = new TreeSet<Clock>();
			for (Clock c : lastResults) {
				c.getChilds(newResults);
			}
			lastResults = newResults;
			
		}
	}
	public static final class GrowResult implements Comparable<GrowResult> {
		String str = "";
		Clock left, right;
		public GrowResult(Clock l, Clock r) {
			left = l;
			right = r;
			ArrayList<Move> moves = new ArrayList<Move>();
			for (Clock temp = l; temp.previous != null; temp = temp.previous) {
				moves.add(0, temp.moves[temp.moveIndex]);
			}
			for (Clock temp = r; temp != null && temp.previous != null; temp = temp.previous) {
				moves.add(temp.moves[temp.moveIndex]);
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < moves.size(); i++) {
				sb.append(moves.get(i).name);
				sb.append(i == moves.size()-1 ? "" : " ");
			}
			str = sb.toString();
		}
		@Override
		public int compareTo(GrowResult o) {
			return str.compareTo(o.str);
		}
	}
	
	
	private static final void problem(Scanner cin, Writer fw) throws IOException {
		int[] times = new int[9];
		for (int i = 0; i < 9; i++) {
			times[i] = cin.nextInt();
		}
		int[] results = new int[9];
		int[] appln = new int[9];
		for (int i = 0; i < (int) Math.pow(4, 9); i++) {
			for (int j = 0; j < 9; j++) {
				results[j] = times[j];
			}
			for (int j = 0, ii = i; j < 9; j++, ii /= 4) {
				appln[j] = ii % 4;
				for (int t = 0; t < appln[j]; t++) {
					for (int c : MOVES_POS[j]) {
						results[c] %= 12;
						results[c] += 3;
					}
				}
			}
			if (Arrays.equals(results, GOAL)) {
				ArrayList<Integer> ints = new ArrayList<Integer>();
				for (int j = 0; j < 9; j++) {
					for (int t = 0; t < appln[j]; t++) {
						ints.add(j+1);
					}
				}
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < ints.size(); j++) {
					sb.append(ints.get(j));
					sb.append(j == ints.size()-1 ? "\n" : " ");
				}
				fw.write(sb.toString());
				return;
			}
			
		}
		
	/*	Grower lg = new Grower(new Clock(times, MOVES_L));
		Grower rg = new Grower(new Clock(GOAL, MOVES_R));
		
		for (int k = 0;true;k++) {
			lg.grow();
			if (check(fw, lg.lastResults, rg.lastResults))
				break;
			rg.grow();
			if (check(fw, lg.lastResults, rg.lastResults))
				break;
		}*/
	}
	private static final boolean check(Writer fw, Collection<Clock> lr, Collection<Clock> rr)
			throws IOException {
		if (!Collections.disjoint(lr, rr)) {
			lr.retainAll(rr);
			ArrayList<GrowResult> al = new ArrayList<GrowResult>();
			for (Clock c : lr) {
				al.add(new GrowResult(c, null));
			}
			GrowResult goal = Collections.min(al);
			al.clear();
			ArrayList<Clock> sc = new ArrayList<Clock>();
			sc.add(goal.left);
			rr.retainAll(sc);
			for (Clock t : rr) {
				al.add(new GrowResult(goal.left, t));
			}
			fw.write(Collections.min(al).str + "\n");
			return true;
		}
		return false;
	}
}
