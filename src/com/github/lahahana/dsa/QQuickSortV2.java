/**
* @author lahahana
*/
package com.github.lahahana.dsa;

public class QQuickSortV2 {
	
	private static final int INSERT_THRESHOLD = 8;
	
	public static void main(String []args) {
		int size = 10000000;
		int[] arr = new int[size];
		for(int i = 0; i < size; i++) {
			arr[i] = (int) (Math.random() * 10000 + 1);
//			arr[i] = 10;
		}
//		int[] arr = {2,3,3,2,6,8,4,1,1,1,1,5,1,1,11,1,1,1,1,11,};
		long s = System.currentTimeMillis();
		qsort(arr);
		System.out.println("Cost:" + (System.currentTimeMillis() - s));
	}
	
	public static void qsort(int[] arr) {
		qsort(arr, 0, arr.length - 1);
	}
	
	public static void qsort(int[] arr, int low, final int high) {
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
			int left = low, right = high;
			int leftRepeatIndex = low, rightRepeatIndex= high;
			int leftRepeatLen = 0,rightRepeatLen = 0;
			final int pivot = calcPivot(arr, low, high);
			while(left < right) {
				while(left < right && arr[right] >= pivot) {
					if(arr[right] == pivot) {
						swap(arr, right, rightRepeatIndex);
						rightRepeatIndex--;
						rightRepeatLen++;
					}
					right--;
				}
				arr[left] = arr[right];
				while(left < right && arr[left] <= pivot) {
					if(arr[left] == pivot) {
						swap(arr, left, leftRepeatIndex);
						leftRepeatIndex++;
						leftRepeatLen++;
					}
					left++;
				}
				arr[right] = arr[left];
			}
			arr[left] = pivot;
			
			//Move element which equals to pivot around pivotIndex to enhance repeat array performence
			int pivotIndex = left;
			int i = pivotIndex - 1, j = low;
			while(j < leftRepeatIndex && arr[i] != pivot) {
				swap(arr, j, i);
				j++;
				i--;
			}
			i = pivotIndex + 1;
			j = high;
			while(j > rightRepeatIndex && arr[i] != pivot) {
				swap(arr, j, i);
				j--;
				i++;
			}
			qsort(arr, low, pivotIndex - 1 - leftRepeatLen);
			low = pivotIndex + 1 + rightRepeatLen;
		}

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
