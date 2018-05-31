#include <stdio.h>
#include <stdlib.h>

int found (char list[], int num, char ch);

int main () {
	char alp[26];
	char key[20];
	char ch = ' ';
	int a, b, c, i, size = 0;

	// read the key from the keyboard into array key (length of the key is less than 20)
	while (ch=getchar(), size<20 && ch!='\n')
		key[size++] = ch;

	// write code below to store the key in array alp after removing duplicate letters 
	for(a = 0; a < size; a++){
		if(!found(alp, size, key[a])){
			alp[b] = key[a];
			b++;
		}
	}

	// write code below to fill up the rest of array alp with other characters of the 
	// alphabet in reverse order
	int z = 122;
	for(c = 0; c < 26; c++){
		if(!found(alp, b, z)){
			alp[b] = z;
			b++;
		}
		z--;
	}

	// print characters in array alp
	for (i=0; i < 26; i++)
		printf("%c", alp[i]);
	
	printf("\n");

	return 0;
}

// search the first num characters in array list for character ch
// return true if it is found; false, otherwise
int found(char list[], int num, char ch) {
	int i;
	for (i=0; i<num; i++)
		if(list[i] == ch)
			return 1;
	return 0;
}
