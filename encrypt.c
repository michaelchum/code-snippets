#include <stdio.h>
#include <string.h>

int menu(){
	int option = 0;
	printf("MAIN MENU\n==========\n");
	printf("1. Input Text\n");
	printf("2. Encrypt\n");
	printf("3. Decrypt\n");
	printf("4. Exit\n");
	printf("Selection: ");
	scanf("%d", &option);
	if(option>4||option<0){
		option = 5;
	}
	return option;
}

void input(char str[50][50], int *pointerRows){
	//printf("Input sentences to fill the matrix\n");
	int i, j, count, length;
	length = 50;

	// Initialize the 2D matrix
	for (i = 0; i < length; i++){
		for (j = 0; j < length; j++){
			str[i][j] = '\0';
		}	
	}

	getchar(); // Absorb the enter key from buffer

	// Get input from user
	for (count = 0; count < length; count++){
		fgets(str[count], 50, stdin);
		if (str[count][0] == '\n'){
			str[count][0] = '\n';
			str[count][1] = '\r';
			str[count][2] = '\0';
			break;
		}
	}
	*pointerRows = count;

}

void encrypt(char str[50][50], char encrypted[50][50]){
	int n,i,j, fromZero, temp = 0;
	int length = 50;

	// Input the integer N by user
	printf("Input an integer N to encrypt: ");
	scanf("%i", &n);

	for (i=0; i < length; i++){
		for (j=0; j < length; j++){
			if (str[i][j]>='a' && str[i][j]<='z'){ // lowercase
				fromZero = str[i][j] - 'a';
				temp = (fromZero + n) % 26;
				encrypted[i][j] = temp + 'a';
			} else if (str[i][j]>='A' && str[i][j]<='Z'){ // uppercase
				fromZero = str[i][j] - 'A';
				temp = (fromZero + n) % 26;
				encrypted[i][j] = temp + 'A';
			} else { // not a letter
				encrypted[i][j] = str[i][j];
			}
		}
	}

}

void decrypt(char str[50][50], char decrypted[50][50]){
	int n = 0;
	printf("Input an integer N to decrypt: ");
	scanf("%i", &n);
}

void printMatrix(char str[50][50]){
	int i, j;
	int length = 50;
	for (i = 0; i < length; i++){
		for (j = 0; j < length; j++){
			printf("%c", str[i][j]);
		}
	}
}

int main(int argc, char *argv[]){
	int option = 9;
	int numberRows = 0;
	int *pointerRows = &numberRows;
	char str[50][50];
	char encrypted[50][50];
	char decrypted[50][50];

	do{
		option = menu();
		switch (option){
			case 1:
				input(str, pointerRows);
				break;
			case 2:
				encrypt(str, encrypted);
				printMatrix(encrypted);
				break;
			case 3:
				decrypt(str, decrypted);
				break;
		}
	} while(option != 4);
	return 0;
}

