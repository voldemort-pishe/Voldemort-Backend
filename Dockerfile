FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD /target/*.war app.war
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.war"]
