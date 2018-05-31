#! /bin/bash

#Sean Crowley
#Nesh Suresh
#Project 3
#Cis 361

shift() {
	#reads the line
    read -a array <<< "$@"
    len=${#array[@]}

	#shifts
    for ((i=0;i<$len;i++)); do
        unset v
        for ((j=0;j<$len;j++)); do
            index=$(($j+$i))
            index=$(($index%$len))
            v[j]=${array[index]}
        done

		#to file
        echo "${v[@]}"
        echo "${v[@]}" >> ./.tempin
    done
}

#removes temp file
if [ -f .tempin ]; then
	rm .tempin
fi

#removes temp file
if [ -f .tempout ]; then
	rm .tempout
fi

#calls shift for each line
if [ $# -eq 1 ]; then
    >./.tempin
    file="./$1"

    if [ -a $direct ]; then
        while read line; do shift "${line%?}"; done <$file
    else
        exit 1
    fi
else
    exit 2
fi
