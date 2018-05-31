#include <stdio.h>
#include <stdlib.h>

struct Mark {
	int x;
	int y;
};

typedef struct Mark Mark;

/* load the structure that p points to with the date from f */
void getInfo (FILE * f, Mark * p);

/* write the data stored in structure item into output file f */
void printInfo (FILE * f, Mark item);

/* compare what pointers a and b point to; to be used by qsort() */
int compare (const void * a, const void * b);

int main()
{
	Mark list[100];
	Mark mark;
	int size = 0, i, col = 0;
	FILE * fin;

	fin = fopen ("lab5.dat", "r");
	if ( fin == NULL )
	{
		printf ("Cannot open file.\n");
		exit(1);

	}

	while ( !feof(fin) )
	{
		getInfo (fin, &mark);
		list[size++] = mark;
	}

	// use qsort() to sort data in list
	qsort (list, size, sizeof(Mark), compare);

	for (i = 0; i < size; i++)
	{
		printInfo (stdout, list[i]);
		if ( ++col % 5 == 0 )
			printf("\n");
		else
			fprintf(stdout, " ");
	}
	fprintf(stdout,"\n");

	fclose (fin);

	return 0;
}

/* complete the following helper functions */

void getInfo (FILE * f, Mark * p)
{
	int x,y;
	fscanf(f,"%d %d", &x, &y);
	p->x = x;
	p->y = y;
}

void printInfo (FILE * f, Mark item)
{
	printf("(%d, %d)", item.x, item.y);

}

int compare (const void * a, const void * b){
	if ( (*(Mark*)a).y > (*(Mark*)b).y ) return -1;
	if ( (*(Mark*)a).y < (*(Mark*)b).y ) return 1;
	if ( (*(Mark*)a).x < (*(Mark*)b).x ) return -1;
	if ( (*(Mark*)a).x > (*(Mark*)b).x ) return 1;
	return 0;
}


