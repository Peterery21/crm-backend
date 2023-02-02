FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar

RUN apk --update add racktables
RUN apk --update add fontconfig ttf-dejavu

COPY wait.sh wait.sh

RUN chmod +x wait.sh

CMD ./wait.sh

#ENTRYPOINT ["java","-jar","/app.jar"]