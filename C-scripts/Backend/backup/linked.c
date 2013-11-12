#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cipher.h"
#define BOOLEAN int
#define TRUE 1
#define FALSE 0

char database[] = "password.csv";

struct NODE {
	char username[50]; 
	char password[50]; 
	char usertype[50]; 
	struct NODE *next;
} *head = NULL;

BOOLEAN add(struct NODE *p){
	if (p == NULL) return FALSE; // Check for error
	p->next=head;
	head=p;
	return TRUE;
}

BOOLEAN valid(char username[50], char password[50]){
	struct NODE *test = head;
	while (test != NULL){
		if ((strcmp(test->username, username) == 0) && (strcmp(test->password, password) == 0)) return TRUE;
		test = test->next;
	}
	return FALSE;
}

void createList(void){

	// Open file and set pointer at the beginning, detect if file exists
	FILE *file;
	if ((file = fopen(database, "r")) == 0){	
		printf("Error: was unable to initialize password validation information!!\n");
		exit(0);
	}

	// Set pointer at the beggining of the file
	fseek(file, 0, SEEK_SET);

	char line[50];
	char userTemp[50];
	char passTemp[50];
	char typeTemp[50];

	// Scan through each line of the file and add to linkedlist
	while (fgets(line, sizeof(line), file)) {
		struct NODE *temp = (struct NODE*)malloc(sizeof(struct NODE));
		//decrypt(line);
	    sscanf(line, "%[^, ], %[^, ], %[^\n]\n", userTemp, passTemp, typeTemp);
	    strcpy(temp->username, userTemp);
	    strcpy(temp->password, passTemp);
	    strcpy(temp->usertype, typeTemp);
	    // Add each line to the linked list while checking for errors
	    if (!add(temp)){
	    	printf("Error: was unable to initialize password validation information!!\n");
	    	exit(0);
	    }
	}

	return;
}

// Print the linked list for debugging purpose
void printList(void){
	struct NODE *t = head;
	while(t!= NULL){
		printf("%s %s %s\n", t->username, t->password, t->usertype);
		t = t->next;
	}
	return;
}



