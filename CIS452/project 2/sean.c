#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

#define MAXINPUT 50
#define READ 0 
#define WRITE 1 

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
	int answer = 0;
	char op;
    int fd[2]; 

	pid_t pid, child; 
    int status;

	int loop = 1;

	while(loop){

		if(*tokens[i] == '+' || *tokens[i] == '-'){

			if (pipe (fd) < 0) { 
				perror ("plumbing problem"); 
				exit(1); 
			} 

			if ((pid = fork()) < 0) {
				perror("fork failure");
				exit(1);
			}else if (pid == 0) {
				sscanf(tokens[i-1], "%d", &left);
				op = *tokens[i];
				
				if(*tokens[i+2] != '+' && *tokens[i+2] != '-'){
					sscanf(tokens[i+1], "%d", &right);
					answer = left + right;
					printf("Answer : %i\n", answer);
					kill(getppid(), SIGINT);
					loop = 0;
					exit(0);
				}else{
					i = 3;
					//printf("\n%c\n", *tokens[i]);

					if(*tokens[i] == '+' || *tokens[i] == '-'){
						if ((pid = fork()) < 0) {
							perror("fork failure");
							exit(1);
						}else if (pid == 0) {
							sscanf(tokens[i-1], "%d", &left);
							op = *tokens[i];
				
							//if(*tokens[i+2] != '+' && *tokens[i+2] != '-'){
					//printf("\n%c\n", *tokens[i]);
								sscanf(tokens[i+1], "%d", &right);
								answer = left + right;
								printf("Answer2 : %i\n", answer);
								
								kill(getppid(), SIGINT);
								sleep(5);
								exit(0);
							//}
						}else{
							wait(&status);
						}
					}
				}
			}else{
				wait(&status);
			}
		}

		i++;

		if(i==numTokens)
			loop = 0;
	}

	return 0;
}

/*
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
*/
