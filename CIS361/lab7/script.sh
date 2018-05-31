#!/bin/bash
while IFS='' read -r line || [[ -n "$line" ]]; do
	lines=($line)

	echo ${lines[0]}

	if [ ! -d ${lines[0]} ]; then
		mkdir ${lines[0]}
	fi

	if [ ! -f /${lines[0]}/${lines[1]} ]; then
		touch ${lines[0]}/${lines[1]}
	fi

done < "$1"
