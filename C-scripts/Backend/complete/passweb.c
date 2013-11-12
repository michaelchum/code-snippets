// STUDENT_NAME: Michael Ho 
// STUDENT_ID: 260532097

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cipher.c"
#define boolean int
#define VALID 1
#define INVALID 0

void add(int argc, char *argv[]){

	if (argc < 5) {
		printf("Error. The %s switch requires at least 3 parameters: username password and type. Please use the following syntax.\n $./passweb %s <username> <password> <type>\n", argv[1], argv[1]);
		return;
	}

	// Open file and set pointer at the beginning, create file if non-existent
	FILE *file = fopen("password.csv", "a+");
	fseek(file, 0, SEEK_SET);

	char line[50];
	char userTemp[50];
	char passTemp[50];
	char typeTemp[50];
	
	// Scan through each line of the file to detect if username already exists. Exit if detected.
    while (fgets(line, sizeof(line), file)) {
    	decrypt(line);
        sscanf(line, "%[^, ], %[^, ], %[^\n]\n", userTemp, passTemp, typeTemp);
 		if (strcmp(userTemp,argv[2])==0) {
 			printf("The username %s already exists in the database. Nothing will be added.\n", userTemp);
 			fclose(file);
 			return;
 		}
    }

    // If the username is non-existent, encrypt and append using the provided information
    char lineInsert[50];
    snprintf(lineInsert, sizeof(lineInsert), "%s, %s, %s\n", argv[2], argv[3], argv[4]);
    encrypt(lineInsert);
    fseek(file, 0, SEEK_END);
    fprintf(file, "%s", lineInsert);
	printf("The user %s has been created in the database.\n", argv[2]);
	fclose(file);

}

void del(int argc, char *argv[]){
	if (argc < 3) {
		printf("Error. The %s switch requires at least 1 parameter: username. Please use the following syntax.\n $./passweb %s <username>\n", argv[1], argv[1]);
		return;
	}
	// Open file and set pointer at the beginning, create a temp file for transfer
	FILE *file = fopen("password.csv", "r");
	FILE *temp = fopen("temp.csv", "w+");

	if(file == NULL){
    	printf("Error. No password.csv present. Need add users first in order to delete entries.");
    	fclose(file);
    	return;
	}

	fseek(file, 0, SEEK_SET);
	fseek(temp, 0, SEEK_SET);

	char line[50];
	char userTemp[50];
	char passTemp[50];
	char typeTemp[50];
	boolean detected=0;
	
	// Scan through each line of the file to detect if username exists.
    while (fgets(line, sizeof(line), file)) {
    	decrypt(line);
        sscanf(line, "%[^, ], %[^, ], %[^\n]\n", userTemp, passTemp, typeTemp);
        /* // Debugging
        printf("%s ", userTemp);
 		printf("%s ", passTemp);
 		printf("%s\n", typeTemp);
 		*/
 		if (strcmp(userTemp,argv[2])==0) {
 			printf("The user %s has been deleted the database, as well as all its information.\n", argv[2]);
 			detected=1;
 		} else {
 			encrypt(line);
 			fprintf(temp, "%s", line);
 		}
    }
    fclose(file);
    fclose(temp);

    if (detected==0){
    	printf("The username %s doesn't exist in the database. Nothing to delete.\n", argv[2]);
    	remove("temp.csv");
    	return;
    }

    remove("password.csv");
    rename("temp.csv", "password.csv");
}

void edit(int argc, char *argv[]){
	if (argc < 5) {
		printf("Error. The %s switch requires at least 3 parameters: username password and type. Please use the following syntax.\n $./passweb %s <username> <password> <type>\n", argv[1], argv[1]);
		return;
	}

	// Open file and set pointer at the beginning, create a temp file for transfer
	FILE *file = fopen("password.csv", "r");
	FILE *temp = fopen("temp.csv", "w+");

	if(file == NULL){
    	printf("Error. No password.csv present. Need add users first in order to edit entries.");
    	fclose(file);
    	return;
	}

	fseek(file, 0, SEEK_SET);
	fseek(temp, 0, SEEK_SET);

	char line[50];
	char userTemp[50];
	char passTemp[50];
	char typeTemp[50];
	boolean detected=0;
	
	// Scan through each line of the file to detect if username exists.
    while (fgets(line, sizeof(line), file)) {
    	decrypt(line);
        sscanf(line, "%[^, ], %[^, ], %[^\n]\n", userTemp, passTemp, typeTemp);
        /* // Debugging
        printf("%s ", userTemp);
 		printf("%s ", passTemp);
 		printf("%s\n", typeTemp);
 		*/
 		if (strcmp(userTemp,argv[2])==0) {
 			char lineInsert[50];
    		snprintf(lineInsert, sizeof(lineInsert), "%s, %s, %s\n", argv[2], argv[3], argv[4]);
    		encrypt(lineInsert);
    		fprintf(temp, "%s", lineInsert);
 			printf("The user %s has been edited with the new information.\n", argv[2]);
 			detected=1;
 		} else {
 			encrypt(line);
 			fprintf(temp, "%s", line);
 		}
    }
    fclose(file);
    fclose(temp);

    if (detected==0){
    	printf("Error. The username %s doesn't exist in the database. Nothing to edit.\n", argv[2]);
    	remove("temp.csv");
    	return;
    }

	remove("password.csv");
    rename("temp.csv", "password.csv");
}

