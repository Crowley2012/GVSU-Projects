#include <stdio.h> 
#include <stdlib.h> 
#include <sys/stat.h> 
#include <sys/types.h> 
#include <errno.h>

int main(int argc, char *argv[]) 
{ 
	struct stat statBuf;

	if (argc < 2) { 
		printf ("Usage: filename required\n"); 
		exit(1); 
	}

	if (stat (argv[1], &statBuf) < 0) { 
		perror ("huh?  there is "); 
		printf("\nINPUT : %s\n", argv[1]);
		exit(1); 
	}

	printf ("value is: %u\n", statBuf.st_mode); 

	if(S_ISDIR(statBuf.st_mode)){
		printf ("file is a directory\n");
	}

	return 0; 
}
