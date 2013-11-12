#include<stdio.h>
#include<math.h>

int checkBinary(int y){
	int check = 0;
	int x = y;
	while(x>10){
		x = x%10;
	}
	if (x==1 || x==0){
		check = 1;
	}
	return check;
}
 
main()
{
	int n;
	int decimal = 0;
	int exponent = 1;
	int remainder = 0;
	printf("Enter a binary number\n");
	scanf("%i",&n);

	int check = checkBinary(n);
	if (check == 0){
		printf("Error, this is not a binary number\n");
		return 0;
	}

	while(n>0){
		remainder = n%10;
		decimal = decimal + remainder*exponent;
		exponent = exponent*2;
		n = n/10;
	}

	printf("%i\n", decimal);

	return 0;
}