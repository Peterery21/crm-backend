dirs=(*)
SUB='service'
containerName='kodzotech/tresosoft:'
for dir in "${dirs[@]}"
do
  if [[ "$dir" == *"$SUB"* ]]; then
    echo "$dir"
    mvn clean install -f "$dir"
    docker stop "$dir"
    docker rm "$dir"
    docker rmi "$containerName""$dir"
    docker buildx build "$dir" --platform linux/amd64 --push --tag="$containerName""$dir"
    #docker build "$dir" --tag="$containerName""$dir"
    #docker rmi $(docker images -a -q)
  fi
done