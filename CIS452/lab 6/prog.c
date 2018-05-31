/****************************************************
*	Lab 6 Program Assignment
*
*	Sean Crowley
*	Vignesh Suresh
*
*	This program uses semaphores and shared memory
*	 to swap data points without any collisions
*****************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <sys/wait.h>

#define SIZE 16

int main (int argc, char *argv[])
{
	int status, i, loop, temp, *shmPtr, shmId, semId;
	pid_t pid;

	//Gets user specified loop length
	loop = atoi(argv[1]);

	//Structure of wait and signal
	struct sembuf wait, signal;

	//Define wait structure
	wait.sem_num = 0;
	wait.sem_op = -1;
	wait.sem_flg = SEM_UNDO;

	//Define signal structure
	signal.sem_num = 0;
	signal.sem_op = 1;
	signal.sem_flg = SEM_UNDO;

	//Initialize shared memory
	if ((shmId = shmget (IPC_PRIVATE, SIZE, IPC_CREAT|S_IRUSR|S_IWUSR)) < 0) {
		perror ("Error initializing shared memory\n");
		exit (1);
	}

	//Attach shared memory
	if ((shmPtr = shmat (shmId, 0, 0)) == (void*) -1) {
		perror ("Error attaching shared memory\n");
		exit (1);
	}

	//Define shared memory data
	shmPtr[0] = 0;
	shmPtr[1] = 1;

	//Create semaphore
	semId = semget(IPC_PRIVATE, 1, 00600);
	printf("Created semaphore\n");

	//Initialize semaphore
	semctl(semId, 0, SETVAL, 1);
	printf("Initialized semaphore\n");

	//Child process
	if((pid = fork()) == 0){
		printf("Child Process\n");

		//Swap the contents of shmPtr[0] and shmPtr[1]
		for(i=0; i<loop; i++) {
			//Wait
			semop(semId, &wait, 1);

			temp = shmPtr[0];
			shmPtr[0] = shmPtr[1];
			shmPtr[1] = temp;

			//Signal
			semop(semId, &signal, 1);
		}
		exit(0);
	}

	//Parent Process
	printf("Parent Process\n");

	//Swap the contents of shmPtr[1] and shmPtr[0]
	for(i=0; i<loop; i++) {
		//Wait
		semop(semId, &wait, 1);

		temp = shmPtr[1];
		shmPtr[1] = shmPtr[0];
		shmPtr[0] = temp;

		//Signal
		semop(semId, &signal, 1);
	}

	//Waits for child to complete
	waitpid(pid, &status, 0);	

	//Print data
	printf ("Values: [%i] [%i]\n", shmPtr[0], shmPtr[1]);

	//Remove semaphore
	semctl(semId, 0, IPC_RMID);

	//Detaches shared memory
	if (shmdt (shmPtr) < 0) {
		perror ("Error detaching shared memory\n");
		exit (1);
	}

	//Deallocates shared memory
	if (shmctl (shmId, IPC_RMID, 0) < 0) {
		perror ("Error deallocating shared memory\n");
		exit(1);
	}

	return 0;
}
