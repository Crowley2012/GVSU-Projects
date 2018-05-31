#include <stdio.h> 
#include <stdlib.h> 
#include <fcntl.h> 
#include <sys/types.h> 
#include <unistd.h> 
#include <errno.h>

#define SIZE 30

int main(int argc, char *argv[]) 
{ 
   struct flock fileLock; 
   int fd; 
   char buf[SIZE];

   if (argc < 2) { 
      printf ("usage: filename\n"); 
      exit (1); 
   } 
   if ((fd = open (argv[1], O_RDWR)) < 0) { 
      perror ("there is"); 
      exit (1); 
   }

   fileLock.l_type = F_WRLCK; 
   fileLock.l_whence = SEEK_SET; 
   fileLock.l_start = 0; 
   fileLock.l_len = 0; 

   while (fcntl (fd, F_SETLK, &fileLock) < 0) { 
      sleep(1);
	  printf("File locked\n");
   }

   read(fd, &buf, SIZE);
   printf("File unlocked\n");
   printf("%s\n", buf);

   fileLock.l_type = F_UNLCK; 
   if (fcntl (fd, F_SETLK, &fileLock) < 0) { 
      perror ("no way"); 
      exit (1); 
   }

   close(fd);

   return 0; 
}
