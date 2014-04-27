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
		assertEquals("default pass length is one", 1,
				cracker.getPasswordLength());
		assertEquals("message word is empty", "", cracker.getMessageWord());
		assertEquals("encrypted message is empty", "",
				cracker.getEncryptedMessage());
	}

	@Test
	public void testConstructorWithWordAndMessage() {
		CeasarCracker cracker = new CeasarCracker("encrypted", "word");
		assertEquals("default pass length is one", 1,
				cracker.getPasswordLength());
		assertEquals("message word is correct", "word",
				cracker.getMessageWord());
		assertEquals("encrypted message is correct", "encrypted",
				cracker.getEncryptedMessage());
	}

	@Test
	public void testSettingEncryptedMessage() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setEncryptedMessage("abcd");
		assertEquals("encrypted message is correctly set", "abcd",
				cracker.getEncryptedMessage());
	}

	@Test
	public void testSettingKnownWord() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setMessageWord("abcd");
		assertEquals("known word is correctly set", "abcd",
				cracker.getMessageWord());
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
	
	@Test
	public void testEvenMoreComplexKey() {
		int[] key = { 150, 256, 100, 200 };
		String output = CeasarCracker.encode("iťmŬp", key);
		String output2 = CeasarCracker.decode(output, key);
		assertEquals("iťmŬp", output2);
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
		cracker.setEncryptedMessage(CeasarCracker.encode(
				"hola que tal como andas", key));
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
		cracker.setEncryptedMessage(CeasarCracker.encode(
				"hola que tal como andas", key));
		cracker.setMessageWord("hola");
		cracker.setPasswordLength(4);
		boolean isDecrypted = cracker.bruteForceDecrypt();
		int[] result = cracker.foundKey();
		assertTrue("message decrypted", isDecrypted);
		assertTrue("encoding key is correct", Arrays.equals(key, result));
	}

	@Test
	public void testSimpleSumOfArrangements() {
		// check if the sum of two arrays is correct
		int[] key = { 1, 2, 0 };
		String s = "home";
		char[] ev = { 'i', 'q', 'm', 'f' }; // expected value
		char[] result = CeasarCracker.sumOfArrangements(key, s);

		assertTrue(Arrays.equals(ev, result));
	}

	@Test
	public void testComplexSumOfArrangements() {
		// check if the sum of two arrays is correct
		int[] key = { 1, 0 };
		String s = "arreAscii";
		char[] ev = { 'b', 'r', 's', 'e', 'B', 's', 'd', 'i', 'j' }; // expected value
		char[] result = CeasarCracker.sumOfArrangements(key, s);

		assertTrue(Arrays.equals(ev, result));
	}

	@Test
	public void testMoreComplexSumOfArrangements() {
		// check if the sum of two arrays is correct
		int[] key = { 1, 256 };
		String s = "hello";
		char[] ev = { 'i', 'ť', 'm', 'Ŭ', 'p' }; // expected value
		char[] result = CeasarCracker.sumOfArrangements(key, s);

		assertTrue(Arrays.equals(ev, result));
	}

	@Test
	public void testSimpleSubOfArrangements() {
		int[] key = { 1, 2, 0 };
		String s = "iqmf";
		char[] ev = { 'h','o','m','e' }; // expected value
		char[] result = CeasarCracker.subOfArrangements(key, s);

		assertTrue(Arrays.equals(ev, result));
	}

	@Test
	public void testComplexSubOfArrangements() {
		int[] key = { 1, 0 };
		String s = "brseBsdij";
		char[] ev = { 'a', 'r', 'r', 'e', 'A', 's', 'c', 'i', 'i' }; // expected value
		char[] result = CeasarCracker.subOfArrangements(key, s);

		assertTrue(Arrays.equals(ev, result));
	}
	
	@Test
	public void testMoreComplexSubOfArrangements() {
		// check if the sum of two arrays is correct
		int[] key = { 1, 256 };
		String s = "iťmŬp";
		char[] ev = { 'h', 'e', 'l', 'l', 'o' }; // expected value
		char[] result = CeasarCracker.subOfArrangements(key, s);

		assertTrue(Arrays.equals(ev, result));
	}

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
