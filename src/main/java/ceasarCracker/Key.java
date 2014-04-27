package main.java.ceasarCracker;

public class Key {

	private int[] key;
	private int N;
	private final int BOUND = 256;

	public Key() {
		key = null;
	}

	public Key(int x) {
		key = new int[x];
		N = x;
	}

	public int[] get() {
		return key;
	}
	
	public void set(int[] a){
		for (int i = 0; i < a.length; i++) 
			key[i] = a[i];
	}
	
	public boolean isComplete() {
		int b = 1;
		for (int i = 0; i < N; i++) {
			if (key[i] != BOUND) {
				b = b * 0;
				break;
			}
		}
		return b == 1;
	}

	public void inc() {
		inc(key, 0, N - 1);
	}
	
	public void inc(int[] a, int begin, int end) {
		if (begin == end)
			a[end] += 1;
		else {
			if(a[end] != BOUND) a[end] += 1;
			else{
				a[end] = 0;
				inc(a,begin,end-1);
			}
		}
	}
}
