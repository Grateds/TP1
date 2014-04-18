package main.java.ceasarCracker;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;


/**
 * This class is a brute force cracker for keyed Ceasar encoding.
 * 
 * @author Nazareno Aguirre
 * @version 0.1 12/4/2014
 */

public class CeasarCracker {
	private String m; // encryptedMessage
	private String w; // word
	private int k; // k

	/**
	 * Default constructor. Sets both known message word and encrypted message
	 * to "". Maximum password length to be tried is set to 1.
	 */
	public CeasarCracker() {
		m = "";
		w = "";
		k = 1;
	}

	/**
	 * Constructor that receives both known message word and encrypted message.
	 * Maximum password length to be tried is set to 1.
	 */
	public CeasarCracker(String encryptedMessage, String word) {
		k = 1; // default pass length
		m = encryptedMessage;
		w = word;
	}

	/**
	 * Returns the maximum length to be tried in password cracking. For
	 * instance, if such length is 1, then all passwords of length 1 (values
	 * from 0 to 255) will be tried as passwords for cracking.
	 * 
	 * @return the maximum length to be tried for password cracking.
	 */
	public int getPasswordLength() {
		return k;
	}

	/**
	 * Sets the maximum length of the password to be tried for deciphering the
	 * encrypted message.
	 * 
	 * @param length 
	 * 			is the new maximum length for the passwords to try for cracking.
	 */
	public void setPasswordLength(int length) {
		k = length;
	}

	/**
	 * Returns the known word of the message previous to encryption. This word
	 * is used to test whether a given password is able to unencrypt the
	 * encrypted message.
	 * 
	 * @return the known word of the unencrypted message.
	 */
	public String getMessageWord() {
		return w;
	}

	/**
	 * Sets the known word from the unencrypted message. A valid decryption will
	 * be one that, applied to the encrypted message leads to an unencrypted
	 * message containing the known word.
	 * 
	 * @param word 
	 * 			is the known word from the unencrypted message.
	 */
	public void setMessageWord(String word) {
		w = word;
	}

	/**
	 * Returns the encrypted message, that to be cracked.
	 * 
	 * @return the encrypted message.
	 */
	public String getEncryptedMessage() {
		return m;
	}

	/**
	 * Sets the encrypted message, to be "decrypted" by brute force.
	 * 
	 * @param message 
	 * 				is the encrypted message to set for the cracker.
	 */
	public void setEncryptedMessage(String message) {
		m = message;
	}

	/**
	 * @param
	 * @param
	 * 
	 * @return key + wordToAscii
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int[] sumOfArrangements(int[] key, int[] wordToAscii) {
		int[] res = new int[wordToAscii.length];
		IterableCircularQueue<Integer> q = new IterableCircularQueue(key.length);
		for (int i = 0; i < key.length; i++)
			q.enqueue(key[i]);
		Iterator it = q.iterator();
		for (int i = 0; i < wordToAscii.length; i++) {
			res[i] += wordToAscii[i] + (Integer) it.next();
			if (res[i] > 256)
				res[i] = res[i] - 255;
		}
		return res;
	}

	/**
	 * Converts word to an array of ASCII numbers
	 * 
	 * @param String word 
	 * 					is the known word from the unencrypted message.
	 * @return int[]
	 * @throws UnsupportedEncodingException
	 */
	public static int[] stringToArray(String word) throws UnsupportedEncodingException {
		String s = word;
		byte[] b = s.getBytes("ASCII");
		int[] arre = new int[b.length];
		for (int i = 0; i < b.length; i++) {
			arre[i] = b[i];
		}
		return arre;
	}
	
	/**
	 * 
	 * @param word
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String arrayToString(int[] word) throws UnsupportedEncodingException {
		String s = "";
		for (int i = 0; i < word.length; i++) {
			s = s + Character.toString ((char) word[i]);
		}	
		return s;
	}
	
	/**
	 * Encodes message with a given key.
	 * 
	 * @param message
	 *            is the message to be encoded.
	 * @param key
	 *            is the key used for encoding, given as an array of integer
	 *            values (from 0 to 255).
	 * @return the message encoded with the provided key.
	 * @throws UnsupportedEncodingException 
	 */
	public static String encode(String message, int[] key) throws UnsupportedEncodingException {
		if(message == null) throw new IllegalArgumentException("The message must not be null!");
		if(key == null) throw new IllegalArgumentException("The key must not be null!");
		if(key.length < 1) throw new IllegalArgumentException("The key's length must be greater than cero!");
		int[] res = new int[message.length()];
		int[] messageBytes = stringToArray(message);
		res = sumOfArrangements(key, messageBytes);
		return arrayToString(res);
	}

	/**
	 * Decodes message with a given key.
	 * 
	 * @param message
	 *            is the message to be decoded.
	 * @param key
	 *            is the key used for decoding, given as an array of integer
	 *            values (from 0 to 255).
	 * @return the message decoded with the provided key.
	 */
	public static String decode(String message, int[] key) {
		// TODO: Implement this method.
		return null;
	}

	/**
	 * Attempts to decode encrypted message with the given key. Brute force
	 * decryption tries to find a key of at most this.passwordLength values
	 * (each from 0 to 255) such that the decryption of the encrypted message
	 * leads to a decrypted text that contaitestMoreComplexFoundKeyns this.messageWord.
	 * 
	 * @return true iff brute force decryption succeeded.
	 * @throws UnsupportedEncodingException 
	 */
	public boolean bruteForceDecrypt() throws UnsupportedEncodingException {
		if(m == null) throw new IllegalArgumentException("The encrypted message must not be null!");
		if(w == null) throw new IllegalArgumentException("The message word must not be null!");
		if(w.length()>m.length()) throw new IllegalStateException("The message's length must be greater-equal than word's length");
		if(k < 1) throw new IllegalStateException("The password's length must be greater than cero");
		return this.foundKey() != null;
	}

	/**
	 * Returns the key found by the last brute force decryption. Method returns
	 * null if brute force decryption was never executed, or if key was not
	 * found.
	 * 
	 * @return key found by brute force decryption (null if not found /
	 * decryption not executed)
	 * @throws UnsupportedEncodingException
	 */
	public int[] foundKey() throws UnsupportedEncodingException {
		Key key = new Key();
		int[] sum = new int[w.length()];
		String matcher;
		boolean foundKey = false;
		int[] wordBytes = stringToArray(w);
		for (int i = 1; i <=k; i++) {
			key = new Key(i);
			while(!key.isComplete() && !foundKey){
				sum = sumOfArrangements(key.get(), wordBytes);
				matcher = arrayToString(sum);
				if(m.contains(matcher))
					foundKey = true;	
				else key.inc();
			}
			if(foundKey) break;
		}
		if(foundKey)return key.get();
		else return null;
	}
}
