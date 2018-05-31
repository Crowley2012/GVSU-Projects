#include <stdio.h>
#include <ctype.h>

int main(){
	char ch;
	int length = 0;
	int avg[1000];
	int words = 1;
	int space = 0;

	printf("Enter text(Ctrl-D to quit).\n");

	scanf("%s", ch);
	printf("%s", ch);

	while(ch = getchar(), ch != EOF){
		if(islower(ch))
			ch = toupper(ch);
		else
			ch = tolower(ch);
		
		if(ch == ' ' && space != 1){
			if(words != 1){
				avg[words - 1] = length - 1;
			}else{
				avg[words - 1] = length;
			}
			words++;
			length = 0;
			space = 1;
		}else{
			while(ch == ' '){
				ch = getchar();
			}
			space = 0;
		}

		putchar(ch);
		length++;

	}
	avg[words - 1] = length - 2;

	printf("Words : %i", words);

	int average = 0;
	int i = 0;

	for(i=0; i<words; i++){
		average += avg[i];
	}

	printf("\nAverage length : %i\n", average / words);

	return 0;
}
