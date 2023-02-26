#!/bin/bash
for dir in */
do
  echo ${dir}
  cd ${dir}
  docker buildx build "$dir" --platform linux/amd64 --push --tag="$containerName""$dir"
  cd ..
done