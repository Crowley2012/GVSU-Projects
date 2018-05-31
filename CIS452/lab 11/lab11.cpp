#include <Windows.h>
#include <iostream>
#include <math.h>

using namespace std;

int main(){
	//Page Size
	SYSTEM_INFO si;
	GetSystemInfo(&si);
	cout << "Page Size : " << si.dwPageSize << " B\n";

	//Allocates memory
	int *mem;
	mem = (int*)malloc(pow(2.0, 20.0));
	mem = (int*)malloc(1000000);
	cout << "\nAllocated Memory : " << (pow(2.0, 20.0))/1000 << " KB\n";

	//Determine allocated memory state
	MEMORY_BASIC_INFORMATION mbi;

	VirtualQuery(mem, &mbi, sizeof(mbi));

	if(mbi.State == MEM_FREE){
		cout << "\nVirtualQuery : Memory Free\n";
	}else if(mbi.State == MEM_COMMIT){
		cout << "\nVirtualQuery : Memory Committed\n";
	}else if(mbi.State == MEM_RESERVE){
		cout << "\nVirtualQuery : Memory Reserved\n";
	}

	//Frees the allocated memory
	cout << "\nfree(memory)\n";
	free(mem);

	//Determine the de-allocated memory state
	VirtualQuery(mem, &mbi, sizeof(mbi));

	if(mbi.State == MEM_FREE){
		cout << "\nVirtualQuery : Memory Free\n";
	}else if(mbi.State == MEM_COMMIT){
		cout << "\nVirtualQuery : Memory Committed\n";
	}else if(mbi.State == MEM_RESERVE){
		cout << "\nVirtualQuery : Memory Reserved\n";
	}

	//Program is finished
	cout << "\nProgram Finished\n";
	getchar();

	return 0;
}