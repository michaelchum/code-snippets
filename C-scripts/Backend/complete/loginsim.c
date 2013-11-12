// STUDENT_NAME: Michael Ho 
// STUDENT_ID: 260532097

#include <stdio.h>
#include <stdlib.h>

int main(void){
	char username[50];
	char password[50];
	char program[] = "./passweb";
	char command[] = "-verify";
	char lineInsert[256];
	printf("Username: ");
	scanf("%s", username);
	getchar();
	printf("Password: ");
	scanf("%s", password);
	getchar();

	snprintf(lineInsert, sizeof(lineInsert), "%s %s %s %s", program, command, username, password);
	if (system(lineInsert)){
		printf("Verified!\n");
	} else {
		printf("Invalid!\n");
	}
	return 0;
}