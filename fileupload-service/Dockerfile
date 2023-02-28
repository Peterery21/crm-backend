FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar

RUN apk --update add racktables

COPY wait.sh wait.sh

RUN chmod +x wait.sh

CMD ./wait.sh

#ENTRYPOINT ["java","-jar","/app.jar"]