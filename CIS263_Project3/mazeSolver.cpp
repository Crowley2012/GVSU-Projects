/*
 *	Sean Crowley 
 *  Project 3
 *  CIS 263
 *  4/12/2015
 * 
 *  Backtracking and Greedy algorithms were used in this project.
 */

#include <cstdlib>
#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
#include <ctime>

using namespace std;

int backtrackingMazeSolver(int x, int y);
int greedyMazeSolver(int x, int y, int xf, int yf);

struct maze
{
	int rows;
	int cols;
	char matrix [100][100];
};

maze myMaze;
maze tempMaze;

int main()
{
	//required variables
	ifstream in;
	in.open("sample_mazes/maze.txt");
	char line;
	
	//read the matrix using plain c code, character by character
	in >> myMaze.rows;
	in >> line;
	in >> myMaze.cols;
	cout << "Reading a " << myMaze.rows << " by " << myMaze.cols << " matrix." << endl;
	//Burn the end of line character
	in.ignore(200,'\n');
	
	for(int i=0; i<myMaze.rows; i++){
		for(int j=0; j<myMaze.cols; j++){
			in.get( myMaze.matrix[i][j] );
		}
		//Burn the end of line character
		in.ignore(200,'\n');
	}
	
	//Print the empty maze
	for(int i=0; i<myMaze.rows; i++){
		for(int j=0; j<myMaze.cols; j++){
			cout << myMaze.matrix[i][j];
		}
		cout << endl;
	}
	
	int x=1,y=1;
	
	//Find starting coordinates
	for(int i=0; i<myMaze.rows; i++)
		for(int j=0; j<myMaze.cols; j++)
			if( myMaze.matrix[i][j] == 'S' ){
				x=i;
				y=j;
			}
			
	int xf=1,yf=1;
	
	//Find finishing coordinates
	for(int i=0; i<myMaze.rows; i++)
		for(int j=0; j<myMaze.cols; j++)
			if( myMaze.matrix[i][j] == 'F' ){
				xf=i;
				yf=j;
			}
	
	//Backup maze
	tempMaze = myMaze;
		
	//Store current time	
	clock_t myTick = clock();
	
	//Call backtracking mazeSolver
    int btDistance = backtrackingMazeSolver(x,y);
    
    //Sets starting point back to S
	myMaze.matrix[x][y] = 'S';
    
    //Print solved maze with results
    cout << endl;
    cout << "Backtracking distance: " << btDistance << " units away!" << endl;
	cout << "Time : " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	for(int i=0; i<myMaze.rows; i++){
		for(int j=0; j<myMaze.cols; j++){
			cout << myMaze.matrix[i][j];
		}
		cout << endl;
	}

	//Reset maze
    myMaze = tempMaze;
    
    //Store current time
	myTick = clock();
	
    //Call greddy mazeSolver
    int gDistance = greedyMazeSolver(x,y,xf,yf);
    
    //Sets starting point back to S
	myMaze.matrix[x][y] = 'S';
    
    //Print solved maze with results
    cout << endl;
    cout << "Greedy distance: " << gDistance << " units away!" << endl;
	cout << "Time : " << (clock() - myTick) / (double)(CLOCKS_PER_SEC / 1000) << " ms" << endl;
	for(int i=0; i<myMaze.rows; i++){
		for(int j=0; j<myMaze.cols; j++){
			cout << myMaze.matrix[i][j];
		}
		cout << endl;
	}
    
    return 0;
}

int backtrackingMazeSolver(int x, int y){
	//Check if finish is found
	bool finish = false;
	//Dot character
	char dot = 46;
	
	//Sets current position to dot char
	myMaze.matrix[x][y] = dot;
	
	//Checks if space next to current pos. If so moves to that pos
	if(myMaze.matrix[x][y+1] == ' '){
		myMaze.matrix[x][y+1] = dot;
		return 1 + backtrackingMazeSolver(x, y+1);
	}else if(myMaze.matrix[x][y-1] == ' '){
		myMaze.matrix[x][y-1] = dot;
		return 1 + backtrackingMazeSolver(x, y-1);
	}else if(myMaze.matrix[x+1][y] == ' '){
		myMaze.matrix[x+1][y] = dot;
		return 1 + backtrackingMazeSolver(x+1, y);
	}else if(myMaze.matrix[x-1][y] == ' '){
		myMaze.matrix[x-1][y] = dot;
		return 1 + backtrackingMazeSolver(x-1, y);
	//Checks if space is next to finish location
	}else if(myMaze.matrix[x][y+1] == 'F' || 
			 myMaze.matrix[x][y-1] == 'F' ||
			 myMaze.matrix[x+1][y] == 'F' ||
			 myMaze.matrix[x-1][y] == 'F'){ 
		return 1;
	}else{
		//Used to backtrack by using x as breadcrumbs
		myMaze.matrix[x][y] = 'x';
		if(myMaze.matrix[x][y+1] == dot){
			return -1 + backtrackingMazeSolver(x, y+1);
		}else if(myMaze.matrix[x][y-1] == dot){
			return -1 + backtrackingMazeSolver(x, y-1);
		}else if(myMaze.matrix[x+1][y] == dot){
			return -1 + backtrackingMazeSolver(x+1, y);
		}else if(myMaze.matrix[x-1][y] == dot){
			return -1 + backtrackingMazeSolver(x-1, y);
		}else{
			//Same as above but this ensures we do not get trapped
			// in a square formation
			myMaze.matrix[x][y] = 'X';
			if(myMaze.matrix[x][y+1] == 'x'){
				return -1 + backtrackingMazeSolver(x, y+1);
			}else if(myMaze.matrix[x][y-1] == 'x'){
				return -1 + backtrackingMazeSolver(x, y-1);
			}else if(myMaze.matrix[x+1][y] == 'x'){
				return -1 + backtrackingMazeSolver(x+1, y);
			}else if(myMaze.matrix[x-1][y] == 'x'){
				return -1 + backtrackingMazeSolver(x-1, y);
			}
		}
	}
	
    return -1;
}

