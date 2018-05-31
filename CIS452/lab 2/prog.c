/*****************************************************************************
*	Lab 2 Programming Assignment
*	Sean Crowley
*	Vignesh Suresh
*
*	Description : 	This is a simple command interpreter. It utilizes system
*					calls for creation, suspension, execution, and termination
*					to manage processes.
*****************************************************************************/

#include <stdio.h>
#include <stdlib.h> 
#include <sys/types.h>
#include <sys/wait.h> 
#include <unistd.h> 
#include <termcap.h>
#include <string.h>
#include <sys/resource.h>

//Method to clear the screen at the launch of program
void clear_screen(){
	char buf[1024];

	tgetent(buf, getenv("TERM"));
	fputs(tgetstr("cl", NULL), stdout);
} 

int main(){
	//Size of inputs
	int SIZE = 50;

	//Contains user input
	char *input;

	//Contains tokens from user input
	char *tokens[20];

	//Clears the screen
	clear_screen();

	while(1){
		//Allocates memory to input
		input = malloc(SIZE);

		//Gets user input and removes new line character
		printf("SCVS: ");
		fgets(input, SIZE, stdin);
		strtok(input, "\n");

		//Checks if user wants to quit
		if(!strcmp(input, "quit"))
			break;

		//Breaks user input into seperate tokens and keeps track of total tokens
		input = strtok(input, " ");
		int numTokens = 0;
		while(input != NULL){
			tokens[numTokens] = input;
			numTokens++;
			input = strtok(NULL, " ");
		}

		//Process ID and Status of process
		pid_t pid;
		int status;

		//Check if fork is successful
		if ((pid = fork()) < 0) {
		    perror("fork failure");
		    exit(1);
		}else if (pid == 0) {
			//Gets resource usage for process
			struct rusage usage;
			getrusage(RUSAGE_SELF, &usage);

			printf("\nUser CPU time used: %ld.%06ld\n", usage.ru_stime.tv_sec, usage.ru_stime.tv_usec);
			printf("Involuntary context switches: %ld\n\n", usage.ru_nivcsw);

			//Executes the command specified by the user
			tokens[numTokens] = NULL;
			execvp (tokens[0], tokens);

			//Kills that child
			exit(0);
		}
		
		//Waits until child has died
		wait(&status);

		//Free allocated memory for input
		free(input);
	}

	printf("Closed\n");

	return 0;
}
