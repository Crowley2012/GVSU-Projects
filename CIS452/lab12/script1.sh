#!/bin/bash

echo -n "Enter a file name : "
read text

stat $text
echo
./a.out $text
