package test.java.ceasarCracker;

import static org.junit.Assert.*;
import java.util.Arrays;

import main.java.ceasarCracker.CeasarCracker;

import org.junit.Test;

/**
 * Tests for class CeasarCracker. Contains tests for all (public) routines of
 * this class.
 * 
 * @author Nazareno Aguirre
 * @version 0.1 12/04/2014
 */

public class CeasarCrackerTests {

	@Test
	public void testDefaultConstructor() {
		CeasarCracker cracker = new CeasarCracker();
		assertEquals("default pass length is one", 1, cracker.getPasswordLength());
		assertEquals("message word is empty", "", cracker.getMessageWord());
		assertEquals("encrypted message is empty", "", cracker.getEncryptedMessage());
	}

	@Test
	public void testConstructorWithWordAndMessage() {
		CeasarCracker cracker = new CeasarCracker("encrypted", "word");
		assertEquals("default pass length is one", 1, cracker.getPasswordLength());
		assertEquals("message word is correct", "word",	cracker.getMessageWord());
		assertEquals("encrypted message is correct", "encrypted", cracker.getEncryptedMessage());
	}

	@Test
	public void testSettingEncryptedMessage() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setEncryptedMessage("abcd");
		assertEquals("encrypted message is correctly set", "abcd",	cracker.getEncryptedMessage());
	}

	@Test
	public void testSettingKnownWord() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setMessageWord("abcd");
		assertEquals("known word is correctly set", "abcd",	cracker.getMessageWord());
	}

	@Test
	public void testSimpleKey() {
		int[] key = { 1 };
		String output = CeasarCracker.encode("hola", key);
		assertEquals("ipmb", output);
		String output2 = CeasarCracker.decode("ipmb", key);
		assertEquals("hola", output2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMessageEncode() {
		int[] key = { 1 };
		CeasarCracker.encode(null, key);
		// must break!
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidKeyEncode() {
		int[] key = null;
		CeasarCracker.encode("hola", key);
		// must break!
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidEmptyKeyEncode() {
		int[] key = {};
		CeasarCracker.encode("hola", key);
		// must break!
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMessageDecode() {
		int[] key = { 1 };
		CeasarCracker.decode(null, key);
		// must break!
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidKeyDecode() {
		int[] key = null;
		CeasarCracker.decode("hola", key);
		// must break!
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidEmptyKeyDecode() {
		int[] key = {};
		CeasarCracker.decode("hola", key);
		// must break!
	}

	@Test
	public void testComplexKey() {
		int[] key = { 1, 0 };
		String output = CeasarCracker.encode("hola", key);
		assertEquals("ioma", output);
		String output2 = CeasarCracker.decode("ioma", key);
		assertEquals("hola", output2);
	}
	
	@Test
	public void testMoreComplexKey() {
		int[] key = { 20, 150, 101 };
		String output = CeasarCracker.encode("hola que tal como andas", key);
		String output2 = CeasarCracker.decode(output, key);
		assertEquals("hola que tal como andas", output2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidBruteForce() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setEncryptedMessage(null);
		// doesn't even reach the statement below. Statement above fails!
		cracker.bruteForceDecrypt();
		// must crash!
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidBruteForce2() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setMessageWord(null);
		// doesn't even reach the statement below. Statement above fails!
		cracker.bruteForceDecrypt();
		// must crash!
	}

	@Test(expected = IllegalStateException.class)
	public void testInvalidBruteForce3() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setMessageWord("hola");
		cracker.setEncryptedMessage("h");
		cracker.bruteForceDecrypt();
		// must crash!
	}

	@Test(expected = IllegalStateException.class)
	public void testInvalidBruteForce4() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setEncryptedMessage("ipmbfasdfa");
		cracker.setPasswordLength(0);
		cracker.setMessageWord("hola");
		cracker.bruteForceDecrypt();
		// must crash!
	}

	@Test
	public void testSimpleBruteForce() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setEncryptedMessage("ipmbfasdfa");
		cracker.setPasswordLength(1);
		cracker.setMessageWord("hola");
		boolean isDecrypted = cracker.bruteForceDecrypt();
		assertTrue("message decrypted", isDecrypted);
	}

	@Test
	public void testLessSimpleBruteForce() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setEncryptedMessage("iomafasdfa");
		cracker.setMessageWord("hola");
		cracker.setPasswordLength(2);
		boolean isDecrypted = cracker.bruteForceDecrypt();
		assertTrue("message decrypted", isDecrypted);
	}

	@Test
	public void testFailedComplexBruteForce() {
		CeasarCracker cracker = new CeasarCracker();
		int[] key = { 1, 100, 101 };
		cracker.setEncryptedMessage(CeasarCracker.encode("hola que tal", key));
		cracker.setMessageWord("hola");
		boolean isDecrypted = cracker.bruteForceDecrypt();
		// message not decrypted. 1-valued keys cannot decode message
		assertFalse("message decrypted", isDecrypted);
		assertTrue("key not found", cracker.foundKey() == null);
	}

	@Test
	public void testComplexBruteForce() {
		CeasarCracker cracker = new CeasarCracker();
		int[] key = { 1, 100, 101 };
		cracker.setEncryptedMessage(CeasarCracker.encode("hola que tal", key));
		cracker.setMessageWord("hola");
		cracker.setPasswordLength(3);
		boolean isDecrypted = cracker.bruteForceDecrypt();
		int[] result = cracker.foundKey();
		assertTrue("message decrypted", isDecrypted);
		assertTrue("encoding key is correct", Arrays.equals(key, result));
	}

	@Test
	public void testMoreComplexBruteForce() {
		CeasarCracker cracker = new CeasarCracker();
		int[] key = { 20, 100, 101 };
		cracker.setEncryptedMessage(CeasarCracker.encode("hola que tal como andas", key));
		cracker.setMessageWord("hola");
		cracker.setPasswordLength(3);
		boolean isDecrypted = cracker.bruteForceDecrypt();
		int[] result = cracker.foundKey();
		assertTrue("message decrypted", isDecrypted);
		assertTrue("encoding key is correct", Arrays.equals(key, result));
	}

	@Test
	public void testEvenMoreComplexBruteForce() {
		// may be slow (more than 3 minutes on a modern PC)
		// comment the @Test above for just running the other "quicker" tests.
		CeasarCracker cracker = new CeasarCracker();
		int[] key = { 3, 23, 151, 103 };
		cracker.setEncryptedMessage(CeasarCracker.encode("hola que tal como andas", key));
		cracker.setMessageWord("hola");		
		cracker.setPasswordLength(4);
		boolean isDecrypted = cracker.bruteForceDecrypt();
		int[] result = cracker.foundKey(); // result = {206, 137, 105, 6}
		assertTrue("message decrypted", isDecrypted);
		assertTrue("encoding key is correct", Arrays.equals(key, result));
	}

//	@Test
//	public void testOneSumOfArrangements() {
//		// check if the sum of two arrays is correct
//		int[] key = { 1, 2, 0 };
//		int[] arreAscii = { 10, 15, 256 };
//		int[] ev = { 11, 17, 256 }; // expected value
//		int[] result = CeasarCracker.sumOfArrangements(key, arreAscii);
//
//		assertTrue(Arrays.equals(ev, result));
//	}
//
//	@Test
//	public void testTwoSumOfArrangements() {
//		// check if the sum of two arrays is correct
//		int[] key = { 1, 0 };
//		int[] arreAscii = { 35, 23, 256, 1 };
//		int[] ev = { 36, 23, 2, 1 }; // expected value
//		int[] result = CeasarCracker.sumOfArrangements(key, arreAscii);
//
//		assertTrue(Arrays.equals(ev, result));
//	}
//
//	@Test
//	public void testFourSumOfArrangements() {
//		// check if the sum of two arrays is correct
//		int[] key = { 1, 256 };
//		int[] arreAscii = { 35, 256 };
//		int[] ev = { 36, 257 }; // expected value
//		int[] result = CeasarCracker.sumOfArrangements(key, arreAscii);
//
//		assertTrue(Arrays.equals(ev, result));
//	}
	
	@Test
	public void testMoreCoplexSumOfArrangements() {
		// check if the sum of two arrays is correct
		int[] key = { 1, 2, 1 };
		String s = "hola";
		char[] ev = { 'i', 'q', 'm', 'b'}; // expected value
		char[] result = CeasarCracker.sumOfArrangements(key, s);

		assertTrue(Arrays.equals(ev, result));
	}
//
//	@Test
//	public void testOneSubtractionOfArrangements(){  
//		int[] key = {1,0};
//		int[] arreAscii = {105, 111, 109, 97};
//		int[] ev = {104, 111, 108, 97}; // expected value
//		int[] result = CeasarCracker.subtractionOfArrangements(key, arreAscii);
//		
//		assertTrue(Arrays.equals(ev, result));
//	}
//	
//	
//	@Test
//	public void testTwoSubtractionOfArrangements(){  
//		int[] key = {1,0};
//		int[] arreAscii = {0,2};
//		int[] ev = {256,2}; // expected value
//		int[] result = CeasarCracker.subtractionOfArrangements(key, arreAscii);
//		
//		assertTrue(Arrays.equals(ev, result));
//	}

	@Test
	public void testArrayToString() {
		char[] s = { 'h', 'o', 'l', 'a' };
		String string = "hola"; // expected value
		assertEquals(string, CeasarCracker.arrayToString(s));
	}

	@Test
	public void testLessSimpleFoundKey() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setEncryptedMessage("iomafasdfa");
		cracker.setMessageWord("holae");
		cracker.setPasswordLength(5);
		int[] key = { 1, 0 }; // expected value
		int[] result = cracker.foundKey();
		assertTrue(Arrays.equals(key, result));
	}

	@Test
	public void testMoreComplexFoundKey() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setEncryptedMessage("iombasdf");
		cracker.setMessageWord("hola");
		cracker.setPasswordLength(5);
		int[] key = { 1, 0, 1 }; // expected value
		int[] result = cracker.foundKey();
		assertTrue(Arrays.equals(key, result));
	}
	
}
