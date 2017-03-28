/*
ID: pirripe1
LANG: JAVA
TASK: shopping
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.math.BigInteger;

public class shopping {

    private static final String DEBUG_STRING = "";



    private static final boolean DEBUG = false;
    public static final String PRO = "shopping";

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


    public static class OfferItem {
        public int code;
        public int count;
    }
    public static class Offer implements Comparable<Offer> {
        public OfferItem[] items;
        public int price;

        @Override
        public int compareTo(Offer o) {
            return price - o.price;
        }
    }
    static Offer[] offers;
    public static class Item {
        public int code;
        public int price;
    }

    public static Item[] items;


    public static int index(int code) {
        for (int  j = 0; j < items.length; j++) {
            if (items[j].code == code) return j;
        }
        return -1;
    }


    public static class Remaining implements Comparable<Remaining> {
        int [] remaining;

        @Override
        public int hashCode() {
            int i = 1;
            for (int j : remaining) {
                i = i * j + 1;
            }
            return i;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Remaining) {
                Remaining o = (Remaining) obj;
                for (int i = 0; i < remaining.length; i++) {
                    if (o.remaining[i] != remaining[i])  return false;
                }
                return true;
            }
            return false;
        }

        @Override
        public int compareTo(Remaining o) {
            for (int i = 0; i < remaining.length; i++) {
                int j = o.remaining[i] - remaining[i];
                if (j != 0) return j;
            }
            return 0;
        }
    }

    public static class Plan {
        Remaining remaining = new Remaining();
        int price;


        public Plan tryAdd(Offer f, int code) {
            int[] newRemaining = Arrays.copyOf(remaining.remaining, remaining.remaining.length);
            int newPrice = price;
            for (int i = 0; i < f.items.length; i++) {
                OfferItem fi = f.items[i];
                int index = index(fi.code);
                if (index >= 0) {
                    newRemaining[index] -= fi.count;
                    if (newRemaining[index] < 0) return null;
                    newPrice -= items[index].price * fi.count;
                } else {
                    return null;
                }
            }
            newPrice += f.price;
            Plan n = new Plan();
            n.remaining = new Remaining();
            n.remaining.remaining = newRemaining;
            n.price = newPrice;
            return n;
        }
    }


    public static TreeMap<Remaining, Plan> plans = new TreeMap<Remaining, Plan>();

    private static void problem(Scanner cin, Writer fw) throws IOException {
        offers = new Offer[cin.nextInt()];
        for (int i = 0; i < offers.length; i++) {
            Offer o = new Offer();
            offers[i] = o;
            o.items = new OfferItem[cin.nextInt()];
            for (int j = 0; j < o.items.length; j++) {
                o.items[j] = new OfferItem();
                o.items[j].code = cin.nextInt();
                o.items[j].count = cin.nextInt();
            }
            o.price = cin.nextInt();
        }
        items = new Item[cin.nextInt()];
        Plan original = new Plan();
        original.remaining.remaining = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            Item t = new Item();
            items[i]  = t;
            t.code = cin.nextInt();
            original.remaining.remaining[i] = cin.nextInt();
            t.price = cin.nextInt();
            original.price += original.remaining.remaining[i] * t.price;
        }
        Arrays.sort(offers);
        plans.put(original.remaining, original);
        for (int i = 0; i < offers.length; i++) {
            Offer f = offers[i];
            ArrayList<Plan> previous = new ArrayList<Plan>(plans.values());
            for (Plan base : previous) {
                while (base != null) {
                    base = base.tryAdd(f, i);
                    if (base != null) {
                        Plan pp = plans.get(base.remaining);
                        if (pp != null && base.price > pp.price) {
                        } else {
                            plans.put(base.remaining, base);
                        }
                    }
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for (Plan p : plans.values()) {
            if (p.price < min) min = p.price;
        }
        fw.write(min + "\n");
    }
}
