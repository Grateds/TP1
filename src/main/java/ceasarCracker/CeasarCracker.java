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
	
	private char[] mAsCharArray;
	
	/**
	 * Default constructor. Sets both known message word and encrypted message
	 * to "". Maximum password length to be tried is set to 1.
	 */
	public CeasarCracker() {
		this.m = "";
		this.w = "";
		this.k = 1;
	}

	/**
	 * Constructor that receives both known message word and encrypted message.
	 * Maximum password length to be tried is set to 1.
	 */
	public CeasarCracker(String encryptedMessage, String word) {
		this.k = 1; // default pass length
		this.m = encryptedMessage;
		this.w = word;
		this.mAsCharArray = m.toCharArray();
	}

	/**
	 * Returns the maximum length to be tried in password cracking. For
	 * instance, if such length is 1, then all passwords of length 1 (values
	 * from 0 to 255) will be tried as passwords for cracking.
	 * 
	 * @return the maximum length to be tried for password cracking.
	 */
	public int getPasswordLength() {
		return this.k;
	}

	/**
	 * Sets the maximum length of the password to be tried for deciphering the
	 * encrypted message.
	 * 
	 * @param length 
	 * 			is the new maximum length for the passwords to try for cracking.
	 */
	public void setPasswordLength(int length) {
		this.k = length;
	}

	/**
	 * Returns the known word of the message previous to encryption. This word
	 * is used to test whether a given password is able to unencrypt the
	 * encrypted message.
	 * 
	 * @return the known word of the unencrypted message.
	 */
	public String getMessageWord() {
		return this.w;
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
		if(word == null) throw new IllegalArgumentException("The word must not be null!");
		this.w = word;
	}

	/**
	 * Returns the encrypted message, that to be cracked.
	 * 
	 * @return the encrypted message.
	 */
	public String getEncryptedMessage() {
		return this.m;
	}

	/**
	 * Sets the encrypted message, to be "decrypted" by brute force.
	 * 
	 * @param message 
	 * 				is the encrypted message to set for the cracker.
	 */
	public void setEncryptedMessage(String message) {
		if(message == null) throw new IllegalArgumentException("The message must not be null!");
		this.m = message;
		this.mAsCharArray = m.toCharArray();
	}

	/**
	 * 
	 * @param key
	 * @param wordAsCharArray
	 * 
	 * @return res
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static char[] sumOfArrangements(int[] key, String word) {
		char[] res = word.toCharArray();
		IterableCircularQueue<Integer> q = new IterableCircularQueue(key.length);
		for (int i = 0; i < key.length; i++)
			q.enqueue(key[i]);
		Iterator it = q.iterator();
		for (int i = 0; i < res.length; i++) {
			int N = (Integer) it.next();
			for (int j = 0; j < N; j++) 
				res[i]++; 
		}
		return res;
	}

	/**
	 * 
	 * @param key
	 * @param wordAsCharArray
	 * @return res
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static char[] subOfArrangements(int[] key, String word) {
		char[] res = word.toCharArray();
		IterableCircularQueue<Integer> q = new IterableCircularQueue(key.length);
		for (int i = 0; i < key.length; i++)
			q.enqueue(key[i]);
		Iterator it = q.iterator();
		for (int i = 0; i < res.length; i++) {
			int N = (Integer) it.next();
			for (int j = 0; j < N; j++) 
				res[i]--; 
		}
		return res;
	}

	/**
	 * 
	 * @param word
	 * @return String
	 */
	public static String arrayToString(char[] word) {
		String s = "";
		for (int i = 0; i < word.length; i++) 
			s = s + word[i];
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
	public static String encode(String message, int[] key) {
		if(message == null) throw new IllegalArgumentException("The message must not be null!");
		if(key == null) throw new IllegalArgumentException("The key must not be null!");
		if(key.length < 1) throw new IllegalArgumentException("The key's length must be greater than cero!");
		char[] res = new char[message.length()];
		res = sumOfArrangements(key, message);
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
	 * @throws UnsupportedEncodingException 
	 */
	public static String decode(String message, int[] key) {
		if(message == null) throw new IllegalArgumentException("The message must not be null!");
		if(key == null) throw new IllegalArgumentException("The key must not be null!");
		if(key.length < 1) throw new IllegalArgumentException("The key's length must be greater than cero!");
		char[] res = new char[message.length()];
		res = subOfArrangements(key, message);
		return arrayToString(res);
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
	public boolean bruteForceDecrypt() {
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
	public int[] foundKey() {
		Key key = new Key();
		char[] res = new char[this.w.length()];
		boolean foundKey = false;
		for (int i = 1; i <=this.k; i++) {
			key = new Key(i);
			while(!key.isComplete() && !foundKey){
				res = sumOfArrangements(key.get(), this.w);
				if(contains(this.mAsCharArray,res))
					foundKey = true;	
				else key.inc();
			}
			if(foundKey) break;
		}
		if(foundKey)return key.get();
		else return null;
	}
	
	public static boolean contains(char[] array, char[]sub){
        if(array == null || sub == null) throw new IllegalArgumentException("The arrays must not be null!");
        if(array.length<sub.length) return false;
        boolean itContains = false;
        int i = 0;
        int counter;
        while (i < array.length && !itContains) {
            counter = 0;
            for (int j = 0; j < sub.length; j++) {
                if(i+j >= array.length || array[i+j] != sub[j]) break;
                else counter++;
            }
            if(counter == sub.length) itContains = true;
            i++;
        }
        return itContains;
    }
}
