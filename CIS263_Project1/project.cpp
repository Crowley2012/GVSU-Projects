/*
 *  Sean Crowley 
 *  Project 1
 *  CIS 263
 *  3/9/2015
 * 
 *  This program uses a binary tree to search text files for specific
 *  terms. Duplicate terms are placed in the immediate next right node.
 */

#include <iostream>
#include <string>
#include <fstream>
#include <algorithm>

using namespace std;

//Node struct stores individual words and neighbors
struct Node{
	string data;
	Node *left;
	Node *right;
};

//Tree object with methods to manage tree
class Tree{

  private:
	Node *root;
	
  public:
	Tree(){
		root = NULL;
	}

	Node getRoot(){
		return *root;
	}

	string getRootData(){
		return root -> data;
	}

	void setRoot(string s){
		Node *n = new Node();
		n -> data = s;
		n -> left = NULL;
		n -> right = NULL;
		root = n;
	}
	
	//Adds new node to tree
	void add(string s){
		Node *n = new Node();
		n -> data = s;
		n -> left = NULL;
		n -> right = NULL;
		
		Node *temp = new Node();
		temp = root;
		
		bool set = false;
		
		//Runs until the node finds a suitable position by comparing the
		// ASCII values
		while(!set){
			if(n -> data.compare(temp -> data) < 0 && temp -> left == 0){
				temp -> left = n;
				set = true;
			}else if(n -> data.compare(temp -> data) < 0 && temp -> left != 0){
				temp = temp -> left;
			}else if(n -> data.compare(temp -> data) > 0 && temp -> right == 0){
				temp -> right = n;
				set = true;
			}else if(n -> data.compare(temp -> data) > 0 && temp -> right != 0){
				temp = temp -> right;
			}else{
				//Sets the duplicate node to the right
				n -> right = temp -> right;
				temp -> right = n;
				set = true;
			}	
		}
	}
	
	//Provides a small display of tree structure
	void display(){
		cout << root -> data << "\n";
		cout << root -> left -> data << "\n";
		cout << root -> left -> left -> data << "\n";
		cout << root -> left -> left -> left -> data << "\n";
		cout << root -> left -> left -> left -> left -> data << "\n";
		cout << root -> left -> right -> data << "\n";
		cout << root -> left -> right -> right -> data << "\n";
		cout << root -> right -> data << "\n";
		cout << root -> right -> right -> data << "\n";
		cout << root -> right -> right -> right -> data << "\n";
		cout << root -> right -> right -> right -> right -> right -> data << "\n";
	}
};

//Populates the tree from a text file
void createTree(Tree *tree, string file){
	bool root = false;
	//Symbols to remove from text file
	char spec[] = {'.', ',', ':', ';', '?', '!', '(', ')', '[', ']', '&', '@', '#', '$', '%', '^', '*', ' '};
	//Size of the spec array
	int specSize = sizeof(spec)/sizeof(spec[0]);
	string output;
	ifstream openFile;

	openFile.open(file.c_str());
	
	if(openFile.is_open()){
		while(!openFile.eof()){
			openFile >> output;
			
			if(!openFile.eof()){
				//Sets everyting to lowercase
				transform(output.begin(), output.end(), output.begin(), ::tolower);
				
				//Remove the symbols from the spec array
				for(int i = 0; i < specSize; i++){
					output.erase(remove(output.begin(), output.end(), spec[i]), output.end());
				}
				
				//If there is no root it sets a root
				if(root){
					tree -> add(output);
				}else{
					tree -> setRoot(output);
					root = true;
				}
			}
		}
	}
	openFile.close();
}

//Checks if specific term exists
bool checkTermExists(Tree *tree, string term){
	bool exists = false;
	
	
	Node *n = new Node();
	n -> data = term;
	n -> left = NULL;
	n -> right = NULL;
	
	Node *temp = new Node();
	*temp = tree -> getRoot();
	
	bool set = false;
	
	//Runs until a match is found or there is no match
	while(!set){
		if(n -> data.compare(temp -> data) < 0 && temp -> left == 0){
			set = true;
		}else if(n -> data.compare(temp -> data) < 0 && temp -> left != 0){
			temp = temp -> left;
		}else if(n -> data.compare(temp -> data) > 0 && temp -> right == 0){
			set = true;
		}else if(n -> data.compare(temp -> data) > 0 && temp -> right != 0){
			temp = temp -> right;
		}else{
			exists = true;
			set = true;
		}
	}
	
	return exists;
	
}

//Returns a string with the plays that the term appears in
string whichPlays(string term, Tree *hamlet, Tree *romeoAndJuliet, Tree *theTempest){
	string plays = "";
	
	if(checkTermExists(hamlet, term) == true)
		plays.append("Hamlet  ");
	if(checkTermExists(romeoAndJuliet, term) == true)
		plays.append("Romeo and Juliet  ");
	if(checkTermExists(theTempest, term) == true)
		plays.append("The Tempest");
		
	return plays;	
}

//Tests the program
int main(){
	Tree hamlet;
	Tree romeoAndJuliet;
	Tree theTempest;
	
	createTree(&hamlet, "Hamlet.txt");
	createTree(&romeoAndJuliet, "RomeoAndJuliet.txt");
	createTree(&theTempest, "TheTempest.txt");
	
	cout << "\nWhich plays, if any, contain the term ‘Hamlet’?\n";
	cout << whichPlays("hamlet", &hamlet, &romeoAndJuliet, &theTempest) << "\n\n";
	cout << "Which plays, if any, have a ‘friar’ in them?\n";
	cout << whichPlays("friar", &hamlet, &romeoAndJuliet, &theTempest) << "\n\n";
	cout << "Which plays, if any, have the term ‘Lady’ occur?\n";
	cout << whichPlays("lady", &hamlet, &romeoAndJuliet, &theTempest) << "\n\n";
	cout << "How many of the plays have the term ‘servant’?\n";
	cout << whichPlays("servant", &hamlet, &romeoAndJuliet, &theTempest) << "\n\n";
	cout << "How many of the plays discuss ‘Italy’?\n";
	cout << whichPlays("italy", &hamlet, &romeoAndJuliet, &theTempest) << "\n\n";
	cout << "How many of the plays discuss ‘England?\n";
	cout << whichPlays("england", &hamlet, &romeoAndJuliet, &theTempest) << "\n\n";

	return 0;
}





