## Camel / Spring Boot demo in Docker

FROM jtech/oracle-jdk:latest

MAINTAINER Terrence Miao <TerrenceMiao@users.noreply.github.com>

ADD build/libs/camel-spring-1.0-SNAPSHOT.jar camel-spring.jar

EXPOSE 8080

## application log directory
VOLUME /var/log

## use ENTRYPOINT and CMD to pass arguments
ENTRYPOINT ["/usr/local/jdk/bin/java", "-jar", "camel-spring.jar"]
CMD [""]
