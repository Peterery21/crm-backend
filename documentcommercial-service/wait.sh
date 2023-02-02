#!/bin/sh

while ! nc -z configuration-server 8888; do
    echo "Waiting for the Configuration Server"
    sleep 3
done

java $JAVA_OPTS -jar app.jar