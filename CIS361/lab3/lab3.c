// lab3.c - measure execution time of C code

#include <stdlib.h>
#include <stdio.h>
#include <time.h>

int main ()
{
	const int MaxSize = 100000;
	int i, j, temp;

	// Part one: processing a statically allocated array

	int staticArray[MaxSize];		// declare an array 
	clock_t begin1 = clock();

	for (i=0; i<MaxSize; i++)		// initialize the array with a
		staticArray[i] = MaxSize-i;	// descending sequence of values

	for (i=0; i<MaxSize-1; i++)		// bubble sort data in the array
		for (j=MaxSize-1; j>i; j--)
			if (staticArray[j-1] > staticArray[j])
			{
				temp = staticArray[j-1];
				staticArray[j-1] = staticArray[j];
				staticArray[j] = temp;
			}

	clock_t end1 = clock();
	printf("Part 1\n");
	printf( "Time used: %lf%s\n",(double)(end1-begin1) / CLOCKS_PER_SEC, " Seconds" );

	// Part two: processing a dynamically allocated array

	double * list;
    list = (double*) malloc (MaxSize * 	sizeof (double));
	clock_t begin2 = clock();

	for ( i=0; i<MaxSize; i++ )
          *(list+i) = MaxSize-i;

	for ( i=0; i<MaxSize-1; i++ )
		for (j=MaxSize-1; j>i; j--)
			if (staticArray[j-1] > staticArray[j])
			{
				temp = *(list+j-1);
				*(list+j-1) = *(list+j);
				*(list+j) = temp;
			}

	clock_t end2 = clock();
	printf("Part 2\n");
	printf( "Time used: %lf%s\n",(double)(end2-begin2) / CLOCKS_PER_SEC, " Seconds" );

	return 0;
}
