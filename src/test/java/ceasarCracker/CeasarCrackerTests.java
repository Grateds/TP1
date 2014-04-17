package test.java.ceasarCracker;
import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import main.java.ceasarCracker.CeasarCracker;

import org.junit.Test;

/**
 * Tests for class CeasarCracker. Contains tests for all (public) routines of this class.
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
		assertEquals("message word is correct", "word", cracker.getMessageWord());
		assertEquals("encrypted message is correct", "encrypted", cracker.getEncryptedMessage());
	}

	@Test
	public void testSettingEncryptedMessage() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setEncryptedMessage("abcd");
		assertEquals("encrypted message is correctly set", "abcd", cracker.getEncryptedMessage());
	}

	@Test
	public void testSettingKnownWord() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setMessageWord("abcd");
		assertEquals("known word is correctly set", "abcd", cracker.getMessageWord());
	}
	
	@Test
	public void testSimpleKey() {
		int[] key = {1};
		String output = CeasarCracker.encode("hola", key);
		assertEquals("ipmb", output);
		String output2 = CeasarCracker.decode("ipmb", key);
		assertEquals("hola",output2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidMessageEncode() {
		int[] key = {1};
		CeasarCracker.encode(null, key);
		// must break!
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidKeyEncode() {
		int[] key = null;
		CeasarCracker.encode("hola", key);
		// must break!
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidEmptyKeyEncode() {
		int[] key = {};
		CeasarCracker.encode("hola", key);
		// must break!
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidMessageDecode() {
		int[] key = {1};
		CeasarCracker.decode(null, key);
		// must break!
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidKeyDecode() {
		int[] key = null;
		CeasarCracker.decode("hola", key);
		// must break!
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidEmptyKeyDecode() {
		int[] key = {};
		CeasarCracker.decode("hola", key);
		// must break!
	}

	@Test
	public void testComplexKey() {
		int[] key = {1,0};
		String output = CeasarCracker.encode("hola", key);
		assertEquals("ioma", output);
		String output2 = CeasarCracker.decode("ioma", key);
		assertEquals("hola", output2);
	}
	
	@Test
	public void testMoreComplexKey() {
		int[] key = {20,150,101};
		String output = CeasarCracker.encode("hola que tal como andas", key);
		String output2 = CeasarCracker.decode(output,key);
		assertEquals("hola que tal como andas", output2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidBruteForce() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setEncryptedMessage(null);
		// doesn't even reach the statement below. Statement above fails!
		cracker.bruteForceDecrypt();
		// must crash!
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidBruteForce2() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setMessageWord(null);
		// doesn't even reach the statement below. Statement above fails!
		cracker.bruteForceDecrypt();
		// must crash!
	}

	@Test(expected=IllegalStateException.class)
	public void testInvalidBruteForce3() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setMessageWord("hola");
		cracker.setEncryptedMessage("h");
		cracker.bruteForceDecrypt();
		// must crash!
	}

	@Test(expected=IllegalStateException.class)
	public void testInvalidBruteForce4() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setPasswordLength(0);
		cracker.bruteForceDecrypt();
		// must crash!
	}
	
	@Test
	public void testSimpleBruteForce() {
		CeasarCracker cracker = new CeasarCracker();
		cracker.setEncryptedMessage("ipmbfasdfa");
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
	public void testFailedComplexBruteForce() throws UnsupportedEncodingException {
		CeasarCracker cracker = new CeasarCracker();
		int[] key = {1,100,101};
		cracker.setEncryptedMessage(CeasarCracker.encode("hola que tal", key));
		cracker.setMessageWord("hola");
		boolean isDecrypted = cracker.bruteForceDecrypt();
		// message not decrypted. 1-valued keys cannot decode message 
		assertFalse("message decrypted", isDecrypted);
		assertTrue("key not found", cracker.foundKey()==null);
	}

	@Test
	public void testComplexBruteForce() throws UnsupportedEncodingException {
		CeasarCracker cracker = new CeasarCracker();
		int[] key = {1,100,101};
		cracker.setEncryptedMessage(CeasarCracker.encode("hola que tal", key));
		cracker.setMessageWord("hola");
		cracker.setPasswordLength(3);
		boolean isDecrypted = cracker.bruteForceDecrypt();
		assertTrue("message decrypted", isDecrypted);
		assertEquals("key found", Arrays.toString(cracker.foundKey()), Arrays.toString(key));
	}

	@Test
	public void testMoreComplexBruteForce() throws UnsupportedEncodingException {
		CeasarCracker cracker = new CeasarCracker();
		int[] key = {20,100,101};
		cracker.setEncryptedMessage(CeasarCracker.encode("hola que tal como andas", key));
		cracker.setMessageWord("hola");
		cracker.setPasswordLength(3);
		boolean isDecrypted = cracker.bruteForceDecrypt();
		assertTrue("message decrypted", isDecrypted);
		assertEquals("encoding key is correct", Arrays.toString(key), Arrays.toString(cracker.foundKey()));
	}

	@Test
	public void testEvenMoreComplexBruteForce() throws UnsupportedEncodingException {
		// may be slow (more than 3 minutes on a modern PC)
		// comment the @Test above for just running the other "quicker" tests.
		CeasarCracker cracker = new CeasarCracker();
		int[] key = {3,23,151,103};
		cracker.setEncryptedMessage(CeasarCracker.encode("hola que tal como andas", key));
		cracker.setMessageWord("hola");
		cracker.setPasswordLength(4);
		boolean isDecrypted = cracker.bruteForceDecrypt();
		assertTrue("message decrypted", isDecrypted);
		assertEquals("encoding key is correct", Arrays.toString(key), Arrays.toString(cracker.foundKey()));
	}
	
}
