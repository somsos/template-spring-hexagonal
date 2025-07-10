# How to build: "docker build -t template51_backend:0.0.1 . "

# How to run: "docker run -p 8080:8080 template51_backend:0.0.1"


# Dependencies downloader
FROM maven:3.9.9-eclipse-temurin-21-alpine AS deps

RUN mkdir /opt/template51
WORKDIR /opt/template51

COPY common/pom.xml   /opt/template51/common/pom.xml
COPY user/pom.xml     /opt/template51/user/pom.xml
COPY product/pom.xml  /opt/template51/product/pom.xml
COPY adapter/pom.xml  /opt/template51/adapter/pom.xml
COPY pom.xml          /opt/template51/pom.xml

RUN mvn -B -e org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline






# Builder
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

RUN mkdir /opt/template51
WORKDIR /opt/template51

COPY --from=deps /root/.m2                  /root/.m2
COPY --from=deps /opt/template51/           /opt/template51
COPY --from=deps /opt/template51/pom.xml    /opt/template51/pom.xml

COPY common/src   /opt/template51/common/src
COPY user/src     /opt/template51/user/src
COPY product/src  /opt/template51/product/src
COPY adapter/src  /opt/template51/adapter/src

RUN mvn -B -e clean package -DskipTests




# Runner
FROM eclipse-temurin:21-alpine

RUN mkdir /opt/template51
WORKDIR /opt/template51

COPY --from=builder /opt/template51/adapter/target/adapter*.jar /opt/template51/template51.jar

EXPOSE 8080

ENTRYPOINT java -Dspring.profiles.active=default,test-docker -jar template51.jar
