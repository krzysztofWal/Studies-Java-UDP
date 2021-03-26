package buffer;

import java.lang.reflect.Array;

public class buf<T> {
	private T[] tab;
	private T[] cast(Object tab) { return (T[]) tab; }
	
	@SuppressWarnings("unchecked") /* unchecked cast from Object to T type*/
	public buf(Object[] t) {
		this.tab = (T[]) new Object[t.length];
		/* przepisanie jednej tablicy do drugiej */
		for (int i = 0; i < t.length; i++) {
			this.tab[i] = (T) t[i];
		}
	}
	
	
	
	public static void main() {
		Double[] tablica = {0.4, 0.7, 1.3}; 
		buf<Double[]> bufor = new buf(tablica);
	}
	
}
