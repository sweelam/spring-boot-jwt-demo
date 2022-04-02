FROM openjdk:16-jdk-alpine

RUN mkdir -p /app
WORKDIR /app
COPY target/*.jar ./jwt-demo.jar

EXPOSE 8080

ENV JAVA_ARGS=""
CMD java $JAVA_ARGS -jar jwt-demo.jar