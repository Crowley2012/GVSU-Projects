/*****************************************
* Lab 7 : Program
*
* Sean Crowley
*
* This program prints the frequency of
*  the high-resolution counter and the
*  durations of a while loop and overhead
*****************************************/

#include <iostream>
#include <Windows.h>

using namespace std;

int main(){
	LARGE_INTEGER li;
	__int64 counter;

	cout << "Program Started\n\n";

	//Print frequency of high-resolution counter
	QueryPerformanceFrequency(&li);
	cout << "\nFrequency : " << li.QuadPart << " ticks/sec\n\n";

	//Print high resolution counter in milliseconds
	QueryPerformanceCounter(&li);
	counter = li.QuadPart;
	cout << "Period : " << counter / 1000 << " milliseconds\n\n";

	//Prints the duration of 1,000,000-iteration empty loop
	for(int i=0; i<1000000; i++);
	QueryPerformanceCounter(&li);
	cout << "Loop Duration : " << (li.QuadPart - counter) / 1000 << " milliseconds\n\n";

	//Prints the overhead of call to high-resolution counter
	QueryPerformanceCounter(&li);
	counter = li.QuadPart;
	QueryPerformanceCounter(&li);
	QueryPerformanceCounter(&li);
	cout << "Overhead : " << (li.QuadPart - counter) / 1000 << " milliseconds\n\n";
	
	cout << "\nProgram Finished\n";
	getchar();

	return 0;
}