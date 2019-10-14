FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 9000
ADD /target/web-bff.jar web-bff.jar

ENTRYPOINT ["java", "-jar", "web-bff.jar"] 