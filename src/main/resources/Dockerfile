FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 9000
ADD /target/CommonBFF.jar CommonBFF.jar

ENTRYPOINT ["java", "-jar", "CommonBFF.jar"]