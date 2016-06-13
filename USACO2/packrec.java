/*
ID: pirripe1
LANG: JAVA
TASK: packrec
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

// the first hard problem I meet
// http://cgi.csc.liv.ac.uk/~epa/surveyhtml.html

// frist I tried linearly search whole space with recurion
// bad enough!!!!
// next I tried using anchor, good
// but the solution used exactly numarate how many ways to position them...
// what the hack!!!????

public class packrec {

	private static final String DEBUG_STRING = "4 3    4 4   6 3   5 5";

	private static final boolean DEBUG = false;
	public static final String PRO = "packrec";
	
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
	
	// place unrealted w, h, rotate
	// place related place, unplace, intersect, x, y
	public static class Rect {
		public Rect(int ww, int hh, int i) {
			w = ww;
			h = hh;
			index = i;
		}
		public int index;
		private int w;
		private int h;
		private int x;
		private int y;
		public void rotate() {
			int t = w;
			w = h;
			h = t;
		}
		public boolean intersect(Rect rect) {
			return !(x >= rect.x + rect.w || rect.x >= x + w)
				&& !(y >= rect.y + rect.h || rect.y >= y + h);
		}
		public void reinit() {
			x = 0;
			y = 0;
		}
		public void place(int x2, int y2) {
			x = x2;
			y = y2;
		}
		
	}
	public static class Point implements Comparable<Point>{
		int x;
		int y;
		public Point(int a, int b) {
			if (a < b) {
				x = a;
				y = b;
			} else {
				y = a;
				x = b;
			}
		}
		public boolean equals(Object po) {
			Point p = (Point) po;
			return x == p.x && y == p.y;
		}
		public int area() {
			return x*y;
		}
		@Override
		public int compareTo(Point o) {
			if (x != o.x) {
				return x - o.x;
			} else {
				return y - o.y;
			}
		}
	}
	/*
	public static class RectArranger {
		private ArrayList<Point> results = new ArrayList<Point>();
		public RectArranger(Rect[] rects2) {
			rects = rects2;
			results.add(new Point(10000, 100000));
		}
		private Rect[] rects;
		private ArrayList<Rect> currentPlaced = new ArrayList<Rect>();
		public ArrayList<Point> arrange() {
			arrange1();
			layout("");
			return results;
		}
		public void arrange1() {
			for (int i = 0; i < rects.length; i++) {
				if (rects[i].placed == false) {
					currentPlaced.add(rects[i]);
					arrange1();
					rects[i].rotate();
					arrange1();
					currentPlaced.remove(rects[i]);
				}
			}
		}
		private void layout(String ttt) {
			if (ttt.length() >= 4)
				return;
		//	System.out.println(ttt+"");
			for (int i = 0; i < rects.length; i++) {
				if (rects[i].placed == false) {
				//	System.out.println("adding one" + rects[i].w + " "  + rects[i].h + " ");
					boolean cont;

					rects[i].reinit();
					do {
						cont = place(rects[i]);
						layout(ttt+1);
						rects[i].unplace();
					} while (cont);
				//	System.out.println("what" + ttt);
					rects[i].reinit();
					rects[i].rotate();
					do {
						cont = place(rects[i]);
						layout(ttt+1);
						rects[i].unplace();
					} while (cont);
					rects[i].rotate();
				//	System.out.println("what");
				}
			}
		}
		private boolean place(Rect rect) {
			int x0 = 0, d0 = 0;
			Integer[] bounds = new Integer[4];
			for (int i = 0; i < 4; i++) {
				bounds[i] = 0;
			}
			for (int i = 0; i < rects.length; i++) {
				rects[i].push(bounds);
			}
			int dInf = bounds[2] + bounds[3];
			if (dInf == 0)
				dInf = 1;
			if (rect.x + rect.y > 0) {
				x0 = rect.y == 0 ? 0 : rect.x + 1;
				d0 = rect.y == 0 ? rect.x + rect.y + 1 : rect.x + rect.y;
				if (d0 > dInf)
					return false;
			}
			for (int d = d0; d < dInf; d++, x0 = 0) {
				for (int x = x0; x <= d; x++) {
					int y = d - x;
			//		System.out.println(rect.w + " "  + rect.h + " "  + x + " " + y);

					rect.place(x, y);
					boolean bad = false;
					for (int i = 0; i < rects.length; i++) {
						if (rects[i] != rect
								&& rects[i].intersect(rect)) {
							bad = true;
							break;
						}
					}
					if (!bad) {
						bounds = new Integer[4];
						for (int ii = 0; ii < 4; ii++) {
							bounds[ii] = 0;
						}
						boolean full = true;
						for (int i = 0; i < rects.length; i++) {
							rects[i].push(bounds);
							if (!rects[i].placed)
								full = false;
						}
						int ww = bounds[3]-bounds[1];
						int hh = bounds[2]-bounds[0];
						rect.place(x, y);
						if (full) {
							int areaOld = results.get(0).x * results.get(0).y;
							if (ww * hh < results.get(0).x * results.get(0).y) {
								results.clear();
								results.add(new Point(ww, hh));
								System.out.println("pushed " + ww * hh);
								for (Rect r : rects) {
									System.out.println("rects " + r.w +" "+ r.h + " " + r.x + " " + r.y);
								}
							} else if (ww * hh == areaOld && ! results.contains(new Point(ww, hh))) {
								results.add(new Point(ww, hh));
								System.out.println("added " + ww * hh);
								for (Rect r : rects) {
									System.out.println("rects " + r.w +" "+ r.h + " " + r.x + " " + r.y);
								}
							}
						}
						return true;
					}
				}
			}
			return false;
		}
	}
	*/
	private static void problem(Scanner cin, Writer fw) throws IOException {
		Rect[] rects = new Rect[4];
		for (int i = 0; i < 4; i++) {
			rects[i] = new Rect(cin.nextInt(), cin.nextInt(), i);
		}

		int[] xs = new int[5];
		xs[0] = 0;
		int[] ys = new int[5];
		ys[0] = 0;
		haa(rects, xs, ys);
		for (int i = 0; i < rects.length; i++) {
			for (int j = i; j < rects.length-1; j++) {
				if (rects[j].index < rects[j+1].index) {
					Rect t = rects[j];
					rects[j] = rects[j+1];
					rects[j+1] = t;
				}
				haa(rects, xs, ys);
			}
		}
		Collections.sort(results);
		fw.write(results.get(0).area() + "\n");
		for (Point p : results) {
			fw.write(p.x + " " + p.y+ "\n");
		}
	}
	private static void haa(Rect[] rects, int[] xs, int[] ys) {
		for (int b1 = 0; b1 < 2; b1++) {
			if (b1 == 1) rects[1].rotate();
			for (int b2 = 0; b2 < 2; b2++) {
				if (b2 == 1) rects[2].rotate();
				for (int b3 = 0; b3 < 2; b3++) {
					if (b3 == 1) rects[3].rotate();
					arrange(rects, 0, xs, ys);
					if (b3 == 1) rects[3].rotate();
				}
				if (b2 == 1) rects[2].rotate();
			}
			if (b1 == 1) rects[1].rotate();
		}
	}
	public static ArrayList<Point> results = new ArrayList<Point>();
	static {
		results.add(new Point(1000, 10000));
	}
	public static int maxInt(int[] nums) {
		int max = 0;
		for (int n : nums) {
			if (n > max)
				max = n;
		}
		return max;
	}
	private static void arrange(Rect[] rects, int index, int[] xs, int[] ys) {
		Rect rect = rects[index];
		for (int xi = 0; xi < index+1; xi++) {
			int x = xs[xi];
			for (int yi =0; yi < index+1; yi++) {
				int y = ys[yi];
				boolean good = true;
				rect.place(x, y);
				for (int i = 0; i < index; i++) {
					if (rects[i].intersect(rect)) {
						good = false;
						break;
					}
				}
				if (good) {
					xs[index+1] = rect.x + rect.w;
					ys[index+1] = rect.y + rect.h;
					if (index == rects.length-1) {
						Point np = new Point(maxInt(xs), maxInt(ys));
						Point sam = results.get(0);
						if (np.area() < sam.area()) {
							results.clear();
							results.add(np);
						} else if (np.area() == sam.area()
								&& !results.contains(np)) {
							results.add(np);
						}
					} else {
						// add the new anchor
						arrange(rects, index+1, xs, ys);
					}
				}
			}
		}
	}
}
