#include <stdio.h>
#include <stdlib.h>
#include <string.h>

const int ENCRYPTION_KEY = 3;

/* Encryption key integer is only 3 because my compiler produces weird undecryptible characters after ascii value 127 (extended ascii table)
Furthermore, only the characters with ascii values >31 are encrypted/decrypted in order to avoid weird behavior of shaped characters such as \n \0
*/

void encrypt(char array[50]){
	int i;
	for (i=0; i < 50; i++){
		if (array[i]>31){
			int temp = (array[i]+ENCRYPTION_KEY)%255;
			array[i] = temp;
		}
	}
}

void decrypt(char array[50]){
	int i;
	for (i=0; i < 50; i++){
		if (array[i]>31){
			int temp = (array[i]-ENCRYPTION_KEY)%255;
			array[i] = temp;
		}
	}
}

/* The following two methods are only used for Question 1 for safe purpose */

void encryptLong(char array[256]){
	int i;
	for (i=0; i < 256; i++){
		if (array[i]>31){
			int temp = (array[i]+ENCRYPTION_KEY)%255;
			array[i] = temp;
		}
	}
}

void decryptLong(char array[256]){
	int i;
	for (i=0; i < 256; i++){
		if (array[i]>31){
			int temp = (array[i]-ENCRYPTION_KEY)%255;
			array[i] = temp;
		}
	}
}