// STUDENT_NAME: Michael Ho 
// STUDENT_ID: 260532097

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "linked.h"
#include "cipher.h"

int main(void){

	printf("Welcome to Michael's login page\n");
	createList();
	// Debugging print the linked list
	//printList();

	char username[50];
	char password[50];
	int numberTries = 0;
	
	while(numberTries<3){
		printf("Username: ");
		scanf("%s", username);
		getchar();
		printf("Password: ");
		scanf("%s", password);
		getchar();

		if (valid(username,password)){
			printf("Congratulations, you've made it!\n");
			return 0;
		} else {
			printf("Sorry, please try again.\n");
		}
		numberTries++;
	}

	printf("Calling the police!\n");

	// Open file and set pointer at the beginning, create file if non-existent
	FILE *file = fopen("LoginErrors.txt", "a+");

	char lineInsert[256];
	time_t rawtime;
	time(&rawtime);

    snprintf(lineInsert, sizeof(lineInsert), "%s%s %s\n", ctime(&rawtime), username, password);
    fprintf(file, "%s", lineInsert);

	fclose(file);
	return 0;
}