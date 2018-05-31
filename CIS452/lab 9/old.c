#include<stdio.h>

int d = 4;
int e = 5;
int f = 6;

int g;
int h;
int i;

int main(){

	int a = 1;
	int b = 2;
	int c = 3;

	printf("A: %p\nB: %p\nC: %p\n", &a, &b, &c);
	//0x7fff45176d7c	140734352551292	A
	//0x7fff45176d78	140734352551288	B
	//0x7fff45176d74	140734352551284	C

	printf("D: %p\nE: %p\nF: %p\n", &d, &e, &f);
	//0x601034	6295604	D
	//0x601038	6295608	E
	//0x60103c	6295612 F

	printf("G: %p\nH: %p\nI: %p\n", &g, &h, &i);
	//0x60104c	6295628 G
	//0x601048	6295624 H
	//0x601050	6295632 I

	return 0;
}
