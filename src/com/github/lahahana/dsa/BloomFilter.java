package com.github.lahahana.dsa;

import static org.junit.Assert.assertTrue;

/**
* Simple Hash BloomFilter
* @author lahahana
*/
public class BloomFilter {

	private static int[] seeds = {3,7,11,19,23,29,43};
	
	private int capacity;
	
	private BitMap bitMap;
	
	public BloomFilter(int capacity) {
		super();
		this.capacity = capacity;
		this.bitMap = new BitMap(capacity);
	}

	public void insert(String str) {
		for (int seed : seeds) {
			int index = xhash(str, seed);
			bitMap.insert(index);
		}
	}
	
	public boolean isExists(String str) {
		boolean isExist = true;
		for (int seed : seeds) {
			int index = xhash(str, seed);
			if(!bitMap.isExists(index)) {
				isExist = false;
				break;
			}
		}
		return isExist;
	}
	
	private int xhash(String str, int seed) {
		int hashCode =0;
		for (int i =0; i < str.length(); i++) 
			hashCode = seed * hashCode + str.charAt(i);
		return (capacity -1) & hashCode;
	}
	
	public static void main(String []args) {
		BloomFilter bloomFilter = new BloomFilter(1000000);
		bloomFilter.insert("apple pie");
		bloomFilter.insert("game of thrones");
		bloomFilter.insert("the decue");
		bloomFilter.insert("narcos");
		assertTrue(bloomFilter.isExists("narcos"));
		assertTrue(bloomFilter.isExists("the decues"));
	}
}
