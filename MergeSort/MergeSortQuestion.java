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
		int temp[] = new int[A.length];
		int tempIndex = start;

		while (tempIndex <= stop){

			// Value of indexStart is smallest
			if (indexStart <= firstThird 
				&& (indexFirstThird > secondThird || (indexFirstThird <= secondThird && A[indexStart] <= A[indexFirstThird])) 
				&& (indexSecondThird > stop || (indexSecondThird <= stop && A[indexStart] <= A[indexSecondThird]))) 
			{
			  	temp[tempIndex] = A[indexStart];
			  	indexStart++;
			}  
		
			// Value of indexFirstThird is smallest	
			else if ((indexFirstThird <= secondThird) 
				&& (indexStart > firstThird || (indexStart <= firstThird && A[indexFirstThird] <= A[indexStart]))
				&& (indexSecondThird > stop || (indexSecondThird <= stop && A[indexFirstThird] <= A[indexSecondThird])))

			{
				temp[tempIndex] = A[indexFirstThird];
				indexFirstThird++;
			}
			// Value of indexSecondThird is smallest
			else if (indexSecondThird <= stop)
			{
				temp[tempIndex] = A[indexSecondThird];
				indexSecondThird++;
			}

			tempIndex++;
		}

		// Copy back the temp array into the original array
		for (int i = start; i <= (stop); i++){
			A[i] = temp[i];
		}

    }

    // sorts A[start...stop]
    public static void mergeSortThreeWay(int A[], int start, int stop) {
		if (start < stop){
			int firstThird = start + (stop-start)/3;
			int secondThird = start + 2*(stop-start)/3;
			mergeSortThreeWay(A, start, firstThird);
			mergeSortThreeWay(A, firstThird+1, secondThird);
			mergeSortThreeWay(A, secondThird+1, stop);
			mergeThreeWay(A, start, firstThird, secondThird, stop);
		}
    }

    
    public static void main (String args[]) throws Exception {
       
		int myArray[] = {8,3,5,7,9,2,3,5,5,6}; // an example array to be sorted. You'll need to test your code with many cases, to be sure it works.

		mergeSortThreeWay(myArray,0,myArray.length-1);

		System.out.println("Sorted array is:\n");
		for (int i=0;i<myArray.length;i++) {
		    System.out.print(myArray[i]+" ");
		}
    }
}
	
