//Sample program 1

#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main()
{
    puts("Before fork");
    fork();
	sleep(10);
    puts("After fork");
    return 0;
} 

//Sample program 2

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

int main(int argc, char* argv[])
{
    int i, limit;

    if (argc < 2) {
        fputs("Usage: must supply a limit value\n", stderr);
        exit(1);
    }
    limit = atoi(argv[1]);

    fork();
    fork();
    printf ("PID#: %d\n", getpid());
    for (i=0; i<limit; i++)
        printf("%d\n", i);
    return 0;
}

//Sameple program 3

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int main()
{ 

    // use these variables

    pid_t pid, child;
    int status;

    if ((pid = fork()) < 0) {
        perror("fork failure");
        exit(1);
    }
    else if (pid == 0) {
        printf("I am child PID %ld\n", (long) getpid());
        /* insert an appropriate form of the exit() function here */
		exit(0);
    }
    else {
        /* insert an appropriate form of the wait() system call here */
		child = wait(&status);
		
        printf("Child PID %ld terminated with return status %d\n", (long) child, status);
    }
    return 0;
} 

//Sample program 4

#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char* argv[])
{
    if (argc < 2) {
        fputs("Usage: must supply a command\n", stderr);
        exit(1);
    }

    puts("Before the exec");
	printf("\n%s\n", argv[1]);
	printf("\n%s\n", &argv[1]);
    if (execvp(argv[1], &argv[1]) < 0) {
        perror("exec failed");
        exit(1);
    }
    puts("After the exec");

    return 0;
} 
