#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

int main()
{
	int out;
	pid_t pid;

	out = open("out", O_WRONLY | O_TRUNC | O_CREAT, S_IRUSR | S_IRGRP | S_IWGRP | S_IWUSR);

    if ((pid = fork()) < 0) {
        perror("fork failure");
        exit(1);
    }else if(!pid){
		printf(" -CHILD- ");
		exit(0);
	}else{
		dup2(out, 1);
		printf(" -PARENT- ");
	}

	close(out);

	return 0;
}
