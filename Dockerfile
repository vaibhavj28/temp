FROM openjdk:8-jdk-alpine    
ADD target/web-bff.jar.jar web-bff.jar.jar      
ENTRYPOINT ["java", "-jar", "/web-bff.jar.jar"]    
EXPOSE 9099