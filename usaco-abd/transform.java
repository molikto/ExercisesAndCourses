import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;

public class transform {


	private static final String DEBUG_STRING = "";

	private static final boolean DEBUG = true;
	public static final String PRO = "transform";
	
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

	public static class Matrix {
		int n;
		byte[][] entries;
		public Matrix(int nn, Scanner in) {
			n = nn;
			entries = new byte[n][];
			for (int i = 0; i < n; i++) {
				entries[i] = in.nextLine().getBytes();
			}
		}
		
		public Matrix(int nn) {
			n = nn;
			entries = new byte[n][];
			for (int i = 0; i < n; i++) {
				entries[i] = new byte[n];
			}
		}
		
		public Matrix rotate(int drg) {
			Matrix nm = new Matrix(n);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					switch (drg) {
					case 90:
						nm.entries[j][n-1-i]=entries[i][j];
						break;
					case 180:
						nm.entries[n-1-i][n-1-j]=entries[i][j];
						break;
					case 270:
						nm.entries[n-1-j][i]=entries[i][j];
						break;
					}
				}
			}
			return nm;
		}
		
		public Matrix reflect() {
			Matrix nm = new Matrix(n);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					nm.entries[i][n-1-j] = entries[i][j];
				}
			}
			return nm;
		}
		
		public boolean equals(Matrix nm) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (nm.entries[i][j] != entries[i][j])
						return false;
				}
			}
			return true;
		}
	}

	private static void problem(Scanner cin, Writer wr) throws IOException {
		int n = cin.nextInt();
		cin.nextLine();
		Matrix m = new Matrix(n, cin);
		Matrix r = m.reflect();
		Matrix mn = new Matrix(n, cin);

		for (int i = 90; i <= 270; i+= 90) {
			if (m.rotate(i).equals(mn)) {
				wr.write(Integer.toString(i/90)+"\n");
				return;
			}
		}
		if (r.equals(mn)) {
			wr.write("4\n");
			return;
		}
		for (int i = 90; i <= 270; i+= 90) {
			if (r.rotate(i).equals(mn)) {
				wr.write("5\n");
				return;
			}
		}
		if (m.equals(mn)) {
			wr.write("6\n");
			return;
		}
		wr.write("7\n");
	}
}
