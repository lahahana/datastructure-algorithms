
package com.github.lahahana.dsa;

/**
* Performence enhence for traditional quick sort 
* @author lahahana
*/
public class QQuickSort {
	
	private static final int INSERT_THRESHOLD = 8;
	
	public static void main(String []args) {
		int size = 1000000;
		int[] arr = new int[size];
		for(int i = 0; i < size; i++) {
			arr[i] = (int) (Math.random() * 10000 + 1);
		}
		long s = System.currentTimeMillis();
		qsort(arr);
		System.out.print("Cost:" + (System.currentTimeMillis() - s));
	}
	
	public static void qsort(int[] arr) {
		qsort(arr, 0, arr.length - 1);
	}
	
	public static void qsort(int[] arr,int low, int high) {
		//Use Insert Sort when size less than INSERT_THRESHOLD
		if(high-low < INSERT_THRESHOLD) {
			for(int i = low; i <= high; i++) {
				for(int j = i; j > low && arr[j-1] > arr[j]; j--)
					swap(arr, j-1, j);
			}
			return;
		}
		
		//Tail Recursion Enhance, although compiler will do this job
		while(low < high) {
			int pivot = partition(arr, low, high);
			qsort(arr, low, pivot - 1);
			low = pivot + 1;
		}

	}
	
	private static int partition(int[] arr, int low, int high) {
		int pivot = calcPivot(arr, low, high);
		int left = low, right = high;
		while(left < right) {
			while(left < right && arr[right] >= pivot) {
				right--;
			}
			arr[left] = arr[right];
			while(left < right && arr[left] <= pivot) {
				left++;
			}
			arr[right] = arr[left];
		}
		arr[left] = pivot;
		return left;
	}
	
	private static int calcPivot(int[] arr, int low, int high) {
		int media  = (low + high) >>> 1;
		if(arr[media] > arr[high]) {
			swap(arr, media, high);
		}
		if(arr[low] > arr[high]) {
			swap(arr, low, high);
		}
		if(arr[media] > arr[low]) {
			swap(arr, low, media);
		}
		return arr[low];
	}
	
	private static void swap(int[] arr, int index1, int index2) {
		int tmp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = tmp;
	}
}
