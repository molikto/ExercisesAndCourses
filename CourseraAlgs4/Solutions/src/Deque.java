import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    private Node<Item> head;
    private Node<Item> tail;
    private int count;
    
    private static class Node<Item> {
        private Node<Item> next;
        private Node<Item> previous;
        private Item item;
   }
   public Deque() {
   }
   
   public boolean isEmpty() {
       return head == null;
   }
   public int size() {
       return count;
   }
   public void addFirst(Item item) {
      if (item == null)
          throw new NullPointerException();
       Node<Item> n = new Node<Item>();
       n.item = item;
       if (head == null) {
           head = n;
           tail = n;
       } else {
           n.next = head;
           head = n;
           n.next.previous = n;
       }
       count++;
   }
   
   public void addLast(Item item) {
      if (item == null)
          throw new NullPointerException();
       Node<Item> n = new Node<Item>();
       n.item = item;
       if (tail == null) {
           head = n;
           tail = n;
       } else {
           tail.next = n;
           n.previous = tail;
           tail = n;
       }
       count++;
   }
   public Item removeFirst() {
       if (head == null) {
           throw new NoSuchElementException();
       } else if (head == tail) {
           Item i = head.item;
           head = null;
           tail = null;
           count--;
           return i;
       } else {
           Item i = head.item;
           head = head.next;
           head.previous = null;
           count--;
           return i;
       }
   }
   public Item removeLast() {
       if (tail == null) {
           throw new NoSuchElementException();
       } else if (head == tail) {
           Item i = head.item;
           head = null;
           tail = null;
           count--;
           return i;
       } else {
           Item i = tail.item;
           tail = tail.previous;
           tail.next = null;
           count--;
           return i;
       }
   }
   
   private class Iter implements Iterator<Item> {

       private Node<Item> i;
       
       Iter() {
           i = head;
       }
       
    @Override
    public boolean hasNext() {
        return i != null;
    }

    @Override
    public Item next() {
        if (i == null)
           throw new NoSuchElementException();
        Item it = i.item;
        i = i.next;
        return it;
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