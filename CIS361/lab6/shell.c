#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAXLINE 80
#define MAXARG 20

char cmd[MAXLINE];

void background(char*);

int main() {
    int childStatus;

    while(1) {
        printf("mysh$ ");
        fgets(cmd, MAXLINE, stdin);
        
        if (strcmp(cmd,"exit\n") == 0) {
            exit(0);
        }

        int childPid = fork();

		if (childPid  < 0) {
			exit(1);
		}
		else if (childPid == 0) {
			background(cmd);
		}
        wait (childStatus);
    }
}

void background(char* cmd) {
    int i = 0;
    char *argv[MAXARG];

    argv[i++] = strtok(cmd, "\t \n");
    while (i<MAXARG && (argv[i++] = strtok(NULL, "\t \n")) != NULL);

    execvp (argv[0], argv);
}
