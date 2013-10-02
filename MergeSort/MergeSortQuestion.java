// STUDENT_NAME: Michael Ho
// STUDENT_ID: 260532097

import java.util.*;
import java.io.*;


// No need to change anything in this class
class MergeSortQuestion {

    // merges sorted subarrays A[start...firstThird], A[firstThird+1,secondThird], and A[secondThird+1,stop]
    public static void mergeThreeWay(int A[], int start, int firstThird, int secondThird, int stop) {
    	int indexStart = start;
    	int indexFirstThird = firstThird + 1;
    	int indexSecondThird = secondThird + 1;
		int tmp[] = new int[A.length];
		int tmpIndex = start;

		while (tempIndex <= stop){

			// Value of indexStart is smallest
			if ((indexFirstThird > secondThird && indexSecondThird > stop) || 
			  (indexStart <= firstThird && A[indexStart] < A[indexFirstThird] && A[indexStart] < A[indexSecondThird]) ){
			  	temp[tmpIndex] = A[indexStart];
			  	indexStart++;
			} 
			// Value of indexFirstThird is smallest	
			else if ((indexStart > indexFirstThird && indexSecondThird > stop) ||
				(indexFirstThird <= secondThird && A[indexFirstThird] < A[indexStart] && A[indexFirstThird] < A[indexSecondThird])){
				temp[tempIndex] = A[indexFirstThird];
				indexFirstThird++;
			}
			// Value of indexSecondThird is smallest
			else {
				temp[tempIndex] = A[indexSecondThird];
				indexSecondThird++;
			}

			tempIndex++;
		}
    }

    // sorts A[start...stop]
    public static void mergeSortThreeWay(int A[], int start, int stop) {
		
    }

    
    public static void main (String args[]) throws Exception {
       
		int myArray[] = {8,3,5,7,9,2,3,5,5,6}; // an example array to be sorted. You'll need to test your code with many cases, to be sure it works.

		mergeSortThreeWay(myArray,0,myArray.length-1);

		System.out.println("Sorted array is:\n");
		for (int i=0;i<myArray.length;i++) {
		    System.out.println(myArray[i]+" ");
		}
    }
}
	
