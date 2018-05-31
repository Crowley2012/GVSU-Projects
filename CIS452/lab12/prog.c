#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <errno.h>
#include <pwd.h>
#include <grp.h>
#include <time.h>
#include <string.h>
#include <dirent.h> 

int main(int argc, char *argv[]){

	struct stat statBuf;
	struct passwd *pwd;
	struct group *grp;

    if(argc != 3){
        printf("usage: %s [-l/i] [filepath]\n", argv[0]);
		exit(1);
    }

	DIR *d;
	struct dirent *dir;
	d = opendir(argv[2]);

	if (d){
		while ((dir = readdir(d)) != NULL){

			char str[300];
			strcpy(str, argv[2]);
			strcat(str, dir->d_name);

			//printf("\n%s\n", str);

			if (stat (str, &statBuf) < 0) {
				perror ("Error statBuf");
				exit(1);
			} 

			if(strcmp(argv[1], "-l") == 0){
				//Checks if directory
				if(S_ISDIR(statBuf.st_mode)){
					printf("d");
				}else{
					printf("-");
				}

				//Owner permissions
				if(statBuf.st_mode & S_IRUSR){
					printf("r");
				}else{
					printf("-");
				}

				if(statBuf.st_mode & S_IWUSR){
					printf("w");
				}else{
					printf("-");
				}

				if(statBuf.st_mode & S_IXUSR){
					printf("x");
				}else{
					printf("-");
				}

				//Group permissions
				if(statBuf.st_mode & S_IRGRP){
					printf("r");
				}else{
					printf("-");
				}

				if(statBuf.st_mode & S_IWGRP){
					printf("w");
				}else{
					printf("-");
				}

				if(statBuf.st_mode & S_IXGRP){
					printf("x");
				}else{
					printf("-");
				}

				//Other permissions
				if(statBuf.st_mode & S_IROTH){
					printf("r");
				}else{
					printf("-");
				}

				if(statBuf.st_mode & S_IWOTH){
					printf("w");
				}else{
					printf("-");
				}

				if(statBuf.st_mode & S_IXOTH){
					printf("x");
				}else{
					printf("-");
				}

				//Number of links and directories
				printf(" %ld", (long) statBuf.st_nlink);

				//File owner
				if ((pwd = getpwuid(statBuf.st_uid)) != NULL)
					printf(" %s", pwd->pw_name);

				//Group owner
				if ((grp = getgrgid(statBuf.st_gid)) != NULL)
					printf(" %s", grp->gr_name);

				//File size
				printf(" %lld", (long long) statBuf.st_size);

				//File modified time
				time_t timer;
				char buffer[26];
				struct tm* tm_info;

				time(&timer);
				tm_info = localtime(&timer);
				strftime(buffer, 26, "%b %d %H:%M", tm_info);
				printf(" %s", buffer);

				//File name
				//printf(" %s", argv[2]);
				printf(" %s\n", dir->d_name);

			}else if(strcmp(argv[1], "-i") == 0){
				printf("%ld %s ", (long) statBuf.st_ino, dir->d_name);
			}

		}

		closedir(d);
	}

	return 0;
}
