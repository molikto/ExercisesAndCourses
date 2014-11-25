import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.TreeMap;



public class NLP {
	
	public static final boolean DEBUG = false;

	public static abstract class LineProcessor {
		String fn;
		public LineProcessor(String fn) throws IOException {
			this.fn = fn;
		}
		public void run() throws IOException {
			BufferedReader br = new BufferedReader(new FileReader(fn));
	        for (String line = br.readLine(); line != null; line = br.readLine()) {
	        	processLine(line);
	        }
		}
		abstract void processLine(String line) throws IOException;
	}
	
	public static abstract class LineRewriter extends LineProcessor {

		FileWriter fw;
		public LineRewriter(String fn, String outFn) throws IOException {
			super(fn);
			fw = new FileWriter(outFn);
		}
		@Override
		public void run() throws IOException {
			super.run();
			fw.close();
		}
		@Override
		void processLine(String line) throws IOException {
			String s = rewriteLine(line);
			if (s != null) {
				fw.append(s);
				fw.append("\n");
			}
		}
		
		abstract String rewriteLine(String line);
	}

	private static Hashtable<String, Integer>
	buildWordTable(String trainFn) throws IOException {
		final Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
		new LineProcessor(trainFn) {
			@Override
			void processLine(String line) {
	        	int ib = line.indexOf(' ');
	        	if (ib == -1)
	        		return;
	        	String key = line.substring(0, ib);
	        	Integer c = ht.get(key);
	        	ht.put(key, c == null ? 1 : c+1);
			}}.run();
		return ht;
	}
	
	public static class TagList {
		int[] ints = new int[2];

		public void changeCount(String tag, int count) {
			if (tag.equals("O"))
				ints[0] = count;
			else if (tag.equals("I-GENE"))
				ints[1] = count;
			else {
				throw new IllegalArgumentException();
			}
				
		}

		public int getCountO() {
			return ints[0];
		}
		public int getCountG() {
			return ints[1];
		}
	}
	
	public static class Gram implements Comparable<Gram> {
		String[] from;
		public Gram(String[] strs, int i) {
			from = new String[strs.length - i];
			for (int j = i; j < strs.length; j++)
				from[j-i] = strs[j];
		}
		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			if (!(obj instanceof Gram))
				return false;
			Gram g = (Gram) obj;
			return Arrays.equals(from, g.from);
		}
		
