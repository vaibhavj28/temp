FROM openjdk:8-jdk-alpine    
ADD target/CommonBFF.jar CommonBFF.jar      
ENTRYPOINT ["java", "-jar", "/CommonBFF.jar"]    
EXPOSE 80