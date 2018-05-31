/*
 *	Sean Crowley 
 *  Project 2
 *  CIS 263
 *  3/25/2015
 * 
 *  I used 5 structures; Red Black Tree, Vector, Binary Heap, 
 * 		Binomial Queue, and Leftist Heap.
 * 
 *  The Vector turned out to perform the fastest when loading in
 * 		a file.
 * 
 *  The Red Black Tree turned out to perform the fastest when
 * 		searching for a term.
 */

#include <iostream>
#include <string>
#include <fstream>
#include <algorithm>
#include <iostream>
#include <ctime>
#include "RedBlackTree.h"
#include "Vector.h"
#include "BinaryHeap.h"
#include "BinomialQueue.h"
#include "LeftistHeap.h"

using namespace std;

//Total lines read from file
int lines = 0;

//Loads file into structure
template<typename T>
void createTree(T tree, string fileName){
	lines = 0;
	string output;
	ifstream file;

	file.open(fileName);
	
	//Checks if file is open and has data
	if(file.is_open()){
		while(!file.eof()){
			
			//Removes blank lines
			file >> ws;
			
			//Retrieves next line
			getline(file, output);
			
			//Sets everyting to lowercase
			transform(output.begin(), output.end(), output.begin(), ::tolower);
			
			//Prevents duplicate last line
			if(!file.eof()){
				//Inserts data into tree
				tree -> insert(output);
				lines++;
			}
		}
	}
	file.close();
}

//Loads file into vector
template<typename V>
void createVector(V vector, string fileName){
	lines = 0;
	string output;
	ifstream file;

	file.open(fileName);
	
	//Checks if file is open and has data
	if(file.is_open()){
		while(!file.eof()){
			
			//Removes blank lines
			file >> ws;
			
			//Retrieves next line
			getline(file, output);
			
			//Sets everyting to lowercase
			transform(output.begin(), output.end(), output.begin(), ::tolower);
			
			//Prevents duplicate last line
			if(!file.eof()){				
				vector -> push_back(output);
				lines++;
			}
		}
	}
	file.close();
}

//Searches Red Black Tree for term
void searchRedBlack(RedBlackTree<string> *tree, string term){
	
	clock_t myTick = clock();
	RedBlackTree<string> temp = *tree;
	term = term + "\r";
	
	//Sets term to lowercase
	transform(term.begin(), term.end(), term.begin(), ::tolower);
	
	//Checks if term exists
	if(temp.contains(term)){
		cout << "\nTerm Located: " << term << "\n";
		cout << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms";
	}else{
		cout << "Term not found.";
	}
}

//Searches structure for term
template<typename T>
void searchStructure(T *structure, string term){
	
	clock_t myTick = clock();
	T temp = *structure;
	
	//Sets term to lowercase
	transform(term.begin(), term.end(), term.begin(), ::tolower);
	
	//Checks if term exists
	while(temp.findMin().substr(0, term.length()).compare(term) != 0){
		temp.deleteMin();
	}
	
	cout << "\nTerm Located: " << temp.findMin() << "\n";
	cout << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms";
}

//Searches vector for term
void searchVector(Vector<string> *vect, string term){
	
	clock_t myTick = clock();
	Vector<string> temp = *vect;
	
	//Sets term to lowercase
	transform(term.begin(), term.end(), term.begin(), ::tolower);
	
	//Checks if term exists
	while(vect -> back().substr(0, term.length()).compare(term) != 0){
		vect -> pop_back();
	}
	
	cout << "\nTerm Located: " << vect -> back() << "\n";
	cout << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms";
}

//Tests file
int main(){
	
	//Create trees
	RedBlackTree<string> *tree1 = new RedBlackTree<string>("");
	BinaryHeap<string> *tree2 = new BinaryHeap<string>();
	BinomialQueue<string> *tree3 = new BinomialQueue<string>();
	LeftistHeap<string> *tree4 = new LeftistHeap<string>();
	Vector<string> *tree5 = new Vector<string>();
	
	clock_t myTick = clock();
	
	//Load data into file and record time; 1X
	createTree(tree1, "RomeoAndJuliet(1).txt");
	cout << "\nRomeoAndJuliet(1).txt - Lines : " << lines;
	cout << "\nRed Black Tree Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	myTick = clock();
	createTree(tree2, "RomeoAndJuliet(1).txt");
	cout << "Binary Heap Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	myTick = clock();
	createTree(tree3, "RomeoAndJuliet(1).txt");
	cout << "Binomial Queue Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	myTick = clock();
	createTree(tree4, "RomeoAndJuliet(1).txt");
	cout << "Leftist Heap Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	myTick = clock();
	createVector(tree5, "RomeoAndJuliet(1).txt");
	cout << "Vector Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;

	//10x
	createTree(tree1, "RomeoAndJuliet(10).txt");
	cout << "\nRomeoAndJuliet(10).txt - Lines : " << lines;
	cout << "\nRed Black Tree Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	myTick = clock();
	createTree(tree2, "RomeoAndJuliet(10).txt");
	cout << "Binary Heap Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	myTick = clock();
	createTree(tree3, "RomeoAndJuliet(10).txt");
	cout << "Binomial Queue Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	myTick = clock();
	createTree(tree4, "RomeoAndJuliet(10).txt");
	cout << "Leftist Heap Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	myTick = clock();
	createVector(tree5, "RomeoAndJuliet(10).txt");
	cout << "Vector Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;

	//100x
	createTree(tree1, "RomeoAndJuliet(100).txt");
	cout << "\nRomeoAndJuliet(100).txt - Lines : " << lines;
	cout << "\nRed Black Tree Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	myTick = clock();
	createTree(tree2, "RomeoAndJuliet(100).txt");
	cout << "Binary Heap Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	myTick = clock();
	createTree(tree3, "RomeoAndJuliet(100).txt");
	cout << "Binomial Queue Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	myTick = clock();
	createTree(tree4, "RomeoAndJuliet(100).txt");
	cout << "Leftist Heap Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	myTick = clock();
	createVector(tree5, "RomeoAndJuliet(100).txt");
	cout << "Vector Time: " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms\n" << endl;

	//Searches Red Black Tree
	cout << "Red Black Tree Search";
	searchRedBlack(tree1, "THE TRAGEDY OF ROMEO AND JULIET");
	searchRedBlack(tree1, "Rom. Tush, thou art deceiv'd.");
	searchRedBlack(tree1, "THE END");

	//Searches Binary Heap
	cout << "\n\nBinary Heap Search";
	searchStructure(tree2, "THE TRAGEDY OF ROMEO AND JULIET");
	searchStructure(tree2, "Rom. Tush, thou art deceiv'd.");
	searchStructure(tree2, "THE END");
	
	//Searches Binomial Queue
	cout << "\n\nBinomial Queue Search";
	searchStructure(tree3, "THE TRAGEDY OF ROMEO AND JULIET");
	searchStructure(tree3, "Rom. Tush, thou art deceiv'd.");
	searchStructure(tree3, "THE END");
	
	//Searches Leftist Heap
	cout << "\n\nLeftist Heap Search";
	searchStructure(tree4, "THE TRAGEDY OF ROMEO AND JULIET");
	searchStructure(tree4, "Rom. Tush, thou art deceiv'd.");
	searchStructure(tree4, "THE END");
	
	//Searches Vector
	cout << "\n\nVector Search";
	searchVector(tree5, "THE TRAGEDY OF ROMEO AND JULIET");
	searchVector(tree5, "Rom. Tush, thou art deceiv'd.");
	searchVector(tree5, "THE END");
	
	cout << endl;
	
	return 0;
}
