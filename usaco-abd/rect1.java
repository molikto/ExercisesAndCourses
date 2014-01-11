/*
ID: pirripe1
LANG: JAVA
TASK: rect1
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Scanner;

// isn't this solution obvirous???
// Test 11: TEST OK [1.267 secs, 276880 KB]
// seems bad...
// whatever!!!
public class rect1 {

	private static final String DEBUG_STRING = "20 20 3 2 2 18 18 2 0 8 19 19 3 8 0 10 19 4";

	private static final boolean DEBUG = false;
	public static final String PRO = "rect1";
	
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

	
	public static class Rect {
		public Rect(int ll, int bb, int rr, int tt, int cc) {
			l = ll;
			b = bb;
			r = rr;
			t = tt;
			color = cc;
		}
		int l, b, r, t;
		int color;
		public boolean hasPoint(int x, int y) {
			return l <= x && r > x && b <= y && t > y;
		}
	}
	private static void problem(Scanner cin, Writer fw) throws IOException {
		Rect rp = new Rect(0, 0, cin.nextInt(), cin.nextInt(), 1);
		int N = cin.nextInt()+1;
		Rect[] rects = new Rect[N];
		rects[N-1] = rp;
		int[] xs = new int[2 * N], ys = new int[2 * N];
		for (N-= 2; N >= 0; N--) {
			rects[N] = new Rect(cin.nextInt(), cin.nextInt(),
					cin.nextInt(), cin.nextInt(), cin.nextInt());
		}
		for (int i = 0; i < rects.length; i++) {
			xs[2*i] = rects[i].l;
			xs[2*i+1] = rects[i].r;
			ys[2*i] = rects[i].t;
			ys[2*i+1] = rects[i].b;
		}
		Arrays.sort(xs);
		Arrays.sort(ys);
		int[] colors = new int[2501];
		for (int ix = 0; ix < xs.length-1; ix++) {
			for (int iy = 0; iy < ys.length-1; iy++) {
				for (Rect r : rects) {
					if (r.hasPoint(xs[ix], ys[iy])) {
						colors[r.color] += (xs[ix+1]-xs[ix])*(ys[iy+1]-ys[iy]);
						break;
					}
				}
			}
		}
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] != 0)
				fw.write(i + " " + colors[i] + "\n");
		}
	}
	
}
