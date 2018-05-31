/************************************************
* Sean Crowley
* Lab 14 Program
*
* This program generates 2 random numbers using
*  /dev/random
*************************************************/

#include <stdio.h> 
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main() { 

	for(int i=0; i<2; i++){
		//Opens /dev/random
		int randomData = open("/dev/random", O_RDONLY);

		//Stores the random data
		char myRandomData[50];

		//Reads in the random number
		ssize_t result = read(randomData, myRandomData, (sizeof myRandomData));

		//Sets the seed of random to the number from /dev/random
		srandom(result);

		//Generates random number
		long int num = random();

		//Prints the number
		printf("%ld\n", num);
	}

	return 0;
}
