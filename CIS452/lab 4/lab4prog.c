/**************************************************************************
*
*	Lab 4 Programming assignment
*
*	Sean Crowley
*	Vignesh Suresh
*
*	Description: This program simulates a multi-threaded file server
*
**************************************************************************/

#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h>
#include <string.h>
#include <pthread.h> 
#include <signal.h>

//Max filename length
#define MAXINPUT 50

//Max number of filenames stored
#define MAXARRAY 20

//Locates file
void* findFile(void* arg); 

//Manages signals
void signalHandler(int signal);

//Number of threads spawned
int currentThreads = 0;

//Backup of total thread count
int backupCurrentThreads = 0;

//Total file requests received
int totalReceived = 0;

//Total files serviced
int totalServiced = 0;

int main(){
	//Status of thread
	int status;

	//Char array containing file name
	char fileName[MAXINPUT];

	//Array of thread data
	char files[MAXARRAY][MAXINPUT];

	//Create an array of threads
	pthread_t *thread = malloc(sizeof(pthread_t));

	//Install signal handler for CTRL-C
	signal(SIGINT, signalHandler);

	while(1)
	{
		//Get user input
		fgets(fileName, MAXINPUT, stdin);

		//Remove new line character
		strtok(fileName, "\n");

		//Check if fileName is empty
		if(fileName[0] != '\n')
		{
			//Increment total received requests
			totalReceived++;

			//Copies the filename to the thread specific array index
			strcpy(files[currentThreads], fileName);

			//Create thread and check for successful creation
			if ((status = pthread_create(&thread[backupCurrentThreads + currentThreads], NULL, findFile, &files[currentThreads])) != 0) 
			{ 
		    		fprintf (stderr, "thread create error %d: %s\n", status, strerror(status)); 
		    		exit (1); 
			}

			//Increment the number of threads started
			currentThreads++;
		}
		else
		{
			//Print error message for empty input
			printf("Please input a file name!\n");
		}

		//If the number of threads is greater than the size of the current threads it resets index back to beginning of array
		if(currentThreads >= MAXARRAY)
		{
			backupCurrentThreads += currentThreads;
			currentThreads = 0;
		}
	}
	return 0;
}

//Finds the file specified by user
void* findFile(void* arg) 
{ 
	//Converts thread argument into a char
	char* val_ptr = (char *) arg;

	//Contains the total time spent finding file (sleep time)
	int findTime;

	//Simulates 80% chance of finding file in the cache
	if(rand() % 10 <= 5)
	{
		//Takes 1 second if file is in cache
		findTime = 1;
		sleep(findTime);
	}
	//Simulates 20% chance of file not in cache
	else
	{
		//Takes random 7-10 seconds if file not in cache
		findTime = rand() % 4 + 7;
		sleep(findTime);
	}

	//Prints the amount of time it took to find file
	printf ("\nFound file : %s\tTime to find : %i secs\n", val_ptr, findTime); 

	//Increments the number of files that have been serviced
	totalServiced++;

	return 0; 
}

//Manages signals
void signalHandler(int signal){
	//Checks the signal type and prints summary data
	if (signal == SIGINT){
		printf("\n\nReceived closing signal...");
		printf("\nClosing all running processes!\n");
		printf("\nTotal file requests: %i\tTotal files serviced: %i\n\n", totalReceived, totalServiced);
		exit (0);
	}
}
