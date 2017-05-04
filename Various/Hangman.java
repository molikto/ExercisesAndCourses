import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Hangman {


    public static int AB_SIZE = 26;

    static Random rand = new Random();

    private static int ctoi(char c) {
        return c - 'a';
    }
    private static char itoc(int i) {
        return (char) (i + 'a');
    }


    public static class State {
        // consistent
        char[] reveal;
        boolean[] guesses = new boolean[AB_SIZE];
        ArrayList<String> words = new ArrayList<String>();

        public State reveal(char c, boolean[] k) {
            assert (k.length == reveal.length);
            State s = new State();
            s.reveal = Arrays.copyOf(reveal, reveal.length);
            for (int i = 0; i < reveal.length; i++) {
                if (k[i]) {
                    s.reveal[i] = c;
                }
            }
            s.guesses = Arrays.copyOf(guesses, guesses.length);
            s.guesses[ctoi(c)] = true;
            s.words = new ArrayList<String>();
            for (String w : words) {
                boolean match = true;
                for (int i = 0; i < reveal.length; i++) {
                    if ((w.charAt(i) == c) != k[i]) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    s.words.add(w);
                }
            }
            return s;
        }

        public static State inital(int size) {
            State s = new State();
            s.reveal = new char[size];
            return s;
        }


        public char guess() {
            char[] counts = new char[AB_SIZE]; // we assume only a-z
            boolean[] temp = new boolean[26];
            for (String s : words) {
                for (int i = 0; i < s.length(); i++) {
                    if (reveal[i] == 0) {
                        temp[ctoi(s.charAt(i))] = true;
                    }
                }
                for (int i = 0; i < AB_SIZE; i++) {
                    if (temp[i]) {
                        counts[i] += 1;
                        temp[i] = false;
                    }
                }
            }
            int max = 0;
            char maxChar = 0;
            for (int i = 0; i < AB_SIZE; i++) {
                if (!guesses[i]) {
                    if (counts[i] > max || (counts[i] == max && rand.nextBoolean())) {
                        max = counts[i];
                        maxChar = itoc(i);
                    }
                }
            }
            return maxChar;
        }

    }

    private static final List<String> words = new ArrayList<String>();
    // lords(i) is words with length i
    private static final List<State> lords = new ArrayList<State>();

    static {
        try {
            BufferedReader br = new BufferedReader(new FileReader("wordlist.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String w : words) {
            int l = w.length();
            while (lords.size() <= l) lords.add(State.inital(lords.size())); // this is correct..!
            lords.get(l).words.add(w);
        }
    }

    private State state;

    public Hangman(int length) {
        this.state = lords.get(length);
    }



    private char guess() {
        return state.guess();
    }

    private void reveal(char c, boolean[] k) {
        state = state.reveal(c, k);
    }

    private String give() {
        if (state.words.size() == 1) {
            return state.words.get(0);
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            int s = Integer.parseInt(args[0]);
            Hangman sol = new Hangman(s);
            while (sol.give() == null) {
                for (char j : sol.state.reveal) {
                    if (j == 0) System.out.print('_');
                    else System.out.print(j);
                }
                char c = sol.guess();
                System.out.println("\nguess: " +  c);
                Scanner scanner = new Scanner(System.in);
                String str = scanner.nextLine();
                String[] rev = str.split(" ");
                boolean[] revs = new boolean[s];
                for (String ss : rev) {
                    try {
                        revs[Integer.parseInt(ss) - 1] = true;
                    } catch (Exception e) {
                    }
                }
                sol.reveal(c, revs);
            }
            System.out.println(sol.give());
        } else {
            test();
        }
    }

    public static void test() {
        int a = 0;
        int totalWrongs = 0;
        int totalWrongsBiggerThan3 = 0;
        int totalWrongsBiggerThan6 = 0;
        for (int i = 0; i < 1000; i++) {
            a += 231;
            a = a % words.size();
            String word = words.get(a);
            Hangman sol = new Hangman(word.length());
            int guesses = 0;
            int wrongs = 0;
            System.out.println(word);
            String give = sol.give();
            while (give == null) {
                give = sol.give();
                if (give == null) {
                    guesses += 1;
                    char c = sol.guess();
                    boolean[] revs = new boolean[word.length()];
                    boolean hit = false;
                    for (int k = 0; k < word.length(); k++) {
                        if (word.charAt(k) == c) {
                            revs[k] = true;
                            hit = true;
                        }
                    }
                    sol.reveal(c, revs);
                    if (!hit) {
                        wrongs += 1;
                        if (wrongs == 3) totalWrongsBiggerThan3 += 1;
                        if (wrongs == 6) totalWrongsBiggerThan6 += 1;
                    }
                    System.out.println("  " + guesses +"/" +  wrongs + " " + c + " " + hit + " " + sol.state.words.size());
                }
            }
        }
        System.out.println("### " + words.size() + ", " + totalWrongsBiggerThan6 + ", " + totalWrongsBiggerThan3 + ", " + totalWrongs + " ###");
    }


}