int greedyMazeSolver(int x, int y, int xf, int yf){
	//Check if finish is found
	bool finish = false;
	//Check if moves are available
	bool right = false;
	bool left = false;
	bool up = false;
	bool down = false;
	//Distance from each pos to finish
	int rightDist = myMaze.cols * myMaze.rows;
	int leftDist = myMaze.cols * myMaze.rows;
	int downDist = myMaze.cols * myMaze.rows;
	int upDist = myMaze.cols * myMaze.rows;
	//Dot character
	char dot = 46;
	
	//Sets current pos to dot
	myMaze.matrix[x][y] = dot;
	
	//Checks if space next to current pos. If so marks as true
	// then calculates distance to finish
	if(myMaze.matrix[x][y+1] == ' '){
		rightDist = abs((xf - x)) + abs((yf - (y+1)));
		right = true;
	}
	if(myMaze.matrix[x][y-1] == ' '){
		leftDist = abs((xf - x)) + abs((yf - (y-1)));
		left = true;
	}
	if(myMaze.matrix[x+1][y] == ' '){
		downDist = abs((xf - (x+1))) + abs((yf - y));
		down = true;
	}
	if(myMaze.matrix[x-1][y] == ' '){
		upDist = abs((xf - (x-1))) + abs((yf - y));
		up = true;
	}
	
	//Stores distances in array
	int distances[4] = {rightDist, leftDist, downDist, upDist};
	//Sorts array least to greatest
	sort(distances, distances + 4);
	
	//Checks if distance is equal to specific move distance and if that
	// move is valid. If it is then it sets other moves to false
	if(distances[0] == rightDist && right){
		left = false;
		up = false;
		down = false;
	}else if(distances[0] == leftDist && left){
		right = false;
		up = false;
		down = false;
	}else if(distances[0] == upDist && up){
		right = false;
		left = false;
		down = false;
	}else if(distances[0] == downDist && down){
		right = false;
		left = false;
		up = false;
	}
	
	//Moves to which ever move is valid
	if(up){
		myMaze.matrix[x-1][y] = dot;
		return 1 + greedyMazeSolver(x-1, y, xf, yf);
	}else if(down){
		myMaze.matrix[x+1][y] = dot;
		return 1 + greedyMazeSolver(x+1, y, xf, yf);
	}else if(left){
		myMaze.matrix[x][y-1] = dot;
		return 1 + greedyMazeSolver(x, y-1, xf, yf);
	}else if(right){
		myMaze.matrix[x][y+1] = dot;
		return 1 + greedyMazeSolver(x, y+1, xf, yf);
	//Checks if next to finish 
	}else if(myMaze.matrix[x][y+1] == 'F' || 
			 myMaze.matrix[x][y-1] == 'F' ||
			 myMaze.matrix[x+1][y] == 'F' ||
			 myMaze.matrix[x-1][y] == 'F'){
		return 1;
	}else{
		//Used to backtrack by using x as breadcrumbs
		myMaze.matrix[x][y] = 'x';
		if(myMaze.matrix[x][y+1] == dot){
			return -1 + greedyMazeSolver(x, y+1, xf, yf);
		}else if(myMaze.matrix[x][y-1] == dot){
			return -1 + greedyMazeSolver(x, y-1, xf, yf);
		}else if(myMaze.matrix[x+1][y] == dot){
			return -1 + greedyMazeSolver(x+1, y, xf, yf);
		}else if(myMaze.matrix[x-1][y] == dot){
			return -1 + greedyMazeSolver(x-1, y, xf, yf);
		}else{
			//Same as above but this ensures we do not get trapped
			// in a square formation
			myMaze.matrix[x][y] = 'X';
			if(myMaze.matrix[x][y+1] == 'x'){
				return -1 + greedyMazeSolver(x, y+1, xf, yf);
			}else if(myMaze.matrix[x][y-1] == 'x'){
				return -1 + greedyMazeSolver(x, y-1, xf, yf);
			}else if(myMaze.matrix[x+1][y] == 'x'){
				return -1 + greedyMazeSolver(x+1, y, xf, yf);
			}else if(myMaze.matrix[x-1][y] == 'x'){
				return -1 + greedyMazeSolver(x-1, y, xf, yf);
			}
		}
	}
	
    return -1;
}
