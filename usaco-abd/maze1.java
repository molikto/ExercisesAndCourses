/*
ID: pirripe1
LANG: JAVA
TASK: maze1
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
import java.util.Scanner;

public class maze1 {

	private static final String DEBUG_STRING = "5 3\n+-+-+-+-+-+\n|         |\n+-+ +-+ + +\n|     | | |\n+ +-+-+ + +\n| |     |  \n+-+ +-+-+-+";

	private static final boolean DEBUG = false;
	public static final String PRO = "maze1";
	
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
		int X = cin.nextInt();
		int Y = cin.nextInt();
		cin.nextLine();
		char[][] maze = new char[2*Y+3][];
		char[] BORD = new char[2*X+3];
		maze[0] = maze[2*Y+2] = BORD;
		for (int y = 1; y < maze.length-1; y++) {
			maze[y] = ("\0" + cin.nextLine().replace(' ', Character.MAX_VALUE) + "\0")
				.toCharArray();
		}
		boolean changed = true;
		while (changed) {
			changed = false;
			for (int y = 2; y < 2*Y+2; y+=2) {
				for (int x = 2; x < 2*X+2; x+=2) {
					char m = Character.MAX_VALUE;
					if (maze[y-1][x] == Character.MAX_VALUE)
						m = (char) Math.min(m, maze[y-2][x]);
					if (maze[y+1][x] == Character.MAX_VALUE)
						m = (char) Math.min(m, maze[y+2][x]);
					if (maze[y][x-1] == Character.MAX_VALUE)
						m = (char) Math.min(m, maze[y][x-2]);
					if (maze[y][x+1] == Character.MAX_VALUE)
						m = (char) Math.min(m, maze[y][x+2]);
					if (maze[y][x]-1 > m) {
						changed = true;
						maze[y][x] = (char) (m + 1);
						changed = true;
					}
				}
			}
		}
		int max = 0;
		for (int y = 2; y < 2*Y+2; y+=2) {
			for (int x = 2; x < 2*X+2; x+=2) {
				max = Math.max(maze[y][x], max);
			}
		}
		fw.write(max+"\n");
	}



	
}
