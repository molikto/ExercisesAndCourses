import java.util.LinkedHashMap;

public class LRUCache {


    public LinkedHashMap<Integer, Integer> map = new LinkedHashMap<Integer, Integer>();
    int cap = 0;
    public LRUCache(int capacity) {
        cap = capacity;
    }

    public int get(int key) {
        Integer i = map.get(key);
        if (i == null) {
            return -1;
        } else {
            map.remove(key);
            map.put(key, i);
            return i;
        }
    }

    public void put(int key, int value) {
        if (!map.containsKey(key) && cap == map.size()) {
            int a = map.keySet().iterator().next();
            map.remove(a);
        }
        map.remove(key);
        map.put(key, value);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */