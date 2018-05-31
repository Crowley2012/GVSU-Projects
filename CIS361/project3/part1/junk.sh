#! /bin/bash

#Sean Crowley
#Nesh Suresh
#Project 3
#Cis 361

#Stores the option from parameters
optionLIST="F"
optionNUM="F"
optionHELP="F"

#Gets user parameters
for i in $@; do
	case $i in
		"-l" ) optionLIST="T";;
		"-n" ) optionNUM="T";;
		"--help" ) optionHELP="T";;
	esac
done


if [[ $@ = "" ]]; 
then
	echo "Error : No command-line arguments or options"
else
	# Help option to display usage info
	if [ $optionHELP = "T" ]; 
	then
		echo
		echo "Syntax: junk [options] targetFileList"
		echo
		echo "–l : option lists the current contents of the .junk directory"
		echo "–n : option shows the number of files in the .junk directory and the amount of memory space they consume"
		echo "--help : option displays a brief description about this script as well as each of the options"
		echo
	else
		#Create the .junk directory
		if [ ! -d ~/.junk ]; 
		then
			mkdir ~/.junk
		fi

		#Move files to .junk directory
		files=${*: -1}
		if [ ! $files = "-l" ] && [ ! $files = "-n" ] && [ ! $files = "--help"  ]; then
			while read f; do
				if [ ! -f ~/.junk/$f ]; 
				then
					mv -u $f ~/.junk/$f &> /dev/null
				else
					echo "Error : $f does not exist"
				fi
			done < $files
		fi

		if [[ "$?" -ne "0" ]]; then
			echo "Error : targetFileList contains directories"
		fi


		#List files in the .junk diretory
		if [ $optionLIST = "T" ]; then
			echo
			echo "Contents : "
			echo $(ls ~/.junk)
			echo
		fi

		# Show the number of files and the memory space consumption
		if [ $optionNUM = "T" ]; then
			echo
			wc -c ~/.junk/*
			echo
		fi
	fi
fi
