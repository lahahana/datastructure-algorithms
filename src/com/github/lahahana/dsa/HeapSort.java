/**
* @author lahahana
*/
package com.github.lahahana.dsa;

import java.util.Arrays;

public class HeapSort {

	public static void main(String[] args) {
		int[] arr = { 10, 8, 4, 6, 7, 2, 1, 6, 9, 12, 7, 15 };
		HeapSort.heapSort(arr);
		System.out.println(Arrays.toString(arr));
	}

	public static void heapSort(int[] arr) {
		int size = arr.length;
		buildHeap(arr, size);
		for (int i = size - 1; i > 0; i--) {// i为无序区的长度，经过如下两步，长度递减
			swap(arr, i, 0);
			adjustHeap(arr, 0, i);
		}
	}

	private static void buildHeap(int[] arr, int size) {
		for (int i = (size - 1) / 2; i >= 0; i--) {
			adjustHeap(arr, i, size);
		}
	}

	private static void adjustHeap(int[] a, int i, int size) {
		int leftChildPos = 2 * i + 1;
		int rightChildPos = 2 * i + 2;
		int maxPos = i;
		if (i <= (size - 1) / 2) {
			if (leftChildPos < size && a[maxPos] < a[leftChildPos])
				maxPos = leftChildPos;
			if (rightChildPos < size && a[maxPos] < a[rightChildPos])
				maxPos = rightChildPos;
			if (maxPos != i) {
				swap(a, maxPos, i);
				adjustHeap(a, maxPos, size);
			}
		}
	}

	private static void swap(int[] arr, int i, int j) {
		int temp = arr[j];
		arr[j] = arr[i];
		arr[i] = temp;
	}
}
