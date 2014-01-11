/*
ID: pirripe1
LANG: JAVA
TASK: checker
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


// after changing the inner loop to a look up table, the speed X2 fast
// latest one cost 0.770

public class checker {

	private static final String DEBUG_STRING = "13";

	private static final boolean DEBUG = false;
	public static final String PRO = "checker";
	
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
		int[] pos = new int[N];
		boolean[] ls = new boolean[N];
		boolean[] dia = new boolean[N*2];
		boolean[] dib = new boolean[N*2];
//		boolean firstDone = false;
		int NSOL = 0;
//		int NSOLPL[] = new int[N];
		for (;;) {
			for (int i = 0; i < N; i++) {
				// pos it
				int inci = -1;
				int p = pos[i];
				for (; p < N; p++) {
					if (!(ls[p] || dia[i+p] || dib[N+i-p])) {
						ls[p] = true;
						dia[i+p] = true;
						dib[N+i-p] = true;
						break;
					}
				}
				pos[i] = p;
				if (p == N) {
					inci = i - 1;
				} else if (i == N - 1) {
					if (NSOL < 3) {
						for (int ii = 0; ii < N; ii++) {
							fw.write((pos[ii]+1) + (ii == N -1 ? "\n" : " "));
						}
	//				} else if (bari[0] < (N+1)/2+1) {
	//					firstDone = true;
					}
					inci = N - 1;
	//				NSOLPL[bari[0]]++;
					NSOL++;
				}
				if (inci >= 0) {
					pos[inci]++;
					i = inci;
					for (int ii = N - 1; ii > inci; ii--) {
						pos[ii] = 0;
					}
					for (int ii = inci; ii >= 0; ii--) {
						ls[pos[ii]-1] = false;
						dia[ii+pos[ii]-1] = false;
						dib[N+ii-pos[ii]+1] = false;
						if (pos[ii] == N) {
							if (ii == 0) {
								fw.write(NSOL + "\n");
								return;
							} else {
								pos[ii-1]++;
								pos[ii] = 0;
								i = ii-1;
							}
						} else {
							break;
						}
					}
					i--;
			/*		if (firstDone && bari[0] == (N+1)/2+1) {
						int SOL = 0;
						for (int ii = 0; ii < N; ii++) {
							SOL += NSOLPL[Math.min(ii, N-1-ii)];
						}
						fw.write(SOL + "\n");
						return;
					} 
			*/		
				}
			}
		}
	}


}
