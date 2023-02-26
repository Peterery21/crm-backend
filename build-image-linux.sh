#!/bin/bash
for dir in */
do
  if ${dir} == *"service"*; then
  echo ${dir}
  fi
done