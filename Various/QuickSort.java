import java.util.*;

public class Quicksort {


    public static void main(String[] args) {
        Random rand = new Random();
        int[] a = new int[rand.nextInt(100000)];
        for (int i = 0; i < a.length; i++) {
            a[i] = rand.nextInt(a.length);
        }
        int[] b = Arrays.copyOf(a, a.length);
        long t1 = System.nanoTime();
        quicksort(a, 0, a.length);
        long t2 = System.nanoTime();

        Arrays.sort(b);
        long t3 = System.nanoTime();
        System.out.println(a.length + "\n" + (t2 - t1) + "\n" + (t3 - t2));
    }

    private static void quicksort(int[] a, int s, int e) {
        if (e - s > 1) {
            int l = s + 1;
            int t = e - 1;
            int p = a[s];
            while (true) {
                while (l <= t && a[l] < p) { l++;}
                while (t >= l && a[t] > p) { t--;}
                if (t < l) break;
                exchange(a, l++, t--);
            }
            exchange(a, s, t);
            quicksort(a, s, t);
            quicksort(a, t + 1, e);
        }
    }

    private static void exchange(int[] a, int l, int t) {
        int temp = a[l];
        a[l] = a[t];
        a[t] = temp;
    }
}