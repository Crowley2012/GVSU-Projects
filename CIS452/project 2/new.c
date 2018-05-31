#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

#define MAXINPUT 50

int equate(char **tokens, int numTokens);

int main(){

	char *equation = malloc(MAXINPUT);
	char **tokens = malloc(MAXINPUT * sizeof(char*));
	int numTokens = 0;

	//Get user input and remove new line
	fgets(equation, MAXINPUT, stdin);
	strtok(equation, "\n");

	//Breaks user input into seperate tokens and keeps track of total tokens
	equation = strtok(equation, " ");
	while(equation != NULL){
		tokens[numTokens] = equation;
		numTokens++;
		equation = strtok(NULL, " ");
	}

	equate(tokens, numTokens);
	
	return 0;
}

int equate(char **tokens, int numTokens){
	int left = 0;
	int right = 0;
	int i = 0;
	char op;

	pid_t pid;
	int status;

	for(i=0;i<numTokens;i++){
		printf("%s\n", tokens[i]);

		if(*tokens[i] == '+' || *tokens[i] == '-' || *tokens[i] == '*' || *tokens[i] == '/'){

			if(tokens[i+2] == NULL){
				sscanf(tokens[i-1], "%d", &left);
				op = *tokens[i];
				sscanf(tokens[i+1], "%d", &right);
				printf("PID : %i\t PPID : %i\t%i\t%i\t%c\tANSWER : %i \n", getpid(), getppid(), left, right, op, left + right);
			}else{
				if ((pid = fork()) < 0) {
					perror("fork failure");
					exit(1);
				}else if (pid == 0) {
					sscanf(tokens[i-1], "%d", &left);
					op = *tokens[i];

					printf("PID : %i\t PPID : %i\t%i\t%i\t%c\n", getpid(), getppid(), left, right, op);

					exit(0);
				}
			}
			wait(&status);
		}
	}

	return 0;
}
