FROM openjdk:8-jdk-alpine
EXPOSE 80
ADD /target/CommonBFF.jar CommonBFF.jar

ENTRYPOINT ["java", "-jar", "CommonBFF.jar"]