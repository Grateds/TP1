package test.java.ceasarCracker;

import static org.junit.Assert.*;
import java.util.Arrays;

import main.java.ceasarCracker.Key;

import org.junit.Test;


public class KeyTest {
	
	@Test
	public void testKeyHasOne() {
		int[] ev = {4}; // expected value
		Key key = new Key(1);
		key.set(0, 3);
		key.inc();
		assertEquals(Arrays.toString(ev),Arrays.toString(key.get()));
	}
	
	@Test
	public void testKeyHasMany() {
		int[] ev = {0,0,5}; // expected value
		Key key = new Key(3);
		key.set(0, 256);key.set(1, 256);key.set(2, 4);
		key.inc();
		assertEquals(Arrays.toString(ev),Arrays.toString(key.get()));
	}
}
