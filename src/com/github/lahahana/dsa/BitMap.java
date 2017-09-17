
package com.github.lahahana.dsa;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
* BitMap Strucutre 
* 
* @author lahahana
*/
public class BitMap {
	
	private int[] arr;

	public BitMap(int capacity) {
		super();
		arr = new int[(capacity >>> 5) + 1];
	}
	
	public void insert(int number) {
		int index  = number >>> 5;
		int offset = number % 32 - 1;
		arr[index] = arr[index] | (1 << offset);
	}
	
	public boolean isExists(int number) {
		int index  = number >>> 5;
		int offset = number % 32 - 1;
		return (arr[index] & (1 << offset)) == (1 << offset);
	}
	
	public static void main(String []args) {
		BitMap bm = new BitMap(1000000);
		bm.insert(1);
		bm.insert(3);
		bm.insert(3);
		bm.insert(954223);
		assertTrue(bm.isExists(954223));
		assertFalse(bm.isExists(0));
		assertTrue(bm.isExists(1));
		assertFalse(bm.isExists(2));
		assertFalse(bm.isExists(1000000));
	}
	
}
