#!/bin/bash
set -evx
for dir in */; do
    if [ -d "$dir" ]; then
        echo "$dir"
    fi
done

