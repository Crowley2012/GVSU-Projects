/*****************************************************************************
*	Program 1 : Order entry system
*	Sean Crowley
*
*	Description : 	This program creates a simple order entry system using
*					the curses library. It shows how to clear the screen,
*					take user input, and print to any location in the terminal
*****************************************************************************/

#include <stdio.h>
#include <stdlib.h> 
#include <unistd.h>
#include <term.h>
#include <curses.h>
#include <string.h>

void printForm();

int main(){
	//Stores the rows and columns of the terminal
	int col, row;

	//Start curses
	initscr();

	//Allow color to be used
	start_color();
	init_pair(1, COLOR_MAGENTA, COLOR_BLACK);
	attron(COLOR_PAIR(1));

	//Gets the column and row numbers
	col = tigetnum("cols"),
	row = tigetnum("lines");

	//Displays the rows and columns
	printw("Rows: %d\n", row);
	printw("Cols: %d\n", col);

	//Pushes output to screen
	refresh();

	//Wait
	sleep(2);
	
	//Clears the screen
	clear();

	//Prints the order entry form
	printForm();

	//Get item input
	char item[20];
	getnstr(item, 20);

	//Resets screen
	clear();
	refresh();

	//Prints form and item
	printForm();
	move(5, 16);
	printw(item);
	move(15, 17);

	//Get name input
	char name[20];
	getnstr(name, 20);

	//Resets screen
	clear();
	refresh();

	//Prints form, item and name
	printForm();
	move(5, 16);
	printw(item);
	move(10, 16);
	printw(name);
	move(15, 17);

	//Pushes output to screen
	refresh();

	//Wait
	sleep(3);

	//Ends the current window
	endwin();

	return 0;
}

//Prints form
void printForm(){
	move(5, 10);
	printw("Item: ");
	move(10, 10);
	printw("Name: ");
	move(15, 10);
	printw("Input: ");
}
