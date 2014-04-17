package main.java.ceasarCracker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IterableCircularQueue<T> implements Iterable<T> {
 
    private final int capacity;
    private List<T> list;
    private int count = 0;
 
    public IterableCircularQueue(int capacity) {
        this.capacity = capacity;
        list = new ArrayList<T>(capacity);
 
    }
 
    /**
     * add item to queue
     * @param elem
     */
    public void enqueue(T elem) {
 
        if (count < capacity) {
            list.add(elem);
        } else {
            if (list.size() == capacity) {
                list.set(count % capacity, elem);
            } else {
                list.add(count % capacity, elem);
            }
        }
        count++;
 
    }
 
    /**
     * Inspect the first item of the queue.
     * @return
     */
    public T peek() {
 
        if (list.isEmpty()) {
            throw new RuntimeException(" queue is empty");
        }
        T value = null;
        if (count > capacity) {
            int head = count % capacity;
            if (head >= list.size()) {
                head = head - list.size();
            }
            value = list.get(head);
        } else {
            value = list.get(0);
        }
 
        return value;
    }
 
    /**
     * remove an item from the queue
     * @return
     */
    public T dequeue() {
 
        if (list.isEmpty()) {
           throw new RuntimeException(" queue is empty");
        }
        T value = null;
        if (count > capacity) {
            int head = count % capacity;
            if (head >= list.size()) {
                head = head - list.size();
            }
            value = list.remove(head);
        } else {
            value = list.remove(0);
        }
        if (list.isEmpty()) {
            count = 0;
        }
 
        return value;
    }
 
    /**
     * index-based access to items in queue.
     * @note The item won't be removed when accessed using index
     * @param index
     * @return
     */
    public T get(int index) {
 
        if (index < 0 || index >= list.size()) {
            throw new IndexOutOfBoundsException();
        }
 
        int i = index;
 
        if (count > capacity) {
            i = count % capacity + index;
            if (i >= list.size()) {
                i = i - list.size();
            }
        }
        return list.get(i);
    }
 
    /**
     * number of items in queue
     * @return
     */
    public int size() {
        return list.size();
    }
 
    /**
     * check if the queue is empty
     * @return
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }
 
    public String toString() {
        return list.toString();
    }
 
    /**
     * Fetch an Iterator to iterate through the queue.
     * @return
     */
    public Iterator<T> iterator() {
        return new QueueIterator();
    }
 
    /**
     * Queue Iterator implementation
     */
    private class QueueIterator implements Iterator<T> {
 
        int cursor = 0;
 
        public boolean hasNext() {
 
            if (cursor >= capacity) {
                return false;
            }
 
            try {
                return get(cursor) != null;
            } catch (Exception e) {
                return false;
            }
        }
 
        public T next() {
        	if(cursor == capacity) cursor = 0;
            T val = get(cursor);
            cursor++;
            return val;
        }
 
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
}