/**************************************************************
* Sean Crowley
* Project 3
*
* This program emulates the du utility. Params -h / -s
*
**************************************************************/

#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <dirent.h>
#include <stdio.h>
#include <string.h>
#include <sys/stat.h>
#include <math.h>

//Holds the output of the current folder information
struct info{
	double size;
	char output[300];
};

int arraySize = 0;
int humanReadable = 0;
int sorted = 0;
long long sizeTotal;
char output[300];
struct info fileArray[300];

//Converts bytes to KB/MB/GB
void convert(double size){
	double b = size * 1024;
	double num;
	char label = 'B';

	//Changes label based on size
	if(b/1024 < 1000){
		num = b/1024;
		label = 'K';
	}else if(b/1048576 < 1000){
		num =  b/1048576;
		label = 'M';
	}else if(b/1073741824 < 1000){
		num =  b/1073741824;
		label = 'G';
	}else{
		num = b;
	}

	//Rounds the number of bytes
	num = ceil(num * 10.0) / 10.0;

	//Appends a 0 if less than 10
	if(num < 10)
		sprintf(output, "Size: %.1f%c", num, label);
	else
		sprintf(output, "Size: %.0f%c", num, label);
}

int listdir(const char *name)
{
	long long size;
	int files;
    DIR *d;
    struct dirent *dir;
	struct stat statBuf;

	//Opens dir
    if(!(d = opendir(name)))
        return 0;

	//Reads dir
    if(!(dir = readdir(d)))
        return 0;

	//Gets the file information
	if (lstat(name, &statBuf) < 0) {
		perror ("Error lstat : ");
		exit(1);
	}

	//Number of files in current dir
	files = 0;

	//Returns number of blocks /2 because returns number of 1024 blocks, need 512
	size = (long long) statBuf.st_blocks / 2;

	//Keeps track of the total size
	sizeTotal += (long long) statBuf.st_blocks / 2;

    do{
		if(strcmp(dir->d_name, ".") != 0 && strcmp(dir->d_name, "..") != 0){

			//Creates the file path
			char file[300];
			strcpy(file, name);
			strcat(file, "/");
			strcat(file, dir->d_name);

			//Gets the file information
			if (lstat(file, &statBuf) < 0) {
				perror ("Error lstat : ");
				exit(1);
			} 

			//Checks if is directory if it is start recursion
			if(S_ISDIR(statBuf.st_mode)){
            	size += listdir(file);
			}else{
				files ++;
				size += (long long) (statBuf.st_blocks / 2);
				sizeTotal += (long long) statBuf.st_blocks / 2;
			}
		}
    }while((dir = readdir(d)));

	//Prints the data to output based on the user specified parameters
	if(humanReadable){
		if(strcmp(name, ".") == 0){
			convert((double) sizeTotal);
		}else{
			convert((double) size);
		}
		sprintf(output + strlen(output), "\tFiles: %i\t%s\n", files, name);
	}else{
		if(strcmp(name, ".") == 0)
			sprintf(output, "Blocks: %lld\tFiles: %i\t%s\n", sizeTotal, files, name);
		else
			sprintf(output, "Blocks: %lld\tFiles: %i\t%s\n", size, files, name);
	}

	//Prints the unsorted data
	if(sorted == 0){
		printf("%s", output);
	}else{
		//Stores the data in an array of info
		fileArray[arraySize].size = size;
		strcpy(fileArray[arraySize].output, output);

		arraySize ++;
	}

    closedir(d);

	return size;
}

int main(int argc, char *argv[])
{
	//Sets the program up based on the parameters
	if(argc > 2){
		if(strcmp(argv[1], "-h") == 0){
			humanReadable = 1;
			listdir(argv[2]);
		}
		if(strcmp(argv[1], "-s") == 0){
			sorted = 1;
			listdir(argv[2]);
		}
		if(strcmp(argv[1], "-hs") == 0 || strcmp(argv[1], "-sh") == 0){
			humanReadable = 1;
			sorted = 1;
			listdir(argv[2]);
		}
	}else{
		listdir(argv[1]);
	}

	if(sorted == 1){
		int i;
		int sortedSize;
		int remove = 0;
		struct info sorted[300];
		struct info temp;

		//Ensures that it checks every element in array
		for(sortedSize=0; sortedSize < arraySize; sortedSize++){
			temp.size = 0;

			for(i=0; i<arraySize; i++){
				
				//Checks if the temp size is greater if it is then set that equal to size
				if(fileArray[i].size >= temp.size){
					temp.size = fileArray[i].size;
					remove = i;
					strcpy(temp.output, fileArray[i].output);
				} 
			}
			//Sets the current greatest in the unsorted array to -1 so that it will not be greater than another element
			fileArray[remove].size = -1;
			sorted[sortedSize] = temp;
		}

		//Prints the sorted array
		for(i=0; i<arraySize; i++){
			printf("%s", sorted[i].output);
		}
	}

    return 0;
}