		@Override
		public String toString() {
			String re = "";
			for (String s : from)
				re += s + " ";
			return re;
		}
		@Override
		public int compareTo(Gram g) {
			int l = from.length - g.from.length;
			if (l != 0)
				return l;
			for (int i = 0; i < from.length; i++) {
				int k = from[i].compareTo(g.from[i]);
				if (k != 0)
					return k;
			}
			return 0;
		}
	}
	
	public static void PA1(String[] args) throws IOException {
		String trainFn = "gene.train";
		String trainRareFn = "gene.train.rare";
		
		final boolean PA3 = true;
		

		String trainRareCountFn = PA3 ? "gene.train.rare.count2" :  "gene.train.rare.count3";
		final Hashtable<String, Integer> wordTable = buildWordTable(trainFn);
		new LineRewriter(trainFn, trainRareFn) {
			@Override
			String rewriteLine(String line) {
	        	int ib = line.indexOf(' ');
	        	if (ib == -1)
	        		return line;
	        	String str = line.substring(0, ib);
	        	Integer count = wordTable.get(str);
	        	if (count != null && count < 5) {
	        		String str2 = line.substring(ib, line.length());
	        		if (str.matches(".*[0-9].*") && PA3)
	        			return "__NUMBER__" + str2;
	        		else if (str.matches("[A-Z]+") && PA3)
	        			return "__ALLCAP__" + str2;
	        		else if (str.matches(".*[A-Z]") && PA3)
	        			return "__LASTCAP__" + str2;
	        		else
	        			return "__RARE__" + str2;
	        	}
	        	else
	        		return line;
			}}.run();
		Runtime.getRuntime().exec("python count_freqs.py " + trainRareFn + " > " + trainRareCountFn);
		
		final Hashtable<String, TagList> tagTable = new Hashtable<String, TagList>();
		final TreeMap<Gram, Integer> qs = new TreeMap<Gram, Integer>();
		new LineProcessor(trainRareCountFn) {
			@Override
			void processLine(String line) throws IOException {

				String[] strs = line.split(" ");
				if (strs.length == 0)
					return;
				int count = Integer.parseInt(strs[0]);
				String type = strs[1];
				if (type.equals("WORDTAG")) {
					String tag = strs[2];
					String word = strs[3];
					if (tagTable.get(word) == null)
						tagTable.put(word, new TagList());
					tagTable.get(word).changeCount(tag, count);
				} else {
					qs.put(new Gram(strs, 2), count);
				//	System.out.println(qs.get(new Gram(strs, 2)));
				}
			}}.run();
		
		new LineRewriter("gene.test", PA3 ? "gene_test.p3.out" : "gene_test.p2.out") {
			LinkedList<TreeMap<Gram, Double>> abys = new LinkedList<TreeMap<Gram, Double>>();
			LinkedList<String> lines = new LinkedList<String>();
			int k = 1;
			{
				init();
			}
			void init() {
				TreeMap<Gram, Double> star = new TreeMap<Gram, Double>();
				star.put(new Gram(new String[]{"*", "*", "*"}, 0), 1.0);
				abys.push(star);
				k = 1;
			}
			void tini() throws IOException {
				Stack<String> token = new Stack<String>();
				double max = 0;
				Gram lastKey = null;
				for (Entry<Gram, Double> e : abys.poll().entrySet()) {
					double toCompare = e.getValue()
						* qs.get(new Gram(new String[]{e.getKey().from[1], e.getKey().from[2], "STOP"}, 0))
						/ qs.get(new Gram(e.getKey().from, 1));
					if (toCompare > max) {
						max = toCompare;
						lastKey = e.getKey();
					}
				}
				token.push(lastKey.from[2]);
				token.push(lastKey.from[1]);
				token.push(lastKey.from[0]);
				while (! abys.isEmpty()) {
					for (Gram g : abys.poll().keySet()) {
						if (g.from[1] == lastKey.from[0] && g.from[2] == lastKey.from[1]) {
							lastKey = g;
							token.push(lastKey.from[0]);
							break;
						}
					}
				}
				while (token.peek().equals("*"))
					token.pop();
				while (!lines.isEmpty()) {
					if (lines.peek().isEmpty()) {
						lines.poll();
						fw.append("\n");
					} else {
						fw.append(lines.poll() + " " + token.pop() + "\n");
					}
				}
			}
			@Override
			String rewriteLine(String line) {
				if (DEBUG)
					try {int iii = System.in.read();} catch (IOException e2) {}

				lines.offer(line);
				if (line.isEmpty()) {
					try {
						if (DEBUG)
							System.out.println("newline\r\n");
						tini();
						init();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					
					final String[] SI = {"*"};
					final String[] SN = {"I-GENE", "O"};
					String[] su = k - 1 > 0 ? SN : SI;
					String[] sv = SN;
					TreeMap<Gram, Double> pis = new TreeMap<Gram, Double>();
					TagList t = tagTable.get(line);
					if (t == null) {
		        		if (line.matches(".*[0-9].*"))
		        			t = tagTable.get("__NUMBER__");
		        		else if (line.matches("[A-Z]*"))
		        			t = tagTable.get("__ALLCAP__");
		        		else if (line.matches(".*[A-Z]"))
		        			t = tagTable.get("__LASTCAP__");
		        		else
		        			t = tagTable.get("__RARE__");
					}
					if (t == null)
	        			t = tagTable.get("__RARE__");
						
					for (String u : su) {
						for (String v : sv) {
							String w = null;
							double max = 0;
							for (Entry<Gram, Double> e : abys.peek().entrySet()) {
								if (e.getKey().from[2].equals(u)) {
									if (DEBUG) {
										System.out.print(e.getValue()
										+ "*" + qs.get(new Gram(new String[]{e.getKey().from[1], u, v}, 0))
										+ "*" + (v.equals("O") ? t.getCountO() : t.getCountG())
										+ "/" + qs.get(new Gram(new String[]{v}, 0))
										+ "/" + qs.get(new Gram(new String[]{e.getKey().from[1], u}, 0)));
									}
									double toCompare = e.getValue()
										* qs.get(new Gram(new String[]{e.getKey().from[1], u, v}, 0))
										* (v.equals("O") ? t.getCountO() : t.getCountG())
										/ qs.get(new Gram(new String[]{v}, 0))
										/ qs.get(new Gram(new String[]{e.getKey().from[1], u}, 0));

									if (DEBUG)
										System.out.println(toCompare);
									if (toCompare >= max) {
										max = toCompare;
										w = e.getKey().from[1];
									}
								}
							}
							pis.put(new Gram(new String[]{w, u, v}, 0), max);
						}
					}
					if (DEBUG) {
						System.out.println(pis);
					}
					double d = Collections.max(pis.values());
					d/=Math.pow(Math.E, Math.log(d)/2);
					for (Entry<Gram, Double> e : pis.entrySet())
						e.setValue(e.getValue()/d);
					abys.push(pis);
					k++;
				}
				return null;
			}
				//if ();
		/*		if (line.isEmpty()) {
					return "";
				}
				TagList t = tagTable.get(line) == null
						? tagTable.get("_RARE_")
						: tagTable.get(line);
				for (int i = 0; i < LEN -1; i++) {
					gram.from[i] = gram.from[i+1];
				}
				gram.from[LEN-1] = "O";
				double pO = (0.0 + t.getCountO())/qs.get(gram);
				gram.from[LEN-1] = "I-GENE";
				double pG = (0.0 + t.getCountG())/qs.get(gram);
				gram.from[LEN-1] = pG > pO ? "I-GENE" : "O";
				return line + " " + gram.from[LEN-1];
			}
		*/	
			
		}.run();
	}
	
	public static void main(String[] args) throws IOException {
		PA1(args);
	}
}
