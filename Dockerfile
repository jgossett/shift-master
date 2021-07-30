# syntax=docker/dockerfile:1
FROM openjdk:16-alpine

# create application user and group.
RUN addgroup -S spring
RUN adduser -S spring -G spring
USER spring:spring

# copy JAR files.
ARG JAR_FILE=build/libs/shiftmaster-0.1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# start application.
ENTRYPOINT java -jar app.jar
