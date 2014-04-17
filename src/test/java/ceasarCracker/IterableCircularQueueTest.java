package test.java.ceasarCracker;

import static org.junit.Assert.*;
import main.java.ceasarCracker.IterableCircularQueue;

import org.junit.Test;


public class IterableCircularQueueTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		@SuppressWarnings("rawtypes")
		IterableCircularQueue<Integer> q = new IterableCircularQueue(5);
		 
	       q.enqueue(0);
	       q.enqueue(1);
	       q.enqueue(2);
	       q.enqueue(3);
	       q.enqueue(4);
	 
	       assertEquals(new Integer(4), q.get(4));
	 
	       q.enqueue(5);// first item gets over-written
	 
	       assertEquals(new Integer(5), q.get(4));
	        assertEquals(new Integer(1), q.dequeue());
	 
	       q.enqueue(6);
	       q.enqueue(7);
	 
	       assertEquals(new Integer(3), q.dequeue());
	 
	       assertEquals(new Integer(5), q.get(1));
	       assertEquals(new Integer(6), q.get(2));
	 
	       q.enqueue(8);
	       q.enqueue(9);
	 
	       assertEquals(new Integer(6), q.get(1));
	       assertEquals(new Integer(5), q.dequeue());
	       assertEquals(new Integer(7), q.get(1));
	       assertEquals(new Integer(6), q.dequeue());
	       assertEquals(new Integer(8), q.get(1));
	 
	        assertEquals(new Integer(7), q.peek());
	 
	       assertEquals(new Integer(7), q.dequeue());
	       assertEquals(new Integer(8), q.dequeue());
	       assertEquals(new Integer(9), q.dequeue());
	 
	       assertTrue( q.isEmpty());
	 
	       q.enqueue(10);
	       q.enqueue(11);
	       q.enqueue(12);
	       q.enqueue(13);
	 
	       assertEquals(new Integer(10), q.get(0));
	 
	       q.enqueue(14);
	       q.enqueue(15);
	       q.enqueue(16);
	       q.enqueue(17);
	 
	       assertEquals(new Integer(13), q.get(0));
	       assertEquals(new Integer(13), q.peek());
	       assertEquals(new Integer(13), q.dequeue());
	       assertEquals(new Integer(14), q.get(0));

	}

}

