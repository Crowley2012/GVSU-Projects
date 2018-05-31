#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define SIZE 50

//Reverses string
char *reverse(char *str);

//Push onto stack
void push(char *elem);

//Pop off stack
char pop();

//Precedence order
int precedence(char *elem);

//Stack
char *stack[SIZE];
int top=-1;

int main(){
    char infix[50],prefix[50],ch;
    int i=0,j=0;

	//Get equation
    printf("Infix Equation: ");
    scanf("%s",infix);

	//Puts a terminating char onto bottom of stack
    push('\0');

	//Reverses input
    reverse(infix);

	//
    while((ch=infix[i++]) != '\0'){
        if(isalnum(ch))
			prefix[j++]=ch;
        else{
            while(precedence(*stack[top]) >= precedence(ch))
                prefix[j++]=pop();
            push(ch);
		}
    }

    while( stack[top] != '\0')     /* Pop from stack till empty */
        prefix[j++]=pop();

    prefix[j]='\0';          /* Make prefix as valid string */

    reverse(prefix);

    printf("Prefix Expn: %s\n",prefix);

	for(i=0;i<j;i++)
		printf("%c\n",prefix[i]);


	return 0;
}

char *reverse(char *str){
      char *p1, *p2;

      if (! str || ! *str)
            return str;
      for (p1 = str, p2 = str + strlen(str) - 1; p2 > p1; ++p1, --p2){
            *p1 ^= *p2;
            *p2 ^= *p1;
            *p1 ^= *p2;
      }

      return str;
}

void push(char *elem){
    stack[++top]=elem;
}

char pop(){
    return(stack[top--]);
}

int precedence(char elem){
	if(elem == '\0')
		return 0;
	else if(elem == '+' || elem == '-')
		return 1;
	else
		return 2;
}
