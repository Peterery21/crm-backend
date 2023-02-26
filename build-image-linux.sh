SUB='service';
containerName='peterado/tresosoft:';
for dir in *;
do
  if "$dir" == *"$SUB"* ; then
    echo "$dir"

  fi;
done