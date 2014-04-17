package test.java.ceasarCracker;

import static org.junit.Assert.*;
import java.util.Arrays;

import main.java.ceasarCracker.Key;

import org.junit.Test;


public class KeyTest {
	
	@Test
	public void testKeyHasOne() {
		int[] A = {3}; // test case
		int[] ev = {4}; // expected value
		Key key = new Key(1);
		key.set(A);
		key.inc();
		assertEquals(Arrays.toString(ev),Arrays.toString(key.get()));
	}
	
	@Test
	public void testKeyHasMany() {
		int[] A = {256,256,4}; // test case
		int[] ev = {0,0,5}; // expected value
		Key key = new Key(3);
		key.set(A);
		key.inc();
		assertEquals(Arrays.toString(ev),Arrays.toString(key.get()));
	}
}
