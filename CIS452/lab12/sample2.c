#include <stdio.h> 
#include <dirent.h> 
#include <sys/stat.h> 
#include <sys/types.h> 
#include <errno.h>

int main() 
{ 
	DIR *dirPtr; 
	struct dirent *entryPtr; 
	struct stat statBuf;

	dirPtr = opendir (".");


	while ((entryPtr = readdir (dirPtr))) {
		stat (entryPtr->d_name, &statBuf);
		printf ("%jd bytes \t", statBuf.st_size);
		printf ("%s\n", entryPtr->d_name);
		
	}

	closedir (dirPtr); 

	printf("\n");
	return 0; 
}
