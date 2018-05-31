/*************************************
* Lab 13 Program
* Sean Crowley
*
* This program will create a hard or
*  soft link to a specified file
**************************************/

#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>

int main(int argc, char *argv[])
{    
	//Checks there are the correct amount of arguments
	if(argc != 4){
        printf("usage: [-h/-s] [original filepath] [link filepath] \n");
		exit(1);
    }

	if(strcmp(argv[1], "-h") == 0){
		//Creates hard link
		link(argv[2], argv[3]);
	}else if(strcmp(argv[1], "-s") == 0){
		//Creates soft link
		symlink(argv[2], argv[3]);
	}else{
		printf("Incorrect flag [-h/-s]\n");
	}

	printf("Success\n");

	return 0;
}
