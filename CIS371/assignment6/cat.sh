#!/bin/bash

if [ $# -ne 1 ]; then
	cat test.txt
    exit 1
fi

cat "$1"

