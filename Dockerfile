FROM maven:3-jdk-8

ENV HOME=/home/usr/app

RUN mkdir -p $HOME

WORKDIR $HOME

# 1. add pom.xml only here
ADD ./adapter/pom.xml $HOME

# 2. start downloading dependencies
RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

# 3. add all source code and start compiling
ADD . $HOME

RUN ["mvn", "package"]

RUN ["mvn", "package"]

EXPOSE 8005

CMD ["java", "-jar", "./target/dist.jar"]
