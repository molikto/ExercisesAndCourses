/*
ID: pirripe1
LANG: JAVA
TASK: ttwo
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
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;


/*
 * you can save the history
 * you can build a mask table
 * you can algule like this 
 * In fact, we don't even need to keep track of 
 * which configurations we've seen. If they're going
 *  to meet, they're going to meet in fewer than 160,000 steps
 * ...
 * I am to week to make good arguments
 * I just like "restate the problem"
 * but not solving it!!!
 * I really need a solver that can reason - bullshit...
 * 
 * so what algorithm is ??? what problem solving is???
 * 
 * to solve a problem, we do the REASONING part
 * and let computer do the hard work part
 * 
 * so, never blindly use your computer!!!!
 * 
 */
public class ttwo {

	
	
	private static final String DEBUG_STRING = "*...*.....\n"+
"......*...\n"+
"...*...*..\n"+
"..........\n"+
"...*.F....\n"+
"*.....*...\n"+
"...*......\n"+
"..C......*\n"+
"...*.*....\n"+
".*.*......";

	private static final boolean DEBUG = false;
	public static final String PRO = "ttwo";
	
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

	public static class Point implements Comparable<Point> {
		final int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public int compareTo(Point o) {
			return (x-o.x) * 100 + y - o.y;
		}
		@Override
		public boolean equals(Object o) {
			if (o instanceof Point) {
				Point op = (Point) o;
				return x == op.x && y == op.y;
			}
			return false;
		}
		public Point add(Point mf) {
			return new Point(x+mf.x, y+mf.y);
		}
		public Point sub(Point mf) {
			return new Point(x-mf.x, y-mf.y);
		}
	}
	public static Point[] FACES = new Point[]{
		new Point(-1, 0), 
		new Point(0, 1),
		new Point(1, 0),
		new Point(0, -1)};
	public static class State implements Comparable<State> {
		final Point f;
		final Point c;
		final Point mf;
		final Point mc;
		public State(Point ff, Point cc) {
			f = ff;
			c = cc;
			mf = FACES[0];
			mc = FACES[0];
		}
		public State(Point[] rf, Point[] rc) {
			f = rf[0];
			c = rc[0];
			mf = rf[1];
			mc = rc[1];
		}
		@Override
		public int compareTo(State o) {
			int r = f.compareTo(o.f);
			if (r != 0) return r;
			r = c.compareTo(o.c);
			if (r != 0) return r;
			r = mf.compareTo(o.mf);
			if (r != 0) return r;
			r = mc.compareTo(o.mc);
			if (r != 0) return r;
			return 0;
		}
		@Override
		public boolean equals(Object o) {
			if (o instanceof State) {
				State op = (State) o;
				return f == op.f && c == op.c && mf == op.mf && mc == op.mc;
			}
			return false;
		}
	}
	public static class Game {
		public Game(char[][] board2, Point f, Point c) {
			this.board = board2;
			cs = new State(f, c);
		}
		int step = 0;
		State cs;
		char[][] board;
		TreeSet<State> states = new TreeSet<State>();
		public int result() {
			while (true) {
				if (cs.f.equals(cs.c)) {
					return step;
				}
				if (states.contains(cs)) {
					return 0;
				}
				states.add(cs);
				cs = new State(nextOp(cs.f, cs.mf), nextOp(cs.c, cs.mc));
				step++;
			}
		}
		
		public Point turn(Point m) {
			if (m == FACES[0])
				return FACES[1];
			else if (m == FACES[1])
				return FACES[2];
			else if (m == FACES[2])
				return FACES[3];
			else
				return FACES[0];
		}
		private Point[] nextOp(Point f, Point mf) {
			Point os = f.add(mf);
			if (hit(os)) {
				return new Point[]{f, turn(mf)};
			}
			return new Point[]{os, mf};
		}

		private boolean hit(Point p) {
			return p.x<0 || p.x>=10 || p.y<0 || p.y>=10 || board[p.x][p.y] != '.';
		}
	}
	private static void problem(Scanner cin, Writer fw) throws IOException {
		char[][] board = new char[10][];
		Point f = null, c = null;
		for (int i = 0; i < 10; i++) {
			board[i] = cin.nextLine().toCharArray();
			for (int j = 0; j < 10; j++) {
				if (board[i][j] == 'F') {
					board[i][j] = '.';
					f = new Point(i, j);
				} else if (board[i][j] == 'C') {
					board[i][j] = '.';
					c = new Point(i, j);
				}
			}
		}
		fw.write(new Game(board, f, c).result() + "\n");
	}
}
