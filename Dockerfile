FROM eclipse-temurin:21-alpine

RUN mkdir /app
WORKDIR /app

COPY ./adapter/target/adapter-0.0.1.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT java -Dspring.profiles.active=default,test-docker -jar app.jar
