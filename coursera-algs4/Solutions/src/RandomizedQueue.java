import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr = (Item[]) new Object[4];
    private int count;
   public RandomizedQueue() {
   }
   public boolean isEmpty() {
       return count == 0;
   }
   public int size() {
       return count;
   }
   public void enqueue(Item item) {
       if (item == null)
           throw new NullPointerException();
       if (count == arr.length)
           enlarge();
       arr[count++] = item;
   }
   private void enlarge() {
       Item[] a = (Item[]) new Object[count*2];
       for (int i = 0; i < count; i++)
           a[i] = arr[i];
       arr = a;
   }
public Item dequeue() {
    if (count == 0)
        throw new NoSuchElementException();
    int i = StdRandom.uniform(count);
    Item it = arr[i];
    arr[i] = arr[count-1];
    arr[count-1] = null;
    count--;
    checkShink();
    return it;
}
   private void checkShink() {
       if (count * 4+1 < arr.length) {
           Item[] a = (Item[]) new Object[count*2+1];
           for (int i = 0; i < count; i++)
               a[i] = arr[i];
           arr = a;
       }
}
public Item sample() {
    if (count == 0)
        throw new NoSuchElementException();
       return arr[StdRandom.uniform(count)];
   }


private class Iter implements Iterator<Item> {
    private int j = 0;
    private Item[] ri;
    Iter() {
        ri = (Item[]) new Object[count];
        for (int i = 0; i < count; i++) {
            ri[i] = arr[i];
        }
        StdRandom.shuffle(ri);
    }
    @Override
    public boolean hasNext() {
        return j < ri.length;
    }

    @Override
    public Item next() {
        if (j >= ri.length)
            throw new NoSuchElementException();
        return ri[j++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
       

}

   public Iterator<Item> iterator() {
       return new Iter();
   }
}
