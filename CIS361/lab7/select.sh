#!/bin/bash
for f in *; do 
	[[ -d "$f" ]] || echo "$f"; 
done