int verify(int argc, char *argv[], int menu){
	if (argc < 4) {
		printf("Error. The %s switch requires at least 2 parameters: username and password. Please use the following syntax.\n $./passweb %s <username> <password>\n", argv[1], argv[1]);
		return INVALID;
	}

	// Open file and set pointer at the beginning
	FILE *file = fopen("password.csv", "r");
	fseek(file, 0, SEEK_SET);

	if (file==NULL){
		printf("Error. Database does not exist, please add users before verifying.\n");
		return INVALID;
	}

	char line[50];
	char userTemp[50];
	char passTemp[50];
	char typeTemp[50];

	// Scan through each line of the file to detect if username with matching password exists.
    while (fgets(line, sizeof(line), file)) {
    	decrypt(line);
        sscanf(line, "%[^, ], %[^, ], %[^\n]\n", userTemp, passTemp, typeTemp);
        // If matching user and pw is found
 		if (strcmp(userTemp,argv[2])==0 && strcmp(passTemp,argv[3])==0) {
 			if (menu) {
 				printf("VALID\n");
 			} else {
 				printf("EXIST_SUCCESS\n");
 			}
 			fclose(file);
 			return VALID;
 		}
 		// If user found but pw not matching
 		if (strcmp(userTemp,argv[2])==0) {
 			printf("Error, user found, but password does not match.\n");
 		}
    }
    // If nothing found
    if (menu) {
    	printf("INVALID\n");
    } else {
    	printf("EXIT_FAILURE\n");
    }
    fclose(file);
    return INVALID;
}


// Check if a single switch has been trigered as the first parameter, but doesn't check its validity
// Returns 0 if switch is okay, returns 0 if switch is wrong
boolean checkSwitch(int argc, char *argv[]){

	// Check if switch or argument present
	if (argc < 2) {
		printf("Error. This program requires a switch to execute. Please use the following syntax with a single valid switch.\n $./passweb –menu –add –del –edit –verify username password type\n");
		return 1;
	}

	// Check if the first parameter is a switch
	if (*argv[1]!='-') {
		printf("Error. The first paramater needs to be a valid switch. Please use the following syntax with a single valid switch.\n $./passweb –menu –add –del –edit –verify username password type\n");
		return 1;
	}

	// Check if more than one switch has been trigered or no switch trigered at all
	int i, check = 0;
	for (i=1;i<argc;i++) {
		if (*argv[i]=='-') check++;
	}
	if (check==0) printf("Error. No switch has been trigered. Please use the following syntax with a single valid switch.\n $./passweb –menu –add –del –edit –verify username password type\n");
	if (check>1) printf("Error. More than one switch has been trigered. Only one switch is supported. Please use the following syntax with a single valid switch.\n $./passweb –menu –add –del –edit –verify username password type\n");
	if (check!=1) return 1;
	return 0;

}

#include "menu.c"

int main(int argc, char *argv[]){

	if (checkSwitch(argc, argv)) return 0;

	if (strcmp(argv[1], "-menu") == 0) {
		menu();
	} 
	else if (strcmp(argv[1], "-add") == 0) {
		add(argc, argv);
	}
	else if (strcmp(argv[1], "-del") == 0) {
		del(argc, argv);
	} 
	else if (strcmp(argv[1], "-edit") == 0) {
		edit(argc, argv);
	} 
	else if (strcmp(argv[1], "-verify") == 0) {
		return verify(argc, argv, 0);
	}
	else {
		printf("%s is an invalid switch. Please use the following syntax with a single valid switch.\n $./passweb –menu –add –del –edit –verify username password type\n", argv[1]);
	}

	return 0;
}

