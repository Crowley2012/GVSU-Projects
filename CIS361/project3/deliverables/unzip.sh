#! /bin/bash

#Sean Crowley
#Nesh Suresh
#Project 3
#Cis 361

#Runs the make files
test() {
    direct="$PWD/$1/"
    cd $direct

    for file in *; do
        echo $file
            make -f ../../makefile -C $file
            make test -f ../../makefile -C $file
    done
}

#Creates the directory
directory() {
    cd $1
    temp=$IFS
    IFS='_'

    for file in *; do
        if [[ ! -d $file ]]; then read -a array <<< "$file"; fi;
        directory=${array[1]}
        if [[ ! -d ${array[1]} ]]; then mkdir $directory; fi;
    done

    IFS=$temp
    cd ..
}

#Moves files
move() {
    temp=$IFS
    IFS='_'
    cd $1

    for file in *; do
        if [[ ! -d $file ]]; then 
            read -a array <<< "$file";
            directory=${array[1]}
            if [[ ${#array[@]} -lt 5 ]]; then
                mv "$file" "$directory/memo.txt"
            else
                mv "$file" "$directory/${array[4]}"
            fi
                
        fi
    done
    IFS=$temp
    cd ..
}

#Unzips the file
if [ $# -eq 1 ]; then
    direct=${1:0:$((${#1}-4))}
    unzip $1 -d $direct
    directory $direct
    move $direct
    test $direct
fi
