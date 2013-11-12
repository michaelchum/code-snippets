// STUDENT_NAME: Michael Ho 
// STUDENT_ID: 260532097

#include <stdio.h>

void menu(){
	printf("Password database control\n");
	printf("1. Add a user\n");
	printf("2. Delete a user\n");
	printf("3. Edit a user\n");
	printf("4. Verify a user\n");
	printf("5. Quit\n");
	
	char option[50];
	char array[3][50];
	char *input[10];
	input[2]=array[0];
	input[3]=array[1];
	input[4]=array[2];
	char buffer;
	while(option[0]!='5'){
		printf("Input an option: ");
		scanf("%s",option);
		getchar();
		switch(option[0]){
			case '1': //add
				printf("Input username: ");
				scanf("%s", array[0]);
				getchar();
				printf("Input password: ");
				scanf("%s", array[1]);
				getchar();
				printf("Input type: ");
				scanf("%s", array[2]);
				getchar();
				add(5, input);
				continue;
			case '2': //del
				printf("Input username to delete: ");
				scanf("%s", array[0]);
				getchar();
				del(3, input);
				continue;
			case '3': //edit
				printf("Input username: ");
				scanf("%s", array[0]);
				getchar();
				printf("Input new password: ");
				scanf("%s", array[1]);
				getchar();
				printf("Input new type: ");
				scanf("%s", array[2]);
				getchar();
				edit(5, input);
				continue;
			case '4': //verify
				printf("Input username: ");
				scanf("%s", array[0]);
				getchar();
				printf("Input password: ");
				scanf("%s", array[1]);
				getchar();
				verify(4, input, 1);
				continue;
			case '5': //quit
				break;
			default :
				printf("Error: Invalid option\n");
		}
	}	
}
