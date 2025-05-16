FROM openjdk:17-jdk-slim
EXPOSE 8081
ADD target/dz-conditional-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]