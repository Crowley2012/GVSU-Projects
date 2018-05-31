#! /bin/bash

#Sean Crowley
#Nesh Suresh
#Project 3
#Cis 361

output1="Number of input file lines : "
output2="Number of output file lines : "

input=0
output=0

#checks argument number
if [ $# -eq 1 ]; then
    file="./$1"
    > $file

	#prints lines in file
    while read line; do
        echo $line
    done

	#removes file if exists
	if [ -f $file ]; then
		rm $file
	fi

	echo

	#reads input
    while read line; do 
        let input++
    done <./.tempin

	#reads output and creates report file
    while read line; do 
        let output++
		echo $line >> $file
    done <./.tempout

	#adds data to report file
	echo >> $file
    echo $output1$input
    echo $output1$input >> $file
    echo $output2$output  
    echo $output2$output >> $file
else
    exit 1
fi